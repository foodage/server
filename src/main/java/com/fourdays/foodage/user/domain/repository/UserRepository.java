package com.fourdays.foodage.user.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fourdays.foodage.oauth.domain.OauthId;
import com.fourdays.foodage.user.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByOauthIdAndAccountEmail(OauthId oauthId, String accountEmail);
}
