package com.obemeuche.socialmediaapi.service;

import com.obemeuche.socialmediaapi.request.SignInRequest;
import com.obemeuche.socialmediaapi.response.SignInResponse;

public interface SignInService {
    SignInResponse signIn(SignInRequest signInRequest);
}
