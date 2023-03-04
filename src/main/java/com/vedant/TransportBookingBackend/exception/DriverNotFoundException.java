package com.vedant.TransportBookingBackend.exception;

import org.springframework.http.HttpStatus;

public class DriverNotFoundException extends BaseException{
    public DriverNotFoundException() {
        super("Driver not found!", HttpStatus.NOT_FOUND);
    }

}
