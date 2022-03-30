package com.demo.user.user.exception;

import java.util.Date;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.demo.user.user.model.ExceptionResponse;
import com.demo.user.user.model.UserExistsException;
import com.demo.user.user.model.UserNotFoundException;

import org.springframework.web.bind.MethodArgumentNotValidException;



@ControllerAdvice
@Controller
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	
	@ExceptionHandler(Exception.class)
	public final ResponseEntity<Object> handleAllException(Exception ex, WebRequest request) throws Exception {
		
		ExceptionResponse exceptionResponse = new ExceptionResponse(request.getDescription(false), ex.getMessage(), new Date());
		return new ResponseEntity(exceptionResponse, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(UserExistsException.class)
	public final ResponseEntity<Object> handleUserExistsException(UserExistsException ex, WebRequest request){
	
		ExceptionResponse exceptionResponse = new ExceptionResponse(request.getDescription(false), ex.getMessage(), new Date());
		return new ResponseEntity(exceptionResponse, HttpStatus.IM_USED);
	}
	
	@ExceptionHandler(BadCredentialsException.class)
	public final ResponseEntity<Object> handleBadCredentialsException(BadCredentialsException ex, WebRequest request){
	
		ExceptionResponse exceptionResponse = new ExceptionResponse(request.getDescription(false), ex.getMessage(), new Date());
		return new ResponseEntity(exceptionResponse, HttpStatus.UNAUTHORIZED);
	}
	
	@ExceptionHandler(UserNotFoundException.class)
	public final ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException ex, WebRequest request){
	
		ExceptionResponse exceptionResponse = new ExceptionResponse(request.getDescription(false), ex.getMessage(), new Date());
		return new ResponseEntity(exceptionResponse, HttpStatus.NOT_FOUND);
	}
	
	@Override
	public final ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request){
	
		ExceptionResponse exceptionResponse = new ExceptionResponse(ex.getBindingResult().getFieldError().toString(), "Validation Failed", new Date());
		return new ResponseEntity(exceptionResponse, HttpStatus.BAD_REQUEST);
	}
}
