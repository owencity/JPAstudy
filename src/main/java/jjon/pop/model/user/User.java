package jjon.pop.model.user;

import java.time.ZonedDateTime;

import jjon.pop.entity.UserEntity;

public record User(
		Long userId,
		String username,
		String profile,
		String description,
		ZonedDateTime createdDateTime,
		ZonedDateTime updatedDateTime) {
	
	public static User from(UserEntity userEntity) {
		return new User(
				userEntity.getUserId(),
				userEntity.getUsername(),
				userEntity.getProfile(),
				userEntity.getDescription(),
				userEntity.getCreatedDateTime(),
				userEntity.getUpdatedDateTime());
	}

}
