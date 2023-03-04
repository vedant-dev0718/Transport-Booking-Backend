package com.vedant.TransportBookingBackend.repository;

import com.vedant.TransportBookingBackend.dao.Driver;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DriverRepository extends JpaRepository<Driver, String> {
}
