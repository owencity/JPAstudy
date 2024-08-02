package jjon.pop.config.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jjon.pop.model.error.ClientErrorReponse;

@RestControllerAdvice
public class GlobalExceptionHandler {
		
	@ExceptionHandler(ClientErrorException.class) 
	public ResponseEntity<ClientErrorReponse> handleClientErrorException(ClientErrorException e) {
		return new ResponseEntity<>(
				new ClientErrorReponse(e.getStatus(), e.getMessage()), e.getStatus());
	}
	
	@ExceptionHandler(HttpMessageNotReadableException.class) 
	public ResponseEntity<ClientErrorReponse> handleHttpMessageNotReadableException(
			HttpMessageNotReadableException e) {
		return new ResponseEntity<>(
				new ClientErrorReponse(HttpStatus.BAD_REQUEST, e.getMessage()), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class) 
	public ResponseEntity<ClientErrorReponse> handleMethodArgumentNotValidException(
			MethodArgumentNotValidException e) {
		var errorMessage = 
				e.getFieldErrors().stream()
				.map(fieldError -> (fieldError.getField() + " : " + fieldError.getDefaultMessage()))
				.toList()
				.toString();
		// 노출 최소화 , 에러문구만 노출 
		// 유효성검사는 클라이언트 레벨, 서버 레벨 , DB레벨 다 필요하다.
		
		return new ResponseEntity<>(
				new ClientErrorReponse(HttpStatus.BAD_REQUEST, errorMessage), HttpStatus.BAD_REQUEST);
	}
	// 서버단의 에러는 클라이언트에 보여줄 필요없다
	@ExceptionHandler(RuntimeException.class) 
	public ResponseEntity<ClientErrorReponse> handleRuntimeException(RuntimeException e) {
		return ResponseEntity.internalServerError().build();
	}
	
	@ExceptionHandler(Exception.class) 
	public ResponseEntity<ClientErrorReponse> handleException(Exception e) {
		return ResponseEntity.internalServerError().build();
	}
}
