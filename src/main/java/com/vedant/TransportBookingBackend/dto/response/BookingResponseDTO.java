package com.vedant.TransportBookingBackend.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
@Data
@Builder
public class BookingResponseDTO {
    private String driverId;
    private String driverName;
    private String driverContact;
    private String driverTruckNumber;
    private String fromCity;
    private String toCity;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date bookingDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date bookedOn;
}
