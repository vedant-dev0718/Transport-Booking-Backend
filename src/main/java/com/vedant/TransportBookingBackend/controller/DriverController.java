package com.vedant.TransportBookingBackend.controller;

import com.vedant.TransportBookingBackend.constant.Endpoints;
import com.vedant.TransportBookingBackend.dto.request.GetBookingsRequestDTO;
import com.vedant.TransportBookingBackend.dto.request.LoginRequestDTO;
import com.vedant.TransportBookingBackend.dto.request.LoginViaOtpRequestDTO;
import com.vedant.TransportBookingBackend.dto.request.SignupDriverRequestDTO;
import com.vedant.TransportBookingBackend.dto.response.GetBookingsResponseDTO;
import com.vedant.TransportBookingBackend.dto.response.LoginDriverResponseDTO;
import com.vedant.TransportBookingBackend.service.DriverService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(Endpoints.BASE_URL)
public class DriverController {

    private final DriverService driverService;

    @PostMapping(Endpoints.AuthAPI.DRIVER_SIGNUP)
    public LoginDriverResponseDTO signupDriver(@RequestBody @Valid SignupDriverRequestDTO signupDriverRequestDTO) {
        return driverService.signup(signupDriverRequestDTO);
    }

    @PostMapping(Endpoints.AuthAPI.DRIVER_LOGIN)
    public LoginDriverResponseDTO loginDriver(@RequestBody @Valid LoginRequestDTO loginRequestDTO) {
        return driverService.login(loginRequestDTO);
    }

    @PostMapping(Endpoints.AuthAPI.DRIVER_LOGIN_OTP)
    public LoginDriverResponseDTO loginDriverViaOtp(@RequestBody @Valid LoginViaOtpRequestDTO loginViaOtpRequestDTO) {
        return driverService.loginViaOtp(loginViaOtpRequestDTO.getUsername(), loginViaOtpRequestDTO.getOtp());
    }

    @PostMapping(Endpoints.DriverAPI.GET_BOOKINGS)
    public GetBookingsResponseDTO getBookingsForDriver(@RequestBody @Valid GetBookingsRequestDTO getBookingsRequestDTO) {
        return driverService.getBookingsForDriver(getBookingsRequestDTO);
    }

}
