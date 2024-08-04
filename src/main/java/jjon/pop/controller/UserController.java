package jjon.pop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import jjon.pop.entity.UserEntity;
import jjon.pop.model.Post;
import jjon.pop.model.UserLoginRequestBody;
import jjon.pop.model.UserPatchRequestBody;
import jjon.pop.model.UserSignUpRequestBody;
import jjon.pop.model.user.User;
import jjon.pop.model.user.UserAuthenticationResponse;
import jjon.pop.service.PostService;
import jjon.pop.service.UserService;


@RestController
@RequestMapping("/api/v1/users")
public class UserController {
	@Autowired UserService userService;
	@Autowired PostService postService;
	
	@GetMapping
	public ResponseEntity<List<User>> getUsers(@RequestParam(required = false) String query) {
		var users = userService.getUsers(query);
		return ResponseEntity.ok(users);
	}
	
	@GetMapping("/{username}")
	public ResponseEntity<User> getUser(@PathVariable String username)  {
		var user = userService.getUser(username);
		return ResponseEntity.ok(user);
	}
	
	@PatchMapping("/{username}")
	public ResponseEntity<User> updateUser(@PathVariable String username,
			@RequestBody UserPatchRequestBody requestBody,
			Authentication authentication)  {
		var user = userService.updateUser(username, requestBody, (UserEntity) authentication.getPrincipal());
		return ResponseEntity.ok(user);
	}
	
	@GetMapping("/{username}/posts")
	public ResponseEntity<List<Post>> getPostByUsername(@PathVariable String username)  {
		var posts = postService.getPostByUsername(username);
		return ResponseEntity.ok(posts);
	}
	
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
