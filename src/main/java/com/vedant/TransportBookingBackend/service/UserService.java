package com.vedant.TransportBookingBackend.service;

import com.vedant.TransportBookingBackend.dao.User;
import com.vedant.TransportBookingBackend.exception.UserNotFoundException;
import com.vedant.TransportBookingBackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User getUserByUsername(String username) {
        return userRepository.findById(username)
                .orElseThrow(UserNotFoundException::new);
    }

    public boolean exists(String username) {
        return userRepository.existsUserByUsername(username);
    }
}
