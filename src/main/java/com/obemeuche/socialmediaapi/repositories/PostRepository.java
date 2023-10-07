package com.obemeuche.socialmediaapi.repositories;

import com.obemeuche.socialmediaapi.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
