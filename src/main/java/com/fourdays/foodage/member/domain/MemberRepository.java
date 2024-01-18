package com.fourdays.foodage.member.domain;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fourdays.foodage.oauth.domain.OauthId;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

	Optional<Member> findByAccountEmail(String accountEmail);

	Optional<Member> findByOauthIdAndAccountEmail(OauthId oauthId, String accountEmail);

	@EntityGraph(attributePaths = "authorities")
	Optional<Member> findOneWithAuthoritiesByNickname(String nickname);
}
