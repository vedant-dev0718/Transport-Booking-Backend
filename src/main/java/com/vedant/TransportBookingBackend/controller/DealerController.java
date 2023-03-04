package com.vedant.TransportBookingBackend.controller;

import com.vedant.TransportBookingBackend.constant.Endpoints;
import com.vedant.TransportBookingBackend.dto.request.LoginRequestDTO;
import com.vedant.TransportBookingBackend.dto.request.LoginViaOtpRequestDTO;
import com.vedant.TransportBookingBackend.dto.response.LoginDealerResponseDTO;
import com.vedant.TransportBookingBackend.dto.request.SignupDealerRequestDTO;
import com.vedant.TransportBookingBackend.service.DealerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(Endpoints.BASE_URL)
@Slf4j
public class DealerController {

    private final DealerService dealerService;

    @PostMapping(Endpoints.AuthAPI.DEALER_SIGNUP)
    public LoginDealerResponseDTO signupDealer(@RequestBody
                                               @Valid SignupDealerRequestDTO
                                                       signupDealerRequestDTO) {
        return dealerService.signup(signupDealerRequestDTO);
    }

    @PostMapping(Endpoints.AuthAPI.DEALER_LOGIN)
    public LoginDealerResponseDTO loginDealer(@RequestBody @Valid LoginRequestDTO loginRequestDTO)
    {
        return dealerService.login(loginRequestDTO);
    }

//    @PostMapping(Endpoints.AuthAPI.DEALER_LOGIN_OTP)
//    public LoginDealerResponseDTO loginDealerViaOtp(@RequestBody @Valid LoginViaOtpRequestDTO loginViaOtpRequestDTO)
//    {
//        return dealerService.loginViaOtp(loginViaOtpRequestDTO.getUsername(),loginViaOtpRequestDTO.getOtp());
//    }
}
