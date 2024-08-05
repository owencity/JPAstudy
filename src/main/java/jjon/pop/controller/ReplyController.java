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
import org.springframework.web.bind.annotation.RestController;

import jjon.pop.entity.UserEntity;
import jjon.pop.model.Post;
import jjon.pop.model.ReplyPatchRequestBody;
import jjon.pop.model.ReplyPostRequestBody;
import jjon.pop.model.reply.Reply;
import jjon.pop.service.ReplyService;

@RestController
@RequestMapping("/api/v1/posts/{postId}/replies")
public class ReplyController {
	
	
	@Autowired private ReplyService replyService;
	
	@GetMapping
	public ResponseEntity<List<Reply>> getRepliesByPostId(@PathVariable Long postId) {
		var replies = replyService.getRepliesByPostId(postId);
		return ResponseEntity.ok(replies);
	}

	@PostMapping
	public ResponseEntity<Reply> createReply(
			@PathVariable Long postId, @RequestBody ReplyPostRequestBody replyPostRequestBody, Authentication authentication) {
		var reply = replyService.createReply(postId, replyPostRequestBody, (UserEntity)authentication.getPrincipal());
		return ResponseEntity.ok(reply);
		// var -> 직관적으로도 알수있는 변수명인 경우 자바10의 기능 var의 타입추론 기능 사용
		// 남발하지않는게 좋고 팀마다 컨벤션이 다를수있기 떄문에 고려해야함.
	} 
	
	@PatchMapping("/{replyId}")
	public ResponseEntity<Reply> updatePost(
			@PathVariable Long postId, @PathVariable Long replyId, @RequestBody ReplyPatchRequestBody replyPatchRequestBody , Authentication authentication)  {
		var reply = 
			replyService.updateReply(postId, replyId, replyPatchRequestBody, (UserEntity)authentication.getPrincipal());
		return ResponseEntity.ok(reply);
	} 
	
	@DeleteMapping("/{replyId}")
	public ResponseEntity<Void> deletePost(@PathVariable Long postId ,  @PathVariable Long replyId, Authentication authentication)  
	{
		replyService.deleteReply(postId, replyId, (UserEntity)authentication.getPrincipal());
		return ResponseEntity.noContent().build();
	} 
	
}
