package jjon.pop.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import jjon.pop.config.exception.follow.FollowAlreadyExistException;
import jjon.pop.config.exception.follow.FollowNotFoundException;
import jjon.pop.config.exception.follow.InvalidFollowException;
import jjon.pop.config.exception.post.PostNotFoundException;
import jjon.pop.config.exception.user.UserAlreadyExistException;
import jjon.pop.config.exception.user.UserNotAllowedException;
import jjon.pop.config.exception.user.UserNotFoundException;
import jjon.pop.entity.FollowEntity;
import jjon.pop.entity.LikeEntity;
import jjon.pop.entity.PostEntity;
import jjon.pop.entity.UserEntity;
import jjon.pop.model.Post;
import jjon.pop.model.UserPatchRequestBody;
import jjon.pop.model.user.Follower;
import jjon.pop.model.user.LikedUser;
import jjon.pop.model.user.User;
import jjon.pop.model.user.UserAuthenticationResponse;
import jjon.pop.repository.FollowEntityRepository;
import jjon.pop.repository.LikeEntityRepository;
import jjon.pop.repository.PostEntityRepository;
import jjon.pop.repository.UserEntityRepository;

@Service
public class UserService implements UserDetailsService {
	
	@Autowired private UserEntityRepository userEntityRepository;
	@Autowired private BCryptPasswordEncoder passwordEncoder;
	@Autowired private JwtService jwtService;
	@Autowired private FollowEntityRepository followEntityRepository;
	@Autowired private PostEntityRepository postEntityRepository;
	@Autowired private LikeEntityRepository likeEntityRepository;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return userEntityRepository
				.findByUsername(username)
				.orElseThrow(() -> new UserNotFoundException(username));
	}
	
	public User signUp(String username , String password) {
		 userEntityRepository
				.findByUsername(username)
				.ifPresent(
						user -> {
							throw new UserAlreadyExistException();
						});
		 
		 var userEntity =  userEntityRepository.save(UserEntity.of(username, passwordEncoder.encode(password)));
		 // 패스워드 암호화는 모든 서비스의 기본 , Bcrypt(단방향 암호화 알고리즘)
		 return User.from(userEntity);
	}

	public UserAuthenticationResponse authenticate( String username, String password) {
		var userEntity = 
				userEntityRepository
				.findByUsername(username)
				.orElseThrow(() -> new UserNotFoundException(username));
		
		if (passwordEncoder.matches(password, userEntity.getPassword())) {
			var accessToken = jwtService.generateAccessToken(userEntity);
			return new UserAuthenticationResponse(accessToken);
		} else {
			throw new UserNotFoundException();
		}
	}

	public List<User> getUsers(String query, UserEntity currentUser) {
		List<UserEntity> userEntities;
		
		if(query != null && !query.isBlank()) {
			// TODO : query 검색어 기반 , 해당 검색어가 username에 포함되어 있는 유저목록 가져오기
			userEntities = userEntityRepository.findByUsernameContaining(query);
		}else {
			userEntities = userEntityRepository.findAll();
		}
		
		return userEntities.stream().map(
				userEntity -> getUserWithFollowingStatus(userEntity, currentUser)).toList();
	}

	public User getUser(String username, UserEntity currentUser) {
		var userEntity = 
				userEntityRepository
				.findByUsername(username)
				.orElseThrow(() -> new UserNotFoundException(username));
		return getUserWithFollowingStatus(userEntity , currentUser);
	}
	
	private User getUserWithFollowingStatus(UserEntity userEntity, UserEntity currentUser) {
		var isFollowing = 
				followEntityRepository.findByFollowerAndFollowing(currentUser, userEntity).isPresent();
		return User.from(userEntity, isFollowing);
	}

	public User updateUser(String username, UserPatchRequestBody userPatchRequestBody, UserEntity currentUser) {
		var userEntity = 
				userEntityRepository
				.findByUsername(username)
				.orElseThrow(() -> new UserNotFoundException(username));
		
		if(!userEntity.equals(currentUser)) {
			throw new UserNotAllowedException();
		} 
		
		if (userPatchRequestBody.description() != null) {
			userEntity.setDescription(userPatchRequestBody.description());
		}
		return User.from(userEntityRepository.save(userEntity));
	}
	
	@Transactional
	public User follow(String username, UserEntity currentUser) {
		var following = 
				userEntityRepository
				.findByUsername(username)
				.orElseThrow(() -> new UserNotFoundException(username));
		
		if (following.equals(currentUser)) {
			throw new InvalidFollowException("A user cannot follow themeslves.");
		}
		
		followEntityRepository.findByFollowerAndFollowing(currentUser, following)
		.ifPresent(
				follow -> {
					throw new FollowAlreadyExistException();
				});
		followEntityRepository.save(
				FollowEntity.of(currentUser, following));
		
		following.setFollowersCount(following.getFollowersCount()+1);
		currentUser.setFolloswingCount(following.getFollowingsCount()+1);
		
		userEntityRepository.saveAll(List.of(following, currentUser));
		return User.from(following, true);
	}
	
	@Transactional
	public User unFollow(String username, UserEntity currentUser) {
		var following = 
				userEntityRepository
				.findByUsername(username)
				.orElseThrow(() -> new UserNotFoundException(username));
		
		if (following.equals(currentUser)) {
			throw new InvalidFollowException("A user cannot unfollow themeslves.");
		}
		
		var followEntity =
		followEntityRepository.
		findByFollowerAndFollowing(currentUser, following)
		.orElseThrow(() -> new FollowNotFoundException(currentUser, following));
				
		followEntityRepository.delete(followEntity);
		
		following.setFollowersCount(Math.max(0, following.getFollowersCount()-1));
		currentUser.setFolloswingCount(Math.max(0, following.getFollowingsCount()-1));
		
		userEntityRepository.saveAll(List.of(following, currentUser));
		return User.from(following, false);
	}

	public List<Follower> getFollowersByUsername(String username, UserEntity currentUser) {
		var following = 
				userEntityRepository
				.findByUsername(username)
				.orElseThrow(() -> new UserNotFoundException(username));
		var followEntities = followEntityRepository.findByFollowing(following);
		return followEntities.stream().map(
				follow -> Follower.from(getUserWithFollowingStatus(follow.getFollower(), currentUser),
						follow.getCreatedDateTime())).toList();
	}
	
	public List<User> getFollowingsByUsername(String username, UserEntity currentUser) {
		var follower = 
				userEntityRepository
				.findByUsername(username)
				.orElseThrow(() -> new UserNotFoundException(username));
		var followEntities = followEntityRepository.findByFollower(follower);
		return followEntities.stream().map(
				follow -> getUserWithFollowingStatus(follow.getFollowing(), currentUser))
				.toList();
	}

	public List<LikedUser> getLikedUsersByPostId(Long postId, UserEntity currentUser) {
		var postEntity = postEntityRepository.findById(postId) .orElseThrow(() -> new PostNotFoundException(postId));
		
		var likeEntities = likeEntityRepository.findByPost(postEntity);
		return likeEntities.stream().map(
				likeEntity -> getLikedUserWithFollowingStatus(likeEntity, postEntity, currentUser)).
				toList();
		}
	
	public List<LikedUser> getLikedUsersByUser(String username, UserEntity currentUser) {
		var userEntity = 
				userEntityRepository
				.findByUsername(username)
				.orElseThrow(() -> new UserNotFoundException(username));
		
			var postEntities = postEntityRepository.findByUser(userEntity);
			return postEntities.stream()
					.flatMap(
					postEntity ->  
					likeEntityRepository.findByPost(postEntity).stream()
						.map(
						likeEntity -> 
						getLikedUserWithFollowingStatus(likeEntity, postEntity, currentUser))).
					toList();
		}
	
		private LikedUser getLikedUserWithFollowingStatus(
				LikeEntity likeEntity, PostEntity postEntity, UserEntity currentUser) {
			var likedUserEntity = likeEntity.getUser();
			var userWithFollowingStatus = getUserWithFollowingStatus(likedUserEntity, currentUser);
			return LikedUser.from(userWithFollowingStatus, postEntity.getPostId(), likedUserEntity.getCreatedDateTime());
		}
}



