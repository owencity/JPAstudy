package jjon.pop.entity;

import java.time.ZonedDateTime;
import java.util.Objects;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

@Entity
@Table(name = "post")

@SQLDelete(sql = "UPDATE \"post\" SET deleteddatetime = CURRENT_TIMESTAMP WHERE postid = ?")
@SQLRestriction("deleteddatetime is NULL")
public class PostEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long postId;
	
	@Column(columnDefinition = "TEXT")
	private String body;
	
	@Column
	private ZonedDateTime createdDateTime;
	
	@Column
	private ZonedDateTime updatedDateTime;
	
	@Column
	private ZonedDateTime deletedDateTime;
	// 소프트 딜리트 . 내부통계용 (삭제시 외부에는 삭제된것으로보이나 내부에서는 일정기간 보유하는 케이스도있음)

	public Long getPostId() {
		return postId;
	}

	public void setPostId(Long postId) {
		this.postId = postId;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
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

	public void setUpatedDateTime(ZonedDateTime updatedDateTime) {
		this.updatedDateTime = updatedDateTime;
	}

	public ZonedDateTime getDeletedDateTime() {
		return deletedDateTime;
	}

	public void setDeleteDateTime(ZonedDateTime deleteDateTime) {
		this.deletedDateTime = deleteDateTime;
	}

	@Override
	public int hashCode() {
		return Objects.hash(body, createdDateTime, deletedDateTime, postId, updatedDateTime);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PostEntity other = (PostEntity) obj;
		return Objects.equals(body, other.body) && Objects.equals(createdDateTime, other.createdDateTime)
				&& Objects.equals(deletedDateTime, other.deletedDateTime) && Objects.equals(postId, other.postId)
				&& Objects.equals(updatedDateTime, other.updatedDateTime);
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
	
	// 번거로움 해소 해주고, 정확한기록, 변경이력 추적을 해주는 기능
	
	
}
