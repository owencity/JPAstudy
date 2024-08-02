package jjon.pop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jjon.pop.entity.PostEntity;

@Repository
public interface PostEntityRepository extends JpaRepository<PostEntity, Long> { // <Entity 명, Key 값>
	
	

}
