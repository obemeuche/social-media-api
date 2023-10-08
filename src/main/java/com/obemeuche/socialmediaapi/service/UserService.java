package com.obemeuche.socialmediaapi.service;

import com.obemeuche.socialmediaapi.response.UserResponse;

import java.security.Principal;

public interface UserService {
    UserResponse viewProfile(Principal principal);
}
