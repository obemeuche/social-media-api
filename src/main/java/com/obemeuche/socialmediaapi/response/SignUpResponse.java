package com.obemeuche.socialmediaapi.response;

import lombok.*;

@Builder
@Data
public class SignUpResponse {

    private String username;
    private String email;
    private String message;
}
