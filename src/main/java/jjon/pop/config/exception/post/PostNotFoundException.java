package jjon.pop.config.exception.post;

import org.springframework.http.HttpStatus;

import jjon.pop.config.exception.ClientErrorException;

public class PostNotFoundException extends ClientErrorException{ 
	
	public PostNotFoundException() {
		super(HttpStatus.NOT_FOUND, "Post Not Found");
	}
	
	public PostNotFoundException(Long postId) {
		super(HttpStatus.NOT_FOUND, "Post Not Found" + postId + "not Found");
		}
	
	public PostNotFoundException(String message) {
		super(HttpStatus.NOT_FOUND, message);
	}
}
