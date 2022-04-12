package com.demo.user.user.controller;

import java.net.URI;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.demo.user.user.entity.UserInfo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.demo.user.user.entity.UserCredentials;
import com.demo.user.user.model.AuthenticationRequest;
import com.demo.user.user.model.AuthenticationResponse;
import com.demo.user.user.model.UserExistsException;
import com.demo.user.user.model.UserNotFoundException;
import com.demo.user.user.service.UserCredentialsService;
import com.demo.user.user.service.UserInfoService;
import com.demo.user.user.util.JwtUtil;

@RestController
public class UserController {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserCredentialsService userCredentialsService;
	
	@Autowired
	private UserInfoService userInfoService;

	@Autowired
	private JwtUtil jwtTokenUtil;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@PostMapping("/api/register")
	public ResponseEntity<?> registerUser(@RequestBody UserCredentials user) {
		UserCredentials savedUser = null;
		
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		savedUser = userCredentialsService.createUser(user);
		if(null == savedUser) {
			throw new UserExistsException("User Id "+ user.getUsername()+ " already exists, choose another User Id ");
		}
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{username}").buildAndExpand(savedUser.getUsername()).toUri();
		return ResponseEntity.created(location).build();
	}

	@PostMapping("/api/authenticate")
	public ResponseEntity<AuthenticationResponse> authenticateUser(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword()));
		}
		catch (BadCredentialsException e) {
			throw new BadCredentialsException("Username or Password is Incorrect");
		}
		final UserDetails userDetails = userCredentialsService.loadUserByUsername(authenticationRequest.getUsername());
		final String jwt = jwtTokenUtil.generateToken(userDetails);
		return ResponseEntity.ok(new AuthenticationResponse(jwt));
	}
	
	@GetMapping("/api/users/{uuid}")
	public UserInfo getUser(@PathVariable("uuid") UUID userUuid) throws Exception {
		UserInfo user = null;
		
		user = userInfoService.getUserbyUuid(userUuid);
		if(null == user) {
			throw new UserNotFoundException("User Id "+ userUuid+ " does not Exist");
		}
		return user;
	}
	
	@PostMapping("/api/users/{uuid}")
	public ResponseEntity<?> updateUser(@PathVariable("uuid") UUID userUuid, @RequestBody UserInfo user) {
		user.setId(userUuid);
		UserInfo updatedUser= userInfoService.updateUserInfo(user);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(updatedUser.getId()).toUri();
		return ResponseEntity.created(location).build();
	}

	@PostMapping("/api/users")
	public ResponseEntity<?> createUser(@RequestBody UserInfo user) {
		UserInfo savedUser= userInfoService.createUser(user);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedUser.getId()).toUri();
		return ResponseEntity.created(location).build();
	}
}
