package com.vedant.TransportBookingBackend.exception;

import org.springframework.http.HttpStatus;

public class InvalidOtpException extends BaseException {

    public InvalidOtpException() {
        super("Invalid/Expired OTP", HttpStatus.UNAUTHORIZED);
    }

}
