package com.fourdays.foodage.user;

import java.util.Set;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.fourdays.foodage.user.controller.command.UserCreateRequest;
import com.fourdays.foodage.user.service.UserCommandService;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;

@SpringBootTest
public class UserCreateTest {

	@Autowired
	private UserCommandService userCommandService;

	@Autowired
	private Validator validator;

	@Nested
	@DisplayName("닉네임 유효성 검사 로직이 정상 동작하는지 확인합니다.")
	class ValidateNickname {

		@Nested
		@DisplayName("🟢 성공 케이스")
		class SuccessTest {

			@Test
			@DisplayName("<유효한 닉네임>으로 생성을 시도하면 성공")
			public void valid_어노테이션_테스트() {
				// given
				String 정상_닉네임 = "foodage";
				String profileUrl = "https://";

				// when - then
				UserCreateRequest userCreateRequest = new UserCreateRequest(정상_닉네임, profileUrl);
				validate(userCreateRequest);
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
				String profileUrl = "https://";

				// when - then
				UserCreateRequest userCreateRequest = new UserCreateRequest(닉네임_공백, profileUrl);
				validate(userCreateRequest);
			}

			@Test
			@DisplayName("<공백이 포함된 닉네임>으로 생성을 시도하면 실패")
			public void 닉네임_공백포함_검증() {
				// given
				String 닉네임_공백포함 = "가  나다";
				String 프로필_주소 = "https://";

				// when - then
				UserCreateRequest userCreateRequest = new UserCreateRequest(닉네임_공백포함, 프로필_주소);
				validate(userCreateRequest);
			}

			@Test
			@DisplayName("<특수문자가 포함된 닉네임>으로 생성을 시도하면 실패")
			public void 닉네임_특수문자_검증() {
				// given
				String 닉네임_특수문자 = "@가나다";
				String 프로필_주소 = "https://";

				// when - then
				UserCreateRequest userCreateRequest = new UserCreateRequest(닉네임_특수문자, 프로필_주소);
				validate(userCreateRequest);
			}

			@Test
			@DisplayName("<자음이 포함된 닉네임>으로 생성을 시도하면 실패")
			public void 닉네임_자음_검증() {
				// given
				String 닉네임_자음 = "ㄱㄴㄷ";
				String 프로필_주소 = "https://";

				// when - then
				UserCreateRequest userCreateRequest = new UserCreateRequest(닉네임_자음, 프로필_주소);
				validate(userCreateRequest);
			}

			@Test
			@DisplayName("<2글자 미만의 닉네임>으로 생성을 시도하면 실패")
			public void 닉네임_최소글자수_검증() {
				// given
				String 닉네임_2글자_미만 = "가";
				String 프로필_주소 = "https://";

				// when - then
				UserCreateRequest userCreateRequest = new UserCreateRequest(닉네임_2글자_미만, 프로필_주소);
				validate(userCreateRequest);
			}

			@Test
			@DisplayName("<12글자를 초과하는 닉네임>으로 생성을 시도하면 실패")
			public void 닉네임_최대글자수_검증() {
				// given
				String 닉네임_12글자_초과 = "가나다라abcd마바사아efgh초과";
				String 프로필_주소 = "https://";

				// when - then
				UserCreateRequest userCreateRequest = new UserCreateRequest(닉네임_12글자_초과, 프로필_주소);
				validate(userCreateRequest);
			}
		}
	}

	private void validate(UserCreateRequest userCreateRequest) {
		Set<ConstraintViolation<UserCreateRequest>> errorMessage = validator.validate(userCreateRequest);
		System.out.println(" ----- errorMessage : " + errorMessage);

		Assertions.assertTrue(errorMessage.isEmpty()); // 비어있으면 정상 요청
	}
}
