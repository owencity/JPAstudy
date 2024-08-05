package jjon.pop.config.exception.reply;

import org.springframework.http.HttpStatus;

import jjon.pop.config.exception.ClientErrorException;

public class ReplyNotFoundException extends ClientErrorException{ 
	
	public ReplyNotFoundException() {
		super(HttpStatus.NOT_FOUND, "Reply Not Found");
	}
	
	public ReplyNotFoundException(Long replyId) {
		super(HttpStatus.NOT_FOUND, "Reply Not Found" + replyId + "not Found");
		}
	
	public ReplyNotFoundException(String message) {
		super(HttpStatus.NOT_FOUND, message);
	}
}
