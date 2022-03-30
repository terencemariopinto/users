package com.demo.user.user.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.user.user.entity.UserInfo;
import com.demo.user.user.model.UserNotFoundException;
import com.demo.user.user.repository.UserInfoRepository;

@Service
public class UserInfoService {

	@Autowired
	private UserInfoRepository userInfoRepository;
	
	public UserInfo updateUserInfo(UserInfo userInfo) {
		return userInfoRepository.save(userInfo);
	}

	public UserInfo createUser(UserInfo user) {
		return userInfoRepository.save(user);
	}

	public UserInfo getUserbyUuid(UUID userUuid) throws UserNotFoundException {
		return userInfoRepository.findById(userUuid).orElse(null);
	}
}
