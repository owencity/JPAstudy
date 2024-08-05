package jjon.pop.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jjon.pop.entity.LikeEntity;
import jjon.pop.entity.PostEntity;
import jjon.pop.entity.UserEntity;


@Repository
public interface LikeEntityRepository extends JpaRepository<LikeEntity, Long> { // <Entity 명, Key 값>
	List<LikeEntity> findByUser(UserEntity user);
	List<LikeEntity> findByPost(PostEntity post);
	Optional<LikeEntity>findByUserAndPost(UserEntity user, PostEntity post);
}
