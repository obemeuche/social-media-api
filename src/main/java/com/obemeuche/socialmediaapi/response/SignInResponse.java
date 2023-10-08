package com.obemeuche.socialmediaapi.response;

import lombok.Builder;
import lombok.Data;
@Data
@Builder
public class SignInResponse {

    String email;
    String message;
    String token;
}
