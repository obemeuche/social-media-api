package com.obemeuche.socialmediaapi.repositories;

import com.obemeuche.socialmediaapi.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
