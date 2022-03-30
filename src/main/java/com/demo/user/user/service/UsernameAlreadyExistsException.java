package com.demo.user.user.service;

public class UsernameAlreadyExistsException extends RuntimeException {

	public UsernameAlreadyExistsException(String string) {
		super(string);
	}
}
