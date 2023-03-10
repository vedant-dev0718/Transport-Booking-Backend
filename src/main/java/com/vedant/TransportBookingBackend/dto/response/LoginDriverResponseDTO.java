package com.vedant.TransportBookingBackend.dto.response;

import com.vedant.TransportBookingBackend.dao.Driver;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginDriverResponseDTO {
    private Driver driver;
    private String accessToken;
}
