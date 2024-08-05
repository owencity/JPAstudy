package jjon.pop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jjon.pop.entity.PostEntity;
import jjon.pop.entity.ReplyEntity;
import jjon.pop.entity.UserEntity;

import java.util.List;


@Repository
public interface ReplyEntityRepository extends JpaRepository<ReplyEntity, Long> { // <Entity 명, Key 값>
	List<ReplyEntity> findByUser(UserEntity user);
	List<ReplyEntity> findByPost(PostEntity post);
}
