package jjon.pop.entity;

import java.time.ZonedDateTime;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "\"Like\"",
uniqueConstraints = {@UniqueConstraint(columnNames = {"userid", "postid"})})
public class LikeEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long likeId;
	
	@Column
	private ZonedDateTime createdDateTime;
	
	@ManyToOne
	@JoinColumn(name = "userId")
	private UserEntity user;
	
	@ManyToOne
	@JoinColumn(name = "postId")
	private PostEntity post;
	
	
	

	public Long getLikeId() {
		return likeId;
	}


	public void setLikeId(Long likeId) {
		this.likeId = likeId;
	}


	public ZonedDateTime getCreatedDateTime() {
		return createdDateTime;
	}


	public void setCreatedDateTime(ZonedDateTime createdDateTime) {
		this.createdDateTime = createdDateTime;
	}


	public UserEntity getUser() {
		return user;
	}


	public void setUser(UserEntity user) {
		this.user = user;
	}


	public PostEntity getPost() {
		return post;
	}


	public void setPost(PostEntity post) {
		this.post = post;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(createdDateTime, likeId, post, user);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LikeEntity other = (LikeEntity) obj;
		return Objects.equals(createdDateTime, other.createdDateTime) && Objects.equals(likeId, other.likeId)
				&& Objects.equals(post, other.post) && Objects.equals(user, other.user);
	}

	public static LikeEntity of( UserEntity user, PostEntity post) {
		var reply = new LikeEntity();
		reply.setUser(user);
		reply.setPost(post);
		return reply;
	}

	@PrePersist // 비유: 자동으로 기록되는 일기장
	// 일기장을 처음 쓸 때 (엔티티 생성), 날짜와 함께 "오늘 일기 시작!"이라고 자동으로 첫 줄을 써주는 기능
	private void prePersist() {
		this.createdDateTime = ZonedDateTime.now();
	}
}
