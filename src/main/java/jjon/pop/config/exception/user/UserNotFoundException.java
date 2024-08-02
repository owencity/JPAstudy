package jjon.pop.config.exception.user;

import org.springframework.http.HttpStatus;

import jjon.pop.config.exception.ClientErrorException;

public class UserNotFoundException extends ClientErrorException{ 
	
	public UserNotFoundException() {
		super(HttpStatus.NOT_FOUND, "User Not Found");
	}
	
	public UserNotFoundException(String username) {
		super(HttpStatus.NOT_FOUND, "User with username" + username + "not Found");
		}
	

}
