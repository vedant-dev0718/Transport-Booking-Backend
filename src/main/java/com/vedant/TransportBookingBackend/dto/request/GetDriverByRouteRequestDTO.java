package com.vedant.TransportBookingBackend.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class GetDriverByRouteRequestDTO {

    @NotBlank(message = "From City must not be blank!")
    private String fromCity;

    @NotBlank(message = "To City must not be blank!")
    private String toCity;

    @Min(value = 0)
    private Integer pageNumber;

    @Min(value = 10)
    private Integer pageSize;
    private String sortBy = "drivingExperience";
    private Boolean descending = false;
}
