package com.vedant.TransportBookingBackend.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public class BaseException extends RuntimeException {

    @Getter
    private final HttpStatus responseHttpStatus;


    public BaseException(final String message, final HttpStatus responseHttpStatus) {
        super(message);
        this.responseHttpStatus = responseHttpStatus;
    }
}
