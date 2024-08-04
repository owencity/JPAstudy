package jjon.pop.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import jjon.pop.config.exception.user.UserAlreadyExistException;
import jjon.pop.config.exception.user.UserNotAllowedException;
import jjon.pop.config.exception.user.UserNotFoundException;
import jjon.pop.entity.UserEntity;
import jjon.pop.model.UserPatchRequestBody;
import jjon.pop.model.user.User;
import jjon.pop.model.user.UserAuthenticationResponse;
import jjon.pop.repository.UserEntityRepository;

@Service
public class UserService implements UserDetailsService {
	
	@Autowired private UserEntityRepository userEntityRepository;
	@Autowired private BCryptPasswordEncoder passwordEncoder;
	@Autowired private JwtService jwtService;
	
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

	public List<User> getUsers(String query) {
		List<UserEntity> userEntities;
		
		if(query != null && !query.isBlank()) {
			// TODO : query 검색어 기반 , 해당 검색어가 username에 포함되어 있는 유저목록 가져오기
			userEntities = userEntityRepository.findByUsernameContaining(query);
		}else {
			userEntities = userEntityRepository.findAll();
		}
		
		return userEntities.stream().map(User::from).toList();
	}

	public User getUser(String username) {
		var userEntity = 
				userEntityRepository
				.findByUsername(username)
				.orElseThrow(() -> new UserNotFoundException(username));
		return User.from(userEntity);
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
	

}
