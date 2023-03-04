package com.vedant.TransportBookingBackend.dto.response;

import com.vedant.TransportBookingBackend.dao.Dealer;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginDealerResponseDTO {

    private Dealer dealer;

    private String accessToken;
}
