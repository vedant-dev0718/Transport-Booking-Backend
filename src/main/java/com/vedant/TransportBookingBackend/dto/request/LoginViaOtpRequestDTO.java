package com.vedant.TransportBookingBackend.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginViaOtpRequestDTO {
    @NotBlank(message = "Username must not be blank!")
    private String username;

    @Min(value = 100000, message = "OTP Must be 6 digits")
    @Max(value = 900000, message = "OTP Must be 6 digits")
    private Integer otp;
}
