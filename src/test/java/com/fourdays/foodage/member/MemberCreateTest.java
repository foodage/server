package com.fourdays.foodage.member;

import java.util.Set;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.fourdays.foodage.common.enums.CharacterType;
import com.fourdays.foodage.member.dto.MemberJoinRequestDto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
public class MemberCreateTest {

	private Validator validator;

	@Nested
	@DisplayName("닉네임 유효성 검사 로직이 정상 동작하는지 확인합니다.")
	class ValidateNickname {

		Long id = 100L;
		String accountEmail = "a@gmail.com";
		String profileUrl = "https://";
		CharacterType characterType;

		@Nested
		@DisplayName("🟢 성공 케이스")
		class SuccessTest {

			@Test
			@DisplayName("<유효한 닉네임>으로 생성을 시도하면 성공")
			public void valid_어노테이션_테스트() {
				// given
				String 정상_닉네임 = "mammoth";

				// when - then
				MemberJoinRequestDto memberCreateRequest = new MemberJoinRequestDto(id, accountEmail,
					정상_닉네임, profileUrl, characterType);
				validate(memberCreateRequest);
			}
		}

		@Nested
		@DisplayName("🔴 실패 케이스")
		class FailureTest {

			@Test
			@DisplayName("<비어있는 닉네임>으로 생성을 시도하면 실패")
			public void 닉네임_공백_검증() {
				// given
				String 닉네임_공백 = "";

				// when - then
				MemberJoinRequestDto memberCreateRequest = new MemberJoinRequestDto(id, accountEmail,
					닉네임_공백, profileUrl, characterType);
				validate(memberCreateRequest);
			}

			@Test
			@DisplayName("<공백이 포함된 닉네임>으로 생성을 시도하면 실패")
			public void 닉네임_공백포함_검증() {
				// given
				String 닉네임_공백포함 = "가  나다";

				// when - then
				MemberJoinRequestDto memberCreateRequest = new MemberJoinRequestDto(id, accountEmail,
					닉네임_공백포함, profileUrl, characterType);
				validate(memberCreateRequest);
			}

			@Test
			@DisplayName("<특수문자가 포함된 닉네임>으로 생성을 시도하면 실패")
			public void 닉네임_특수문자_검증() {
				// given
				String 닉네임_특수문자 = "@가나다";

				// when - then
				MemberJoinRequestDto memberCreateRequest = new MemberJoinRequestDto(id, accountEmail,
					닉네임_특수문자, profileUrl, characterType);
				validate(memberCreateRequest);
			}

			@Test
			@DisplayName("<자음이 포함된 닉네임>으로 생성을 시도하면 실패")
			public void 닉네임_자음_검증() {
				// given
				String 닉네임_자음 = "ㄱㄴㄷ";

				// when - then
				MemberJoinRequestDto memberCreateRequest = new MemberJoinRequestDto(id, accountEmail,
					닉네임_자음, profileUrl, characterType);
				validate(memberCreateRequest);
			}

			@Test
			@DisplayName("<2글자 미만의 닉네임>으로 생성을 시도하면 실패")
			public void 닉네임_최소글자수_검증() {
				// given
				String 닉네임_2글자_미만 = "가";

				// when - then
				MemberJoinRequestDto memberCreateRequest = new MemberJoinRequestDto(id, accountEmail,
					닉네임_2글자_미만, profileUrl, characterType);
				validate(memberCreateRequest);
			}

			@Test
			@DisplayName("<12글자를 초과하는 닉네임>으로 생성을 시도하면 실패")
			public void 닉네임_최대글자수_검증() {
				// given
				String 닉네임_12글자_초과 = "가나다라abcd마바사아efgh초과";

				// when - then
				MemberJoinRequestDto memberCreateRequest = new MemberJoinRequestDto(id, accountEmail,
					닉네임_12글자_초과, profileUrl, characterType);
				validate(memberCreateRequest);
			}
		}
	}

	private void validate(MemberJoinRequestDto memberCreateRequest) {
		Set<ConstraintViolation<MemberJoinRequestDto>> errorMessage = validator.validate(memberCreateRequest);

		log.debug("\n\n### errorMessage : {}", errorMessage);

		Assertions.assertTrue(errorMessage.isEmpty()); // 비어있으면 정상 요청
	}
}
