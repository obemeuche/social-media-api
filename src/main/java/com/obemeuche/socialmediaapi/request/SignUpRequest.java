package com.obemeuche.socialmediaapi.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SignUpRequest {

    private String username;

    private String email;

    private String password;

    private String confirmPassword;
}
