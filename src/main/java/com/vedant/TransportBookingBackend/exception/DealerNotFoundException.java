package com.vedant.TransportBookingBackend.exception;

import org.springframework.http.HttpStatus;

public class DealerNotFoundException extends BaseException{


    public DealerNotFoundException(String message, HttpStatus responseHttpStatus) {
        super("Dealer not found!", HttpStatus.NOT_FOUND);
    }
}
