package jjon.pop.config.exception.follow;

import org.springframework.http.HttpStatus;

import jjon.pop.config.exception.ClientErrorException;
import jjon.pop.entity.UserEntity;

public class FollowNotFoundException extends ClientErrorException{ 
	
	public FollowNotFoundException() {
		super(HttpStatus.NOT_FOUND, "Follow Not Found");
	}
	
	public FollowNotFoundException(UserEntity follower , UserEntity following) {
		super(
				HttpStatus.NOT_FOUND, 
				"Follow with follower" 
				+ follower.getUsername()
				+" and following"
				+ following.getUsername()
				+ "not found" );
	}
}
