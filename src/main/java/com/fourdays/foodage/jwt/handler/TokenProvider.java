package com.fourdays.foodage.jwt.handler;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import com.fourdays.foodage.common.enums.ResultCode;
import com.fourdays.foodage.jwt.dto.TokenDto;
import com.fourdays.foodage.jwt.enums.JwtClaim;
import com.fourdays.foodage.jwt.enums.JwtType;
import com.fourdays.foodage.jwt.exception.JwtClaimParseException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class TokenProvider implements InitializingBean {

	private final String secret;
	private final long accessTokenExpiration; // ms
	private final long refreshTokenExpiration; // ms
	private Key key;

	public TokenProvider(
		@Value("${jwt.secret}") String secret,
		@Value("${jwt.access-token-expiration-seconds}") long accessTokenExpiration,
		@Value("${jwt.refresh-token-expiration-seconds}") long refreshTokenExpiration) {

		this.secret = secret;
		this.accessTokenExpiration = accessTokenExpiration * 1000;
		this.refreshTokenExpiration = refreshTokenExpiration * 1000;
	}

	@Override
	public void afterPropertiesSet() {

		byte[] keyBytes = Decoders.BASE64.decode(secret);
		this.key = Keys.hmacShaKeyFor(keyBytes);
	}

	public TokenDto createToken(Authentication authentication) {

		SecurityContextHolder.getContext().setAuthentication(authentication);

		String authorities = getAuthorities(authentication);
		long now = (new Date()).getTime();
		Date accessTokenExpiration = new Date(now + this.accessTokenExpiration);
		Date refreshTokenExpiration = new Date(now + this.refreshTokenExpiration);

		String accessToken = Jwts.builder()
			.setSubject(authentication.getName())
			.claim(JwtClaim.TYPE.name(), JwtType.ACCESS_TOKEN)
			.claim(JwtClaim.ROLE.name(), authorities)
			.signWith(key, SignatureAlgorithm.HS512)
			.setExpiration(accessTokenExpiration)
			.compact();

		String refreshToken = Jwts.builder()
			.claim(JwtClaim.ROLE.name(), authorities)
			.claim(JwtClaim.TYPE.name(), JwtType.REFRESH_TOKEN)
			.signWith(key, SignatureAlgorithm.HS512)
			.setExpiration(refreshTokenExpiration)
			.compact();

		TokenDto tokenDto = new TokenDto(accessToken, refreshToken);
		return tokenDto;
	}

	private static String getAuthorities(Authentication authentication) {

		return authentication.getAuthorities().stream()
			.map(GrantedAuthority::getAuthority)
			.collect(Collectors.joining(","));
	}

	public Authentication getAuthentication(String token) {

		Claims claims = Jwts
			.parserBuilder()
			.setSigningKey(key)
			.build()
			.parseClaimsJws(token)
			.getBody();

		Collection<? extends GrantedAuthority> authorities =
			Arrays.stream(claims.get(JwtClaim.ROLE.name()).toString().split(","))
				.map(SimpleGrantedAuthority::new)
				.collect(Collectors.toList());

		User principal = new User(claims.getSubject(), "", authorities);

		return new UsernamePasswordAuthenticationToken(principal, token, authorities);
	}

	public Jws<Claims> getClaims(String token) {

		if (!validateToken(token)) { // 토큰이 유효하지 않을 경우
			throw new JwtClaimParseException(ResultCode.ERR_INVALID_JWT);
		}
		return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
	}

	public boolean validateToken(String token) {

		try {
			Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
			return true;
		} catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
			log.info("JWT 서명이 잘못되었습니다.");
		} catch (ExpiredJwtException e) {
			log.info("JWT가 만료되었습니다.");
		} catch (UnsupportedJwtException | IllegalArgumentException e) {
			log.info("유효하지 않은 JWT입니다.");
		}
		return false;
	}
}
