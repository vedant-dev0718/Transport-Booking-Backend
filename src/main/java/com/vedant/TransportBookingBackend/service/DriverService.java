package com.vedant.TransportBookingBackend.service;

import com.vedant.TransportBookingBackend.dao.Driver;
import com.vedant.TransportBookingBackend.dao.User;
import com.vedant.TransportBookingBackend.dto.request.GetBookingsRequestDTO;
import com.vedant.TransportBookingBackend.dto.request.LoginRequestDTO;
import com.vedant.TransportBookingBackend.dto.request.SignupDriverRequestDTO;
import com.vedant.TransportBookingBackend.dto.response.GetBookingsResponseDTO;
import com.vedant.TransportBookingBackend.dto.response.LoginDriverResponseDTO;
import com.vedant.TransportBookingBackend.enums.UserRole;
import com.vedant.TransportBookingBackend.exception.*;
import com.vedant.TransportBookingBackend.model.CustomUserDetails;
import com.vedant.TransportBookingBackend.repository.BookingRepository;
import com.vedant.TransportBookingBackend.repository.DriverRepository;
import com.vedant.TransportBookingBackend.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DriverService {
    private final BCryptPasswordEncoder passwordEncoder;

    private final DriverRepository driverRepository;

    private final AuthenticationManager authenticationManager;

    private final JwtUserDetailsService userDetailsService;

    private final JwtUtil jwtUtil;

    private final BookingRepository bookingRepository;

    private final OtpService otpService;

    private final UserService userService;

    public LoginDriverResponseDTO signup(SignupDriverRequestDTO signupRequest) {
        if(userService.exists(signupRequest.getUsername())) {
            throw new UserAlreadyExistsException();
        }
        val user = User.builder()
                .name(signupRequest.getName())
                .email(signupRequest.getEmail())
                .username(signupRequest.getUsername())
                .mobileNumber(signupRequest.getMobileNumber())
                .password(passwordEncoder.encode(signupRequest.getPassword()))
                .role(UserRole.DRIVER)
                .build();
        val driver = Driver.builder()
                .user(user)
                .age(signupRequest.getAge())
                .truckNumber(signupRequest.getTruckNumber())
                .truckCapacity(signupRequest.getTruckCapacity())
                .transporterName(signupRequest.getTransporterName())
                .drivingExperience(signupRequest.getDrivingExperience())
                .interestedRoutes(signupRequest.getInterestedRoutes())
                .build();
        driverRepository.save(driver);
        return login(new LoginRequestDTO(signupRequest.getUsername(), signupRequest.getPassword()));
    }

    public LoginDriverResponseDTO login(LoginRequestDTO loginRequestDTO) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequestDTO.getUsername(),
                            loginRequestDTO.getPassword()
                    )
            );
        } catch (BadCredentialsException exception) {
            throw new InvalidCredentialsException();
        }
        val userDetails = (CustomUserDetails) userDetailsService.loadUserByUsername(loginRequestDTO.getUsername());
        val accessToken = jwtUtil.generateToken(userDetails);
        val driver = driverRepository.findByUser_Username(userDetails.getUsername());
        return LoginDriverResponseDTO.builder()
                .driver(driver)
                .accessToken(accessToken)
                .build();
    }

    public Driver getDriverById(String id) {
        return driverRepository.findById(id)
                .orElseThrow(DriverNotFoundException::new);
    }

    public GetBookingsResponseDTO getBookingsForDriver(GetBookingsRequestDTO getBookingsRequestDTO) {
        val sortOrder = getBookingsRequestDTO.getDescending() ? Sort.Direction.DESC : Sort.Direction.ASC;
        val sortBy = getSortField(getBookingsRequestDTO.getSortBy());
        val pageRequest = PageRequest.of(
                getBookingsRequestDTO.getPageNumber(),
                getBookingsRequestDTO.getPageSize(),
                Sort.by(sortOrder, sortBy)
        );
        val bookings = bookingRepository.findAllByDriver_DriverId(getBookingsRequestDTO.getDriverId(), pageRequest);
        return GetBookingsResponseDTO.builder()
                .totalBookings(bookings.getTotalElements())
                .totalPages(bookings.getTotalPages())
                .bookingList(bookings.toList())
                .build();
    }

    private String getSortField(String sortRequest) {
        switch (sortRequest) {
            case "dealerName": return "dealer.user.name";
            case "fromCity": return "fromCity";
            case "toCity": return "toCity";
            case "bookingDate": return "bookingDate";
            case "bookedOn": return "bookedOn";
            default: throw new InvalidSortFieldException(String.format("Can not sort on %s!", sortRequest));
        }
    }

    public LoginDriverResponseDTO loginViaOtp(String username, Integer otp) {
        if(!otpService.isOtpValid(username, otp)) throw new InvalidOtpException();
        otpService.clearOTP(username);
        val userDetails = (CustomUserDetails) userDetailsService.loadUserByUsername(username);
        val accessToken = jwtUtil.generateToken(userDetails);
        val driver = driverRepository.findByUser_Username(userDetails.getUsername());
        return LoginDriverResponseDTO.builder()
                .driver(driver)
                .accessToken(accessToken)
                .build();
    }
}
