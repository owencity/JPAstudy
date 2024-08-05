package jjon.pop.config.exception.follow;

import org.springframework.http.HttpStatus;

import jjon.pop.config.exception.ClientErrorException;

public class InvalidFollowException extends ClientErrorException{ 
	
	public InvalidFollowException() {
		super(HttpStatus.BAD_REQUEST, "Invalid follow request");
	}
	
	public InvalidFollowException(String message) {
		super(HttpStatus.BAD_REQUEST, message);
	}
}
