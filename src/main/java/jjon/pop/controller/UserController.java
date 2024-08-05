package jjon.pop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
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
import jjon.pop.model.reply.Reply;
import jjon.pop.model.user.Follower;
import jjon.pop.model.user.LikedUser;
import jjon.pop.model.user.User;
import jjon.pop.model.user.UserAuthenticationResponse;
import jjon.pop.service.PostService;
import jjon.pop.service.ReplyService;
import jjon.pop.service.UserService;


@RestController
@RequestMapping("/api/v1/users")
public class UserController {
	@Autowired UserService userService;
	@Autowired PostService postService;
	@Autowired ReplyService replyService;
	
	@GetMapping
	public ResponseEntity<List<User>> getUsers(@RequestParam(required = false) String query, Authentication authentication) {
		var users = userService.getUsers(query, (UserEntity) authentication.getPrincipal());
		return ResponseEntity.ok(users);
	}
	
	@GetMapping("/{username}")
	public ResponseEntity<User> getUser(@PathVariable String username, Authentication authentication)  {
		var user = userService.getUser(username, (UserEntity) authentication.getPrincipal());
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
	public ResponseEntity<List<Post>> getPostByUsername(
			@PathVariable String username, Authentication authentication)  {
		var posts = postService.getPostByUsername(username, (UserEntity) authentication.getPrincipal());
		return ResponseEntity.ok(posts);
	}
	
	@PostMapping("/{username}/follows")
	public ResponseEntity<User> follow(
			@PathVariable String username, Authentication authentication)  {
		var user = userService.follow(username, (UserEntity) authentication.getPrincipal());
		return ResponseEntity.ok(user);
	}
	
	@DeleteMapping("/{username}/follows")
	public ResponseEntity<User> unFollow(
			@PathVariable String username, Authentication authentication)  {
		var user = userService.unFollow(username, (UserEntity) authentication.getPrincipal());
		return ResponseEntity.ok(user);
	}
	
	@GetMapping("/{username}/followers")
	public ResponseEntity<List<Follower>> getFollowersByUser(
			@PathVariable String username, Authentication authentication)  {
		var followers = userService.getFollowersByUsername(username, (UserEntity) authentication.getPrincipal());
		return ResponseEntity.ok(followers);
	}
	
	@GetMapping("/{username}/followings")
	public ResponseEntity<List<User>> getFollowingsByUser(
			@PathVariable String username, Authentication authentication)  {
		var followings = userService.getFollowingsByUsername(username, (UserEntity) authentication.getPrincipal());
		return ResponseEntity.ok(followings);
	}
	
	@GetMapping("/{username}/replies")
	public ResponseEntity<List<Reply>> getrepliesByUser(
			@PathVariable String username)  {
		var replies = replyService.getRepliesByUser(username);
		return ResponseEntity.ok(replies);
	}
	
	@GetMapping("/{username}/liked-users")
	public ResponseEntity<List<LikedUser>> getLikedUserByUser(
			@PathVariable String username, Authentication authentication)  {
		var likedUsers = userService.getLikedUsersByUser(username, (UserEntity) authentication.getPrincipal());
		return ResponseEntity.ok(likedUsers);
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
