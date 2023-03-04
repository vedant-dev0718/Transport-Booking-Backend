package com.vedant.TransportBookingBackend.exception;

import org.springframework.http.HttpStatus;

public class UserNotFoundException extends BaseException{

    public UserNotFoundException() {
        super("User not found!", HttpStatus.NOT_FOUND);
    }

}
