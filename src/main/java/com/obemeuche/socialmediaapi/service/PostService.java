package com.obemeuche.socialmediaapi.service;

import com.obemeuche.socialmediaapi.entities.Post;
import com.obemeuche.socialmediaapi.entities.pageCriteria.PostPage;
import com.obemeuche.socialmediaapi.request.CreatePostRequest;
import com.obemeuche.socialmediaapi.response.PostResponse;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

public interface PostService {

    ResponseEntity<PostResponse> makePost(CreatePostRequest postRequest);

    ResponseEntity<PostResponse> viewPost(Long id);

    ResponseEntity<?> likePost(Long id);

    Page<Post> fetchPage(PostPage postPage);

    String deletePost(Long id);
}
