package com.vedant.TransportBookingBackend.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetDriversForDealerRequestDTO {
    @NotBlank(message = "City must not be blank!")
    private String city;

    @Min(value = 0)
    private Integer pageNumber;

    @Min(value = 10)
    private Integer pageSize;
    private String sortBy = "drivingExperience";
    private Boolean descending = false;

}
