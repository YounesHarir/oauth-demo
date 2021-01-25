package com.app.demo.exception;

public class UsernameAlreadyExistException extends RuntimeException {

    public UsernameAlreadyExistException(String message){
        super(message);
    }
}
