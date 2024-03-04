package com.fourdays.foodage.jwt.domain;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberTokenRepository extends JpaRepository<MemberToken, Long> {

	@EntityGraph(attributePaths = "authorities")
	Optional<MemberToken> findOneWithAuthoritiesByNickname(String username);
}
