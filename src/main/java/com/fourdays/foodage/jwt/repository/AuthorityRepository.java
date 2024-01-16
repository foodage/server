package com.fourdays.foodage.jwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.fourdays.foodage.jwt.domain.Authority;

public interface AuthorityRepository extends JpaRepository<Authority, String> {
}
