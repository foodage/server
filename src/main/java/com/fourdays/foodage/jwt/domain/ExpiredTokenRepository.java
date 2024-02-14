package com.fourdays.foodage.jwt.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpiredTokenRepository extends JpaRepository<ExpiredToken, Long> {

}
