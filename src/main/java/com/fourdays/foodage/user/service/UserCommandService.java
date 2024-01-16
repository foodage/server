package com.fourdays.foodage.user.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fourdays.foodage.common.enums.ResultCode;
import com.fourdays.foodage.common.exception.UserException;
import com.fourdays.foodage.user.domain.User;
import com.fourdays.foodage.user.domain.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * author         : ebkim, jhjung
 * date           : 2023/10/19
 * description    : db를 업데이트 하거나 상태를 변경하는 작업을 여기에 작성합니다.
 */
@Service
@Slf4j
public class UserCommandService {

	private final UserRepository userRepository;

	public UserCommandService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	// public void save(UserCreateRequest userCreateRequest) {
	// 	User user = new User(userCreateRequest);
	// 	userRepository.save(user);
	//
	// 	log.debug("\n#--- saved user ---#\nid : {}\nnickname : {}\ncreatedAt : {}\n#------------------#",
	// 		user.getId(), user.getNickname(), user.getCreatedAt());
	// }

	@Transactional
	public void leave(long userId) {
		Optional<User> user = userRepository.findById(userId);
		if (user.isEmpty()) {
			throw new UserException(ResultCode.ERR_USER_NOT_FOUND);
		}

		user.get().leaved(user.get());

		log.debug("\n#--- leaved user ---#\nid : {}\nnickname : {}\n#--------------------#",
			user.get().getId(),
			user.get().getNickname()
		);
	}
}
