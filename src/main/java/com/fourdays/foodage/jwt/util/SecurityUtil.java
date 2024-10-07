package com.fourdays.foodage.jwt.util;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.fourdays.foodage.common.exception.ExceptionInfo;
import com.fourdays.foodage.jwt.enums.JwtClaim;
import com.fourdays.foodage.jwt.exception.InvalidArgsException;
import com.fourdays.foodage.member.vo.MemberId;
import com.fourdays.foodage.oauth.util.OauthServerType;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class SecurityUtil {

	private static String secret;

	@Value("${jwt.secret}")
	private void setSecret(String value) {
		secret = value;
	}

	public static MemberId getCurrentMemberId() {

		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null) {
			log.error("Security Context에 인증 정보가 없습니다.");
			return null;
		}

		OauthServerType oauthServerType = null;
		String accountEmail = null;
		if (authentication.getPrincipal() instanceof UserDetails) {
			UserDetails springSecurityUser = (UserDetails)authentication.getPrincipal();
			accountEmail = springSecurityUser.getUsername();

			// parse claim
			try {
				Claims claims = getClaims(authentication.getCredentials().toString());
				oauthServerType = OauthServerType.fromName(
					claims.get(JwtClaim.OAUTH_SERVER_TYPE.getValue()).toString()
				);
			} catch (Exception e) {
				log.error("유효한 Jwt Claim을 찾을 수 없습니다.");
			}
		}

		if (oauthServerType == null || accountEmail == null) {
			throw new InvalidArgsException(ExceptionInfo.ERR_MEMBER_ID_CREATE_FAILED);
		}
		return new MemberId(oauthServerType, accountEmail);
	}

	public static Optional<MemberId> getOptionalMemberId() {

		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null) {
			log.error("Security Context에 인증 정보가 없습니다.");
			return null;
		}

		OauthServerType oauthServerType = null;
		String accountEmail = null;
		if (authentication.getPrincipal() instanceof UserDetails) {
			UserDetails springSecurityUser = (UserDetails)authentication.getPrincipal();
			accountEmail = springSecurityUser.getUsername();

			// parse claim
			try {
				Claims claims = getClaims(authentication.getCredentials().toString());
				oauthServerType = OauthServerType.fromName(
					claims.get(JwtClaim.OAUTH_SERVER_TYPE.getValue()).toString()
				);
			} catch (Exception e) {
				log.error("유효한 Jwt Claim을 찾을 수 없습니다.");
			}
		}

		if (oauthServerType == null || accountEmail == null) {
			return Optional.empty();
		}
		return Optional.of(new MemberId(oauthServerType, accountEmail));
	}

	public static Claims getClaims(String token) {

		return Jwts.parserBuilder()
			.setSigningKey(secret)
			.build()
			.parseClaimsJws(token)
			.getBody();
	}
}
