package com.fourdays.foodage.member.domain;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fourdays.foodage.oauth.domain.OauthId;
import com.fourdays.foodage.oauth.util.OauthServerType;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

	Optional<Member> findByOauthIdAndAccountEmail(OauthId oauthId, String accountEmail);

	Optional<Member> findByOauthIdOauthServerTypeAndAccountEmail(OauthServerType oauthServer, String accountEmail);

	@Query("SELECT m.accountEmail FROM Member m WHERE m.id = :id")
	Optional<String> findAccountEmailById(Long id);

	@EntityGraph(attributePaths = "authorities")
	Optional<Member> findOneWithAuthoritiesByNickname(String nickname);
}
