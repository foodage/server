package com.fourdays.foodage.oauth.dto;

import static com.fourdays.foodage.oauth.util.OauthServerType.*;

import java.time.LocalDateTime;

import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fourdays.foodage.oauth.domain.OauthId;
import com.fourdays.foodage.oauth.domain.OauthMember;

@JsonNaming(SnakeCaseStrategy.class)
public record KakaoMember(
	Long id,
	boolean hasSignedUp,
	LocalDateTime connectedAt,
	KakaoAccount kakaoAccount
) {

	public OauthMember toOauthMember() {
		return OauthMember.builder()
			.oauthId(new OauthId(String.valueOf(id), KAKAO))
			.accountEmail(kakaoAccount.email)
			.build();
	}

	@JsonNaming(SnakeCaseStrategy.class)
	public record KakaoAccount(
		boolean profileNeedsAgreement,
		boolean profileNicknameNeedsAgreement,
		boolean profileImageNeedsAgreement,
		Profile profile,
		boolean nameNeedsAgreement,
		String name,
		boolean emailNeedsAgreement,
		boolean isEmailValid,
		boolean isEmailVerified,
		String email,
		boolean ageRangeNeedsAgreement,
		String ageRange,
		boolean birthyearNeedsAgreement,
		String birthyear,
		boolean birthdayNeedsAgreement,
		String birthday,
		String birthdayType,
		boolean genderNeedsAgreement,
		String gender,
		boolean phoneNumberNeedsAgreement,
		String phoneNumber,
		boolean ciNeedsAgreement,
		String ci,
		LocalDateTime ciAuthenticatedAt
	) {
	}

	@JsonNaming(SnakeCaseStrategy.class)
	public record Profile(
		String nickname,
		String thumbnailImageUrl,
		String profileImageUrl,
		boolean isDefaultImage
	) {
	}
}