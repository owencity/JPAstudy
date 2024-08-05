package jjon.pop.entity;

import java.time.ZonedDateTime;
import java.util.Objects;

import org.hibernate.annotations.ManyToAny;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "follow",
uniqueConstraints = {@UniqueConstraint(columnNames = {"follower", "following"})})
public class FollowEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long followId;
	
	@Column
	private ZonedDateTime createdDateTime;
	
	@ManyToOne
	@JoinColumn(name = "follower")
	private UserEntity follower;
	
	@ManyToOne
	@JoinColumn(name = "following")
	private UserEntity following;
	
	public Long getFollowId() {
		return followId;
	}

	public void setFollowId(Long followId) {
		this.followId = followId;
	}

	public ZonedDateTime getCreatedDateTime() {
		return createdDateTime;
	}

	public void setCreatedDateTime(ZonedDateTime createdDateTime) {
		this.createdDateTime = createdDateTime;
	}

	public UserEntity getFollower() {
		return follower;
	}

	public void setFollower(UserEntity follower) {
		this.follower = follower;
	}

	public UserEntity getFollowing() {
		return following;
	}

	public void setFollowing(UserEntity following) {
		this.following = following;
	}

	
	
	@Override
	public int hashCode() {
		return Objects.hash(createdDateTime, followId, follower, following);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FollowEntity other = (FollowEntity) obj;
		return Objects.equals(createdDateTime, other.createdDateTime) && Objects.equals(followId, other.followId)
				&& Objects.equals(follower, other.follower) && Objects.equals(following, other.following);
	}

	public static FollowEntity of( UserEntity follower, UserEntity following) {
		var follow = new FollowEntity();
		follow.setFollower(follower);
		follow.setFollowing(following);
		return follow;
	}

	@PrePersist // 비유: 자동으로 기록되는 일기장
	// 일기장을 처음 쓸 때 (엔티티 생성), 날짜와 함께 "오늘 일기 시작!"이라고 자동으로 첫 줄을 써주는 기능
	private void prePersist() {
		this.createdDateTime = ZonedDateTime.now();
	}
}
