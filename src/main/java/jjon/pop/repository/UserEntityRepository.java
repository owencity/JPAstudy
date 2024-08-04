package jjon.pop.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jjon.pop.entity.UserEntity;


@Repository
public interface UserEntityRepository extends JpaRepository<UserEntity, Long> { // <Entity 명, Key 값>
	
	Optional<UserEntity> findByUsername(String username);
	
	List<UserEntity>findByUsernameContaining(String username);
}
