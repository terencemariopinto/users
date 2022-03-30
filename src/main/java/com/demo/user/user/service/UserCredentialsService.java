package com.demo.user.user.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.demo.user.user.entity.UserCredentials;
import com.demo.user.user.repository.UserCredentialsRepository;

@Service
public class UserCredentialsService implements UserDetailsService {
	
	@Autowired
	private UserCredentialsRepository userCredentialsRepository;


	public UserCredentials createUser(UserCredentials user) {
		if(null == userCredentialsRepository.findByUsername(user.getUsername())) {
			return userCredentialsRepository.save(user);
		}
		return null;
	}

	@Override
	public org.springframework.security.core.userdetails.UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		UserCredentials userCredentials = userCredentialsRepository.findByUsername(username);
		return new User(userCredentials.getUsername(), userCredentials.getPassword(), new ArrayList<>());
	}

}
