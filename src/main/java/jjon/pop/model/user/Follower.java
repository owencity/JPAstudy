package jjon.pop.model.user;

import java.time.ZonedDateTime;

import jjon.pop.entity.UserEntity;

public record Follower(
		Long userId,
		String username,
		String profile,
		String description,
		Long followersCount,
		Long followingsCount,
		ZonedDateTime createdDateTime,
		ZonedDateTime updatedDateTime,
		ZonedDateTime followedDataTime,
		Boolean isFollowing) {
	
	
	public static Follower from(User user, ZonedDateTime followedDataTime) {
		return new Follower(
				user.userId(),
				user.username(),
				user.profile(),
				user.description(),
				user.followersCount(),
				user.followingsCount(),
				user.createdDateTime(),
				user.updatedDateTime(),
				followedDataTime,
				user.isFollowing());
	}

}
