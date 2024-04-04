package com.fourdays.foodage.member.domain;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fourdays.foodage.oauth.domain.OauthId;
import com.fourdays.foodage.oauth.util.OauthServerType;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

	boolean existsByNickname(String nickname);

	boolean existsByOauthIdAndAccountEmail(OauthId oauthId, String accountEmail);

	Optional<Member> findByOauthIdAndAccountEmail(OauthId oauthId, String accountEmail);

	Optional<Member> findByOauthIdOauthServerTypeAndAccountEmail(OauthServerType oauthServer, String accountEmail);

	@EntityGraph(attributePaths = "authorities")
	Optional<Member> findOneWithAuthoritiesByAccountEmail(String accountEmail);
}
