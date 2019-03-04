package edu.ecnu.scsse.pizza.data.repository;

import edu.ecnu.scsse.pizza.data.domain.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentJpaRepository extends JpaRepository<CommentEntity,Integer> {

}