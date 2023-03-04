package com.vedant.TransportBookingBackend.exception;

import org.springframework.http.HttpStatus;

public class InvalidSortFieldException extends BaseException {

    public InvalidSortFieldException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }

    public InvalidSortFieldException() {
        super("Invalid sort field!", HttpStatus.BAD_REQUEST);
    }

}
