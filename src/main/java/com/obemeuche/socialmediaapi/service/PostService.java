package com.obemeuche.socialmediaapi.service;

import com.obemeuche.socialmediaapi.request.CreatePostRequest;
import com.obemeuche.socialmediaapi.response.PostResponse;
import org.springframework.http.ResponseEntity;

public interface PostService {

    PostResponse makePost(CreatePostRequest postRequest);

    PostResponse viewPost(Long id);

    ResponseEntity<?> likePost(Long id);
}
