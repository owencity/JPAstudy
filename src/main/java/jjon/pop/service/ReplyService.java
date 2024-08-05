package jjon.pop.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import jjon.pop.config.exception.post.PostNotFoundException;
import jjon.pop.config.exception.reply.ReplyNotFoundException;
import jjon.pop.config.exception.user.UserNotAllowedException;
import jjon.pop.config.exception.user.UserNotFoundException;
import jjon.pop.entity.PostEntity;
import jjon.pop.entity.ReplyEntity;
import jjon.pop.entity.UserEntity;
import jjon.pop.model.Post;
import jjon.pop.model.PostPatchRequestBody;
import jjon.pop.model.PostPostRequestBody;
import jjon.pop.model.ReplyPatchRequestBody;
import jjon.pop.model.ReplyPostRequestBody;
import jjon.pop.model.reply.Reply;
import jjon.pop.repository.PostEntityRepository;
import jjon.pop.repository.ReplyEntityRepository;
import jjon.pop.repository.UserEntityRepository;

@Service
public class ReplyService {
	
	@Autowired private PostEntityRepository postEntityRepository;
	@Autowired private UserEntityRepository userEntityRepository;
	@Autowired private ReplyEntityRepository replyEntityRepository;
	
	public List<Reply> getRepliesByPostId(Long postId) {
		var postEntity = postEntityRepository.findById(postId).orElseThrow(() -> new PostNotFoundException(postId));
		var replyEntities = replyEntityRepository.findByPost(postEntity);
		return replyEntities.stream().map(Reply::from).toList();
	}
	
	@Transactional
	public Reply createReply(Long postId, ReplyPostRequestBody replyPostRequestBody, UserEntity currentUser) {
		var postEntity = postEntityRepository
				.findById(postId) // OPtional postId를 반환
				.orElseThrow(
						() -> new PostNotFoundException(postId));
		
		var replyEntity = 
				replyEntityRepository.save(
						ReplyEntity.of(replyPostRequestBody.body(), currentUser, postEntity));
		
		postEntity.setRepliesCount(postEntity.getRepliesCount() + 1);
		
		return Reply.from(replyEntity);
	}

	public Reply updateReply(
			Long postId, 
			Long replyId, 
			ReplyPatchRequestBody replyPatchRequestBody,
			UserEntity currentUser) {
				postEntityRepository.findById(postId) .orElseThrow(() -> new PostNotFoundException(postId));
		var replyEntity = replyEntityRepository.findById(replyId).orElseThrow(() -> new ReplyNotFoundException(replyId));
		
		if(!replyEntity.getUser().equals(currentUser)) {
			throw new UserNotAllowedException();
		}
		
		replyEntity.setBody(replyPatchRequestBody.body());
		return Reply.from(replyEntityRepository.save(replyEntity));
	}
	
	@Transactional
	public void deleteReply(Long postId, Long replyId, UserEntity currentUser) {
		var postEntity = postEntityRepository.findById(postId).orElseThrow(() -> new PostNotFoundException(postId));
		var replyEntity = 
				replyEntityRepository.findById(replyId) .orElseThrow(() -> new PostNotFoundException(replyId)); // OPtional postId를 반환
		
		
		if(!replyEntity.getUser().equals(currentUser)) {
			throw new UserNotAllowedException();
		}
		
		replyEntityRepository.delete(replyEntity);
		
		postEntity.setRepliesCount(Math.max(0, postEntity.getRepliesCount()-1));
		postEntityRepository.save(postEntity);
	}

	public List<Reply> getRepliesByUser(String username) {
		var userEntity = 
				userEntityRepository
				.findByUsername(username)
				.orElseThrow(() -> new UserNotFoundException(username));
		
		var replyEntities = replyEntityRepository.findByUser(userEntity);
		return replyEntities.stream().map(Reply::from).toList();
	}

	
		
//		Optional<Post> postOptional = 
//				posts.stream().filter(post -> postId.equals(post.getPostId())).findFirst();
//
//		if(postOptional.isPresent()) {
//			posts.remove(postOptional.get());
//		}else {
//			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found");
//		}
//	}
//}
	//	Optional<Post> matchingPost = posts.stream().filter(post -> postId.equals(post.getPostId())).findFirst();
//	Optional을 사용하는 이유는 찾고자 하는 게시물이 존재하지 않을 수 있는 상황을 우아하게 처리하기 위함입니다. Optional 클래스는 Java 8에서 도입되었으며, 
//	null 값을 처리하는 더 안전하고 명시적인 방법을 제공합
//		
//* 		1.명시적인 의사표현 -> Optional은 값이 있을수도 있고 없을수도 있다는 가능성을 명시적으로 표현
//* 		2.NullPointerException  방지 : 직접 null을 반환하거나 처리X -> 큰 코드베이스 우용
//* 		3.코드의 의도 명확성 부여 및 다른 개발자에게 코드의도 파악 쉽게할수있음. 		
}

