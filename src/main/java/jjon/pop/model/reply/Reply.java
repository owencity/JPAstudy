package jjon.pop.model.reply;

import java.time.ZonedDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;

import jjon.pop.entity.ReplyEntity;
import jjon.pop.model.Post;
import jjon.pop.model.user.User;

@JsonInclude(JsonInclude.Include.NON_NULL) // // JSON 직렬화 시 null 값 필드 제외
public record Reply(
		Long replyId, 
		String body,
		User user,
		Post post,
		ZonedDateTime createdDateTime,
		ZonedDateTime updatedDateTime,
		ZonedDateTime deletedDateTime) {

			public static Reply from(ReplyEntity replyEntity) {
				 // PostEntity를 Post로 변환
				return new Reply ( 
						replyEntity.getReplyId(),
						replyEntity.getBody(),
						User.from(replyEntity.getUser()),
						Post.from(replyEntity.getPost()),
						replyEntity.getCreatedDateTime(),
						replyEntity.getUpdatedDateTime(),
						replyEntity.getDeletedDateTime());
						
			}
		
	}

