package com.obemeuche.socialmediaapi.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorResponse {
    private String responseMsg;
    public String responseCode;
}
