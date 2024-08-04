package jjon.pop.config.exception.user;

import org.springframework.http.HttpStatus;

import jjon.pop.config.exception.ClientErrorException;

public class UserNotAllowedException extends ClientErrorException{ 
	
	public UserNotAllowedException() {
		super(HttpStatus.FORBIDDEN,  "User Not allowed");
	}
}
