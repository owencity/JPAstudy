package jjon.pop.model;

import java.util.Objects;

public record PostPatchRequestBody(String body) {}
// 자바 16 기능 record -> 아래 코드들 다 생략 가능하다.
// 기본적으로 record 의 객체는 불변성이라 자동으로 final로 정의되어 수정,삭제에는 쓰지않는다.
// 근데 어차피 DTO따로작성하는거면 사용안하는것도 괜찮을지도?
// 불변객체라 JPA Entity 와 어울리지 않는다.
//보일러 플레이트 코드
//최소한의 변경(인자, 혹은 결과 타입)으로 여러 곳에서 재사용 되면 반복적으로 비슷한 형태를 가지고 있는 코드
//→ getter, setter, equals, hashCode, toString 등이 여기에 해당
//public class PostPostRequestBody {
//	
//	private String body;
//	
//	public PostPostRequestBody(String body) {
//		this.body = body;
//	}
//	
//	public PostPostRequestBody() {
//		
//	}
//
//	public String getBody() {
//		return body;
//	}
//
//	public void setBody(String body) {
//		this.body = body;
//	}
//
//	@Override
//	public int hashCode() {
//		return Objects.hash(body);
//	}
//
//	@Override
//	public boolean equals(Object obj) {
//		if (this == obj)
//			return true;
//		if (obj == null)
//			return false;
//		if (getClass() != obj.getClass())
//			return false;
//		PostPostRequestBody other = (PostPostRequestBody) obj;
//		return Objects.equals(body, other.body);
//	}
//	
//	
//}

