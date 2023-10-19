package com.fourdays.foodage.user.service;

import java.util.Optional;

import org.springframework.web.bind.annotation.RestController;

import com.fourdays.foodage.common.enums.BadRequestCode;
import com.fourdays.foodage.common.exception.UserException;
import com.fourdays.foodage.user.domain.User;
import com.fourdays.foodage.user.domain.repository.UserRepository;
import com.fourdays.foodage.user.service.dto.UserInfo;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class UserQueryService {

	private final UserRepository userRepository;

	public UserQueryService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public UserInfo getUserInfo(Long id) {
		Optional<User> findUser = userRepository.findById(id);
		if (findUser.isEmpty()) {
			throw new UserException(BadRequestCode.ERR_USER_NOT_FOUND);
		}
		return new UserInfo(findUser.get());
	}
}
