package com.vedant.TransportBookingBackend.repository;

import com.vedant.TransportBookingBackend.dao.Dealer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DealerRepository extends JpaRepository<Dealer, String> {
    Dealer findByUser_Username(String username);
}
