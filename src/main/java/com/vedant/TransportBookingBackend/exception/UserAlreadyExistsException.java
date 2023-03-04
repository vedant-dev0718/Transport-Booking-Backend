package com.vedant.TransportBookingBackend.exception;

import org.springframework.http.HttpStatus;

public class UserAlreadyExistsException extends BaseException{

    public UserAlreadyExistsException() {
        super("User with given username already exists!", HttpStatus.BAD_REQUEST);
    }

}
