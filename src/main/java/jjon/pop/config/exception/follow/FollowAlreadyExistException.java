package jjon.pop.config.exception.follow;

import org.springframework.http.HttpStatus;

import jjon.pop.config.exception.ClientErrorException;
import jjon.pop.entity.UserEntity;

public class FollowAlreadyExistException extends ClientErrorException{ 
	
	public FollowAlreadyExistException() {
		super(HttpStatus.CONFLICT, "Follow alredy exists");
	}
	
	public FollowAlreadyExistException(UserEntity follower , UserEntity following) {
		super(
				HttpStatus.CONFLICT, 
				"Follow with follower" 
				+ follower.getUsername()
				+" and following"
				+ following.getUsername()
				+ "User alredy exists" );
		}
	

}
