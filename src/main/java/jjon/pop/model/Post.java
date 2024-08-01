package jjon.pop.model;

import java.time.ZonedDateTime;
import java.util.Objects;

//public record Post(Long postId, String body, ZonedDateTime createDateTime) {}
// 
public class Post {
	
	private Long postId;
	private String body;
	private ZonedDateTime createdTime;
	// 서비스 규모와 맞게 데이터타입 맞추는게좋다 , 글로벌 서비스 -> Long, ZonedDateTime 
	
	public Post(Long postId, String body, ZonedDateTime createdTime) {
		this.postId = postId;
		this.body = body;
		this.createdTime = createdTime;
	}

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

	public ZonedDateTime getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(ZonedDateTime createdTime) {
		this.createdTime = createdTime;
	}

	@Override
	public int hashCode() {
		return Objects.hash(body, createdTime, postId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Post other = (Post) obj;
		return Objects.equals(body, other.body) && Objects.equals(createdTime, other.createdTime)
				&& Objects.equals(postId, other.postId);
	}

	@Override
	public String toString() {
		return "Post [postId=" + postId + ", body=" + body + ", createdTime=" + createdTime + "]";
	}
	
	
	
	
}
