package com.vedant.TransportBookingBackend.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class GetBookingsRequestDTO {
    @NotBlank(message = "Driver ID must not be blank!")
    private String driverId;

    @Min(value = 0)
    private Integer pageNumber;

    @Min(value = 10)
    private Integer pageSize;
    private String sortBy = "bookingDate";
    private Boolean descending = true;
}
