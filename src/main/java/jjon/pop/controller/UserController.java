package jjon.pop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import jjon.pop.model.UserLoginRequestBody;
import jjon.pop.model.UserSignUpRequestBody;
import jjon.pop.model.user.User;
import jjon.pop.model.user.UserAuthenticationResponse;
import jjon.pop.service.UserService;


@RestController
@RequestMapping("/api/v1/users")
public class UserController {
	@Autowired UserService userService;
	
	@PostMapping()
	public ResponseEntity<User> signUp(@Valid @RequestBody UserSignUpRequestBody userSignUpRequestBody) {
		var user = userService.signUp(
				userSignUpRequestBody.username(),
				userSignUpRequestBody.password()
				);
		return ResponseEntity.ok(user);
	}
	
	@PostMapping("/authenticate")
	public ResponseEntity<UserAuthenticationResponse> authenticate(@Valid @RequestBody UserLoginRequestBody userLoginRequestBody) {
		var user = 
				userService.authenticate(
				userLoginRequestBody.username(),
				userLoginRequestBody.password()
				);
		return ResponseEntity.ok(user);
	}
	
}
