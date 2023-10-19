package com.fourdays.foodage.user.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fourdays.foodage.user.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
