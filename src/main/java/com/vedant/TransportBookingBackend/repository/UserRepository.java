package com.vedant.TransportBookingBackend.repository;

import com.vedant.TransportBookingBackend.dao.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    Boolean existsUserByUsername(String username);
    Optional<User> findByEmail(String email);

}
