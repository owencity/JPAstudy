package jjon.pop.config.exception.user;

import org.springframework.http.HttpStatus;

import jjon.pop.config.exception.ClientErrorException;

public class UserAlreadyExistException extends ClientErrorException{ 
	
	public UserAlreadyExistException() {
		super(HttpStatus.CONFLICT, "User alredy exists");
	}
	
	public UserAlreadyExistException(String username) {
		super(HttpStatus.CONFLICT, "User with username" + username + "User alredy exists");
		}
	

}
