package com.obemeuche.socialmediaapi.service;

import com.obemeuche.socialmediaapi.request.SignUpRequest;
import com.obemeuche.socialmediaapi.response.SignUpResponse;
import org.springframework.http.ResponseEntity;

public interface UserService {
    ResponseEntity<SignUpResponse> signUp(SignUpRequest signUpRequest);

}
