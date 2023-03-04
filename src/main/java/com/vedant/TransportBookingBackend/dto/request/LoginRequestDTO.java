package com.vedant.TransportBookingBackend.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginRequestDTO {

    @NotBlank(message = "Username must not be blank!")
    private String username;

    @NotBlank(message = "Password must not be blank!")
    private String password;
}
