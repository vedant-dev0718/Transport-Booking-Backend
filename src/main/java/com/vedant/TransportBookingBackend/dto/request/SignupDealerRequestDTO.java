package com.vedant.TransportBookingBackend.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class SignupDealerRequestDTO {

    @NotBlank(message = "Username must not be blank!")
    private String username;

    @NotBlank(message = "Name must not be blank!")
    private String name;

    @NotBlank(message = "Email must not be blank!")
    @Pattern(
            regexp = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$",
            message = "Invalid Email!"
    )
    private String email;

    @NotBlank(message = "Mobile Number must not be blank!")
    @Pattern(
            regexp = "^(?:(?:\\+|0{0,2})91(\\s*[\\-]\\s*)?|[0]?)?[789]\\d{9}$",
            message = "Invalid Mobile Number!"
    )
    private String mobileNumber;

    @NotBlank(message = "Password must not be blank!")
    private String password;

    private String natureOfMaterial;

    private Double weightOfMaterial;

    private Integer quantity;

    @NotBlank(message = "City must not be blank!")
    private String city;

    @NotBlank(message = "State must not be blank!")
    private String state;
}
