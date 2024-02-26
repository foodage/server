package com.fourdays.foodage.member.dto;

import com.fourdays.foodage.common.enums.CharacterType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberCreateRequestDto {

	@NotNull
	private Long id;

	@NotBlank(message = "계정 이메일을 입력해주세요.")
	private String accountEmail;

	@NotBlank(message = "닉네임을 입력해주세요.")
	@Size(min = 2, max = 8, message = "닉네임은 2~8글자 사이로 입력해주세요. (예: 푸디지)")
	@Pattern(regexp = "^[a-zA-Z0-9가-힣]+$", message = "닉네임에 사용할 수 없는 문자가 포함되어 있습니다. 다시 입력해주세요.")
	private String nickname;

	private String profileImage;

	@NotNull(message = "캐릭터를 선택해주세요.")
	private CharacterType character;
}
