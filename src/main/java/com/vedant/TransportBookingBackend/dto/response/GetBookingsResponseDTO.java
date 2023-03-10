package com.vedant.TransportBookingBackend.dto.response;

import com.vedant.TransportBookingBackend.dao.Booking;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class GetBookingsResponseDTO {

    private Long totalBookings;
    private Integer totalPages;
    private List<Booking> bookingList;
}
