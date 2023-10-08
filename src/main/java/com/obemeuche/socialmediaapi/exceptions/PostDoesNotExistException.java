package com.obemeuche.socialmediaapi.exceptions;

public class PostDoesNotExistException extends RuntimeException{
    public PostDoesNotExistException(String message) {
        super(message);
    }
}
