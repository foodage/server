package com.fourdays.foodage.oauth.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fourdays.foodage.oauth.domain.OauthId;
import com.fourdays.foodage.oauth.domain.OauthMember;

public interface OauthMemberRepository extends JpaRepository<OauthMember, Long> {

	Optional<OauthMember> findByOauthId(OauthId oauthId);
	// 회원가입 추가 구현 필요
}