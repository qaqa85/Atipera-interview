package com.atipera.interview.github.exceptions;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(String userName) {
        super("User " + userName + " not found");
    }
}
