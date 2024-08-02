package jjon.pop.service;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import jjon.pop.config.exception.post.PostNotFoundException;
import jjon.pop.entity.PostEntity;
import jjon.pop.model.Post;
import jjon.pop.model.PostPatchRequestBody;
import jjon.pop.model.PostPostRequestBody;
import jjon.pop.repository.PostEntityRepository;

@Service
public class PostService {
	
	@Autowired private PostEntityRepository postEntityRepository;

	private static final List<Post> posts = new ArrayList<>();
	
	
	public List<Post> getPosts() {
		var postEntities = postEntityRepository.findAll();
		
		return postEntities.stream().map(Post::from).toList();
	}
	
	public Post getPostByPostId(Long postId) {
			var postEntity = postEntityRepository
					.findById(postId) // OPtional postId를 반환
					.orElseThrow(
							() -> new PostNotFoundException(postId));
			
	return Post.from(postEntity);

	}

	public Post createPost(PostPostRequestBody postPostRequestBody) {
		var postEntity = new PostEntity();
		postEntity.setBody(postPostRequestBody.body());
		var savedPostEntity = postEntityRepository.save(postEntity);
		return Post.from(savedPostEntity);
		
		
	}

	public Post updatePost(Long postId, PostPatchRequestBody postPatchRequestBody) {
		var postEntity = postEntityRepository
				.findById(postId) // OPtional postId를 반환
				.orElseThrow(
						() -> new PostNotFoundException(postId));
		postEntity.setBody(postPatchRequestBody.body());
		var updatedPostEntity = postEntityRepository.save(postEntity);
		return Post.from(updatedPostEntity);
		
		
//		Optional<Post> postOptional = posts.stream().filter(post -> postId.equals(post.getPostId())).findFirst();
		
//		if(postOptional.isPresent()) {
//			Post postToUpdate = postOptional.get();
//			postToUpdate.setBody(postPatchRequestBody.body());
//			return postToUpdate;
			
//			isPresent()는 Optional이 비어 있지 않은지 확인하는 전통적인 방법이지만, 
//			Java 11 이후에는 ifPresentOrElse()와 같은 더 명확한 방식이 도입되었습니다. 이를 통해 코드의 가독성과 명확성을 높일 수 있습니다.
//			isPresent -> true ,false 반환 ElseGet() -> 값 없을 시 대체값 반환.
		
	}

	public void deletePost(Long postId) {
		
		var postEntity = postEntityRepository
				.findById(postId) // OPtional postId를 반환
				.orElseThrow(
						() -> new PostNotFoundException(postId));	
		postEntityRepository.delete(postEntity);
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

