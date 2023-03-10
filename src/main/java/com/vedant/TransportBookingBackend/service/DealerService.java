package com.vedant.TransportBookingBackend.service;


import com.vedant.TransportBookingBackend.dao.Booking;
import com.vedant.TransportBookingBackend.dao.Dealer;
import com.vedant.TransportBookingBackend.dao.User;
import com.vedant.TransportBookingBackend.dto.request.*;
import com.vedant.TransportBookingBackend.dto.response.BookingResponseDTO;
import com.vedant.TransportBookingBackend.dto.response.GetDriversResponseDTO;
import com.vedant.TransportBookingBackend.dto.response.LoginDealerResponseDTO;
import com.vedant.TransportBookingBackend.enums.UserRole;
import com.vedant.TransportBookingBackend.exception.InvalidCredentialsException;
import com.vedant.TransportBookingBackend.exception.InvalidOtpException;
import com.vedant.TransportBookingBackend.exception.InvalidSortFieldException;
import com.vedant.TransportBookingBackend.exception.UserAlreadyExistsException;
import com.vedant.TransportBookingBackend.model.CustomUserDetails;
import com.vedant.TransportBookingBackend.repository.BookingRepository;
import com.vedant.TransportBookingBackend.repository.DealerRepository;
import com.vedant.TransportBookingBackend.repository.DriverRepository;
import com.vedant.TransportBookingBackend.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
@Slf4j
public class DealerService {

    private final BCryptPasswordEncoder passwordEncoder;

    private final DealerRepository dealerRepository;

    private final AuthenticationManager authenticationManager;

    private final JwtUserDetailsService userDetailsService;

    private final DriverRepository driverRepository;

    private final DriverService driverService;

    private final JwtUtil jwtUtil;

    private final BookingRepository bookingRepository;

    private final OtpService otpService;

    private final UserService userService;

    public LoginDealerResponseDTO signup(SignupDealerRequestDTO signupDealerRequestDTO) {
        if (userService.exists(signupDealerRequestDTO.getUsername())) {
            throw new UserAlreadyExistsException();
        }


        val user = User.builder()
                .name(signupDealerRequestDTO.getName())
                .email(signupDealerRequestDTO.getEmail())
                .username(signupDealerRequestDTO.getUsername())
                .mobileNumber(signupDealerRequestDTO.getMobileNumber())
                .password(passwordEncoder.encode(signupDealerRequestDTO.getPassword()))
//                .password(signupDealerRequestDTO.getPassword())
                .role(UserRole.DEALER)
                .build();

        val dealer = Dealer.builder()
                .user(user)
                .natureOfMaterial(signupDealerRequestDTO.getNatureOfMaterial())
                .quantity(signupDealerRequestDTO.getQuantity())
                .weightOfMaterial(signupDealerRequestDTO.getWeightOfMaterial())
                .city(signupDealerRequestDTO.getCity())
                .state(signupDealerRequestDTO.getState())
                .build();
        log.info(dealer.toString());
        dealerRepository.save(dealer);

        return login(new LoginRequestDTO(signupDealerRequestDTO.getUsername(), signupDealerRequestDTO.getPassword()));
    }

    public LoginDealerResponseDTO login(LoginRequestDTO loginRequestDTO) {
        log.info(loginRequestDTO.toString());
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
        val dealer = dealerRepository.findByUser_Username(userDetails.getUsername());

        return LoginDealerResponseDTO.builder()
                .dealer(dealer)
                .accessToken(accessToken)
                .build();
    }

    public GetDriversResponseDTO getDriversByState(GetDriversByStateRequestDTO getDriversByStateRequestDTO) {
        val sortOrder = getDriversByStateRequestDTO.getDescending()
                ? Sort.Direction.DESC : Sort.Direction.ASC;

        val sortBy = getSortField(getDriversByStateRequestDTO.getSortBy());
        val pageRequest =
                PageRequest.of(
                        getDriversByStateRequestDTO.getPageNumber(),
                        getDriversByStateRequestDTO.getPageSize(),
                        Sort.by(sortOrder, sortBy)
                );

        val drivers = driverRepository.getAllDriversByState(getDriversByStateRequestDTO.getState(), pageRequest);
        return GetDriversResponseDTO.builder()
                .totalDrivers(drivers.getTotalElements())
                .totalPages(drivers.getTotalPages())
                .driverList(drivers.toList())
                .build();
    }

    public GetDriversResponseDTO getDriversForDealer(GetDriversForDealerRequestDTO driverRequest) {
        val sortOrder = driverRequest.getDescending() ? Sort.Direction.DESC
                : Sort.Direction.ASC;

        val sortBy = getSortField(driverRequest.getSortBy());

        val pageRequest = PageRequest.of(
                driverRequest.getPageNumber(),
                driverRequest.getPageSize(),
                Sort.by(sortOrder, sortBy)
        );

        val drivers = driverRepository.getAllDriversByCity(driverRequest.getCity(), pageRequest);

        return GetDriversResponseDTO.builder()
                .totalDrivers(drivers.getTotalElements())
                .totalPages(drivers.getTotalPages())
                .driverList(drivers.toList())
                .build();
    }


    private String getSortField(String sortRequest) {
        return switch (sortRequest) {
            case "name" -> "user.name";
            case "age" -> "age";
            case "truckCapacity" -> "truckCapacity";
            case "transporterName" -> "transporterName";
            case "drivingExperience" -> "drivingExperience";
            default -> throw new InvalidSortFieldException(String.format("Can not sort on %s!", sortRequest));
        };
    }

    public LoginDealerResponseDTO loginViaOtp(String username, Integer otp) {
        if (!otpService.isOtpValid(username, otp)) throw new InvalidOtpException();
        otpService.clearOTP(username);

        val userDetails = (CustomUserDetails) userDetailsService.loadUserByUsername(username);
        val accessToken = jwtUtil.generateToken(userDetails);

        val dealer = dealerRepository.findByUser_Username(userDetails.getUsername());
        return LoginDealerResponseDTO.builder()
                .dealer(dealer)
                .accessToken(accessToken)
                .build();

    }

    public GetDriversResponseDTO getDriversByRoute(GetDriverByRouteRequestDTO getDriverByRouteRequestDTO) {
        val sortOrder = getDriverByRouteRequestDTO.getDescending() ? Sort.Direction.DESC : Sort.Direction.ASC;
        val sortBy = getSortField(getDriverByRouteRequestDTO.getSortBy());
        val pageRequest = PageRequest.of(
                getDriverByRouteRequestDTO.getPageNumber(),
                getDriverByRouteRequestDTO.getPageSize(),
                Sort.by(sortOrder, sortBy)
        );
        val drivers = driverRepository.getAllDriversByRoute(getDriverByRouteRequestDTO.getFromCity(), getDriverByRouteRequestDTO.getToCity(), pageRequest);
        return GetDriversResponseDTO.builder()
                .totalDrivers(drivers.getTotalElements())
                .totalPages(drivers.getTotalPages())
                .driverList(drivers.toList())
                .build();
    }

    public BookingResponseDTO bookDriver(BookingRequestDTO bookingRequestDTO) {
        val dealer = getDealerById(bookingRequestDTO.getDealerId());
        val driver = driverService.getDriverById(bookingRequestDTO.getDriverId());

        val booking = Booking.builder()
                .dealers(dealer)
                .driver(driver)
                .bookedOn(new Date())
                .fromCity(bookingRequestDTO.getFromCity())
                .toCity(bookingRequestDTO.getToCity())
                .bookingDate(bookingRequestDTO.getBookingDate())
                .build();
        bookingRepository.save(booking);

        return BookingResponseDTO.builder()
                .driverId(driver.getDriverId())
                .driverName(driver.getUser().getName())
                .driverContact(driver.getUser().getMobileNumber())
                .driverTruckNumber(driver.getTruckNumber())
                .fromCity(booking.getFromCity())
                .toCity(booking.getToCity())
                .bookingDate(bookingRequestDTO.getBookingDate())
                .bookedOn(booking.getBookedOn())
                .build();
    }

    public Dealer getDealerById(String id) {
        return dealerRepository.findById(id)
                .orElseThrow();
    }
}
