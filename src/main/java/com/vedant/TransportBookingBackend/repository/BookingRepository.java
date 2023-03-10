package com.vedant.TransportBookingBackend.repository;

import com.vedant.TransportBookingBackend.dao.Booking;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, String> {
    Page<Booking> findAllByDriver_DriverId(String driverId, Pageable pageable);
}
