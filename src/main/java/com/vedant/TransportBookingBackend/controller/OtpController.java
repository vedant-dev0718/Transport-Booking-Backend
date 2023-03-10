package com.vedant.TransportBookingBackend.controller;

import com.vedant.TransportBookingBackend.constant.Endpoints;
import com.vedant.TransportBookingBackend.service.OtpService;
import com.vedant.TransportBookingBackend.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping(Endpoints.BASE_URL)
@RequiredArgsConstructor
public class OtpController {
    private final OtpService otpService;

    private final UserService userService;

    @GetMapping(Endpoints.AuthAPI.GET_OTP)
    public String generateOtp(@RequestParam String username) throws IOException {
        val user = userService.getUserByUsername(username);
        otpService.generateOTP(user);
        return "Sent OTP";
    }
}
