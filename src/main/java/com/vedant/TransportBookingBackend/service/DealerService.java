package com.vedant.TransportBookingBackend.service;


import com.vedant.TransportBookingBackend.dao.Dealer;
import com.vedant.TransportBookingBackend.dao.User;
import com.vedant.TransportBookingBackend.dto.request.LoginRequestDTO;
import com.vedant.TransportBookingBackend.dto.request.SignupDealerRequestDTO;
import com.vedant.TransportBookingBackend.dto.response.LoginDealerResponseDTO;
import com.vedant.TransportBookingBackend.enums.UserRole;
import com.vedant.TransportBookingBackend.exception.InvalidCredentialsException;
import com.vedant.TransportBookingBackend.exception.UserAlreadyExistsException;
import com.vedant.TransportBookingBackend.model.CustomUserDetails;
import com.vedant.TransportBookingBackend.repository.DealerRepository;
import com.vedant.TransportBookingBackend.repository.DriverRepository;
import com.vedant.TransportBookingBackend.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class DealerService {

    private final BCryptPasswordEncoder passwordEncoder;

    private final DealerRepository dealerRepository;

    private final AuthenticationManager authenticationManager;

    private final JwtUserDetailsService userDetailsService;

    private final DriverRepository driverRepository;

//    private final DriverService driverService;

    private final JwtUtil jwtUtil;

//    private final BookingRepository bookingRepository;

//    private final OtpService otpService;

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
}
