package com.fourdays.foodage.member;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.fourdays.foodage.member.domain.Member;
import com.fourdays.foodage.member.service.MemberQueryService;
import com.fourdays.foodage.member.vo.MemberId;
import com.fourdays.foodage.oauth.util.OauthServerType;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
public class MemberEncryptTest {

	@Autowired
	private MemberQueryService memberQueryService;

	@Nested
	@DisplayName("DB 컬럼의 데이터 암복호화가 정상적으로 이루어지는지 확인합니다.")
	class DecryptAccountEmail {

		OauthServerType oauthServerType = OauthServerType.NAVER;
		String accountEmail = "box0_@naver.com";

		@Nested
		@DisplayName("🟢 성공 케이스")
		class SuccessTest {

			@Test
			@DisplayName("<이메일>을 잘 가져왔으면 성공")
			public void get_이메일() {
				// when - then
				Member member = memberQueryService.findByMemberId(MemberId.create(oauthServerType, accountEmail));
				Assertions.assertNotNull(member);
				System.out.println(member.getAccountEmail());
			}
		}

		@Nested
		@DisplayName("🔴 실패 케이스")
		class FailureTest {

		}
	}
}
