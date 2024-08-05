package jjon.pop.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jjon.pop.entity.FollowEntity;
import jjon.pop.entity.LikeEntity;
import jjon.pop.entity.PostEntity;
import jjon.pop.entity.UserEntity;


@Repository
public interface FollowEntityRepository extends JpaRepository<FollowEntity, Long> { // <Entity 명, Key 값>
	List<FollowEntity> findByFollower(UserEntity follower);
	List<FollowEntity> findByFollowing(UserEntity following);
	Optional<FollowEntity>findByFollowerAndFollowing(UserEntity follower, UserEntity following);
}
