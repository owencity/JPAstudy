package jjon.pop.entity;

import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.Objects;
import java.util.Random;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;


@Entity
@Table(name = "\"user\"", // postgresql 에서는 user가 기본 예약어라 따로 \ 따로붙여줌
indexes = {@Index(name = "user_username_idx", columnList = "username", unique = true)}) 
@SQLDelete(sql = "UPDATE \"user\" SET deleteddatetime = CURRENT_TIMESTAMP WHERE postid = ?")
@SQLRestriction("deleteddatetime is NULL")
public class UserEntity implements UserDetails{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userId;
	
	@Column(nullable = false)
	private String username;
	
	@Column(nullable = false) 
	private String password;
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return null;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}
	@Column private String profile;
	
	@Column private String description;
	
	@Column private Long followersCount = 0L;
	
	@Column private Long followingsCount = 0L;
	
	@Column private ZonedDateTime createdDateTime;
	
	@Column private ZonedDateTime updatedDateTime;
	
	@Column private ZonedDateTime deletedDateTime;

	
	
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getProfile() {
		return profile;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getFollowersCount() {
		return followersCount;
	}

	public void setFollowersCount(Long followerCount) {
		this.followersCount = followerCount;
	}

	public Long getFollowingsCount() {
		return followingsCount;
	}

	public void setFolloswingCount(Long followingCount) {
		this.followingsCount = followingCount;
	}

	public ZonedDateTime getCreatedDateTime() {
		return createdDateTime;
	}

	public void setCreatedDateTime(ZonedDateTime createdDateTime) {
		this.createdDateTime = createdDateTime;
	}

	public ZonedDateTime getUpdatedDateTime() {
		return updatedDateTime;
	}

	public void setUpdatedDateTime(ZonedDateTime updatedDateTime) {
		this.updatedDateTime = updatedDateTime;
	}

	public ZonedDateTime getDeletedDateTime() {
		return deletedDateTime;
	}

	public void setDeletedDateTime(ZonedDateTime deletedDateTime) {
		this.deletedDateTime = deletedDateTime;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	
	

	@Override
	public int hashCode() {
		return Objects.hash(createdDateTime, deletedDateTime, description, followersCount, followingsCount, password,
				profile, updatedDateTime, userId, username);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserEntity other = (UserEntity) obj;
		return Objects.equals(createdDateTime, other.createdDateTime)
				&& Objects.equals(deletedDateTime, other.deletedDateTime)
				&& Objects.equals(description, other.description)
				&& Objects.equals(followersCount, other.followersCount)
				&& Objects.equals(followingsCount, other.followingsCount) && Objects.equals(password, other.password)
				&& Objects.equals(profile, other.profile) && Objects.equals(updatedDateTime, other.updatedDateTime)
				&& Objects.equals(userId, other.userId) && Objects.equals(username, other.username);
	}

	public static UserEntity of(String username, String password) {
		var userEntity = new UserEntity();
		userEntity.setUsername(username);
		userEntity.setPassword(password);
		
		//Avatar Placeholder 서비스 기반
		// 랜덤한 프로필 사진 설정( 1  ~ 100)
		userEntity.setProfile(
				"https://avatar.iran.liara.run/public/" + new Random().nextInt(100));
		
		return userEntity;
	}
	
	@PrePersist // 비유: 자동으로 기록되는 일기장
	// 일기장을 처음 쓸 때 (엔티티 생성), 날짜와 함께 "오늘 일기 시작!"이라고 자동으로 첫 줄을 써주는 기능
	private void prePersist() {
		this.createdDateTime = ZonedDateTime.now();
		this.updatedDateTime = this.createdDateTime;
	}
	
	@PreUpdate // 비유: 자동으로 기록되는 일기장
	// 일기를 수정할 때마다 (엔티티 업데이트), 현재 날짜와 함께 "수정됨"이라고 자동으로 기록해주는 기능
	private void preUpdate() {
		this.updatedDateTime = ZonedDateTime.now();
	}
	

	
// 다음은 userDetails 에 있는 메서드 인데 defalut 메서드라 따로 구현하지않아도 기본 제공한다 , 자바8부터 제공함. 
// 굳이 쓰려면 오버라이딩해서 쓰면된다. 시큐리티버전마다 다를수 있어 확인해야할거같다.
//	default boolean isAccountNonExpired() {
//		return true;
//	}
//
//	/**
//	 * Indicates whether the user is locked or unlocked. A locked user cannot be
//	 * authenticated.
//	 * @return <code>true</code> if the user is not locked, <code>false</code> otherwise
//	 */
//	default boolean isAccountNonLocked() {
//		return true;
//	}
//
//	/**
//	 * Indicates whether the user's credentials (password) has expired. Expired
//	 * credentials prevent authentication.
//	 * @return <code>true</code> if the user's credentials are valid (ie non-expired),
//	 * <code>false</code> if no longer valid (ie expired)
//	 */
//	default boolean isCredentialsNonExpired() {
//		return true;
//	}
//
//	/**
//	 * Indicates whether the user is enabled or disabled. A disabled user cannot be
//	 * authenticated.
//	 * @return <code>true</code> if the user is enabled, <code>false</code> otherwise
//	 */
//	default boolean isEnabled() {
//		return true;
//	}
	
	

}
