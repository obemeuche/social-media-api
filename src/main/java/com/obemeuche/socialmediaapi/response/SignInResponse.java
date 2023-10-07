package com.obemeuche.socialmediaapi.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SignInResponse {

    String message;
    String token;
}
