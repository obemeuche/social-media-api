package com.obemeuche.socialmediaapi.service;

import com.obemeuche.socialmediaapi.request.CommentRequest;
import org.springframework.http.ResponseEntity;

public interface CommentService {
    ResponseEntity<?> commentOnPost(Long id, CommentRequest commentRequest);

    String deleteComment(Long id);
}
