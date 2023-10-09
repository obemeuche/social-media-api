package com.obemeuche.socialmediaapi.service;

import com.obemeuche.socialmediaapi.response.UserResponse;
import org.springframework.http.ResponseEntity;

import java.security.Principal;

public interface UserService {
    UserResponse viewProfile(Principal principal);

    ResponseEntity<?> followUser(Long followerId, Long followingId);

    ResponseEntity<?> unfollowUser(Long followerId, Long followingId);
}
