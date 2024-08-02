package jjon.pop.config.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jjon.pop.model.error.ClientErrorReponse;

@RestControllerAdvice
public class GlobalExceptionHandler {
		
	@ExceptionHandler(ClientErrorException.class) 
	public ResponseEntity<ClientErrorReponse> handleClientErrorException(ClientErrorException e) {
		return new ResponseEntity<>(new ClientErrorReponse(e.getStatus(), e.getMessage()), e.getStatus());
	}

	
}
