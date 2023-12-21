package com.fourdays.foodage.member.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fourdays.foodage.member.domain.Member;
import com.fourdays.foodage.oauth.domain.OauthId;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

	Optional<Member> findByAccountEmail(String accountEmail);

	Optional<Member> findByOauthIdAndAccountEmail(OauthId oauthId, String accountEmail);
}
