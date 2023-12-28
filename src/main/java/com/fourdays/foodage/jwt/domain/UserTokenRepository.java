package com.fourdays.foodage.jwt.domain;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserTokenRepository extends JpaRepository<UserToken, Long> {

	@EntityGraph(attributePaths = "authorities")
	Optional<UserToken> findOneWithAuthoritiesByUsername(String username);
}
