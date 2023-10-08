package com.obemeuche.socialmediaapi.service;

import com.obemeuche.socialmediaapi.request.SignInRequest;
import com.obemeuche.socialmediaapi.response.SignInResponse;
import org.springframework.http.ResponseEntity;

public interface SignInService {
    ResponseEntity<SignInResponse> signIn(SignInRequest signInRequest);
}
