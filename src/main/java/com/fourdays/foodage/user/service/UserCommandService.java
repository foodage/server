package com.fourdays.foodage.user.service;

import org.springframework.stereotype.Service;

import com.fourdays.foodage.user.controller.command.UserCreateRequest;
import com.fourdays.foodage.user.domain.User;
import com.fourdays.foodage.user.domain.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserCommandService {

	private final UserRepository userRepository;

	public UserCommandService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public void save(UserCreateRequest userCreateRequest) {
		User user = new User(userCreateRequest);
		userRepository.save(user);

		log.debug("\n#--- saved user ---#\nid : {}\nnickname : {}\ncreatedAt : {}\n#------------------#",
			user.getId(), user.getNickname(), user.getCreatedAt());
	}
}
