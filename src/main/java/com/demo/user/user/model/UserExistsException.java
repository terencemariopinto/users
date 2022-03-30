package com.demo.user.user.model;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.IM_USED)
public class UserExistsException extends RuntimeException {

	public UserExistsException(String string) {
		super(string);
	}

}
