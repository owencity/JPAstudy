package jjon.pop.model.user;

import java.time.ZonedDateTime;

import jjon.pop.entity.UserEntity;

public record LikedUser(
		Long userId,
		String username,
		String profile,
		String description,
		Long followersCount,
		Long followingsCount,
		ZonedDateTime createdDateTime,
		ZonedDateTime updatedDateTime,
		Boolean isFollowing,
		Long likedPostId,
		ZonedDateTime likedDateTime) {
	
	public static LikedUser from(User user, Long likedPostId, ZonedDateTime likedDateTime) {
		return new LikedUser(
				user.userId(),
				user.username(),
				user.profile(),
				user.description(),
				user.followersCount(),
				user.followingsCount(),
				user.createdDateTime(),
				user.updatedDateTime(),
				user.isFollowing(),
				likedPostId,
				likedDateTime);
	}

}
