package jjon.pop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jjon.pop.entity.PostEntity;
import jjon.pop.entity.UserEntity;

import java.util.List;


@Repository
public interface PostEntityRepository extends JpaRepository<PostEntity, Long> { // <Entity 명, Key 값>
	List<PostEntity> findByUser(UserEntity user);
	// 한명의 user는 여러개의 글 작성 가능 -> List 사용 , findByUser -> User가 가능한 이유 PostEntity 에 지정한 변수명이 User라서 findByUser로 사용가능
	// 예를들어 user가 아닌 username 으로 되있으면 findByUsername 으로 사용.
}
