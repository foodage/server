package com.fourdays.foodage.oauth.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fourdays.foodage.oauth.domain.OauthMember;

public interface OauthMemberRepository extends JpaRepository<OauthMember, Long> {

}