package jjon.pop.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import org.springframework.web.bind.annotation.RestController;

import jjon.pop.entity.UserEntity;
import jjon.pop.model.Post;
import jjon.pop.model.PostPatchRequestBody;
import jjon.pop.model.PostPostRequestBody;
import jjon.pop.service.PostService;

@RestController
@RequestMapping("/api/v1/posts")
public class PostController {
	
	private static final Logger logger = LoggerFactory.getLogger(PostController.class);
	
	@Autowired private PostService postService;
	
	@GetMapping
	public ResponseEntity<List<Post>> getPosts() {
		logger.info("GET /api/v1/posts");
		var posts = postService.getPosts();
		return ResponseEntity.ok(posts);
	}
	
	@GetMapping("/{postId}")
	public ResponseEntity<Post> getBypostsId(@PathVariable Long postId) {
//			@PathVariable("postId") Long postId  -> 기본형태는 이렇게 쓰나 스프링부트 3.2 버전 이상부터 -parameter 옵션 
//			비활성화 되있어서 따로 설정해야 위에처럼사용가능
		logger.info("GET /api/v1/posts/{}", postId);
		var post  = postService.getPostByPostId(postId);
		return ResponseEntity.ok(post);

		//				matchingPost.map(post -> ResponseEntity.ok(post))
//				.orElseGet(() -> ResponseEntity.notFound().build());
				// ElseGet -> Optional 안에 값이 없을때 404 Not Found 응답 반환.
	} 
	
	@PostMapping
	public ResponseEntity<Post> createPost(
			@RequestBody PostPostRequestBody postPostRequestBody, Authentication authentication) {
		logger.info("POST /api/v1/posts");
		var post = postService.createPost(postPostRequestBody, (UserEntity)authentication.getPrincipal());
		return ResponseEntity.ok(post);
		// var -> 직관적으로도 알수있는 변수명인 경우 자바10의 기능 var의 타입추론 기능 사용
		// 남발하지않는게 좋고 팀마다 컨벤션이 다를수있기 떄문에 고려해야함.
	} 
	
	@PatchMapping("/{postId}")
	public ResponseEntity<Post> updatePost(
			@PathVariable Long postId, @RequestBody PostPatchRequestBody postPatchRequestBody , Authentication authentication)  {
		logger.info("POST /api/v1/posts/{}", postId);
		var post = 
			postService.updatePost(postId, postPatchRequestBody, (UserEntity)authentication.getPrincipal());
		return ResponseEntity.ok(post);
	} 
	
	@DeleteMapping("/{postId}")
	public ResponseEntity<Void> deletePost(@PathVariable Long postId , Authentication authentication)  
	{
		logger.info("DELETE /api/v1/posts/{}", postId);
		postService.deletePost(postId,  (UserEntity)authentication.getPrincipal());
		return ResponseEntity.noContent().build();
	} 
	
}
