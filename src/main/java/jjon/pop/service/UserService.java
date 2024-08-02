package jjon.pop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import jjon.pop.config.exception.user.UserAlreadyExistException;
import jjon.pop.config.exception.user.UserNotFoundException;
import jjon.pop.entity.UserEntity;
import jjon.pop.model.user.User;
import jjon.pop.repository.UserEntityRepository;

@Service
public class UserService implements UserDetailsService {
	
	@Autowired private UserEntityRepository userEntityRepository;
	@Autowired private BCryptPasswordEncoder passwordEncoder;

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

}
