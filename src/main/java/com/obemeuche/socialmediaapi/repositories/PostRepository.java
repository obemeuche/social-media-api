package com.obemeuche.socialmediaapi.repositories;

import com.obemeuche.socialmediaapi.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findPostByCreatedDate(LocalDateTime creationDate);

    List<Post> findPostByCreatedDateBetween(LocalDateTime lastSevenDays, LocalDateTime currentDate);

//    List<Post> findPostByCreatedDateBetween

//    @Query(value = "SELECT * FROM user_post WHERE ", nativeQuery = true)
//    Post findDefaultPost();
}
