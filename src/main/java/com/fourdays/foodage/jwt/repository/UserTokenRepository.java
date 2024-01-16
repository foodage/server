package com.fourdays.foodage.jwt.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.fourdays.foodage.jwt.domain.UserToken;

public interface UserTokenRepository extends JpaRepository<UserToken, Long> {

	@EntityGraph(attributePaths = "authorities")
	Optional<UserToken> findOneWithAuthoritiesByUsername(String username);
}
