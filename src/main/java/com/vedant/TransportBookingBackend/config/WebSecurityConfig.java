package com.vedant.TransportBookingBackend.config;

import com.vedant.TransportBookingBackend.constant.Endpoints;
import com.vedant.TransportBookingBackend.enums.UserRole;
import com.vedant.TransportBookingBackend.utils.FilterChainExceptionHandler;
import com.vedant.TransportBookingBackend.utils.JwtRequestFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final JwtRequestFilter jwtRequestFilter;

    private final FilterChainExceptionHandler filterChainExceptionHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.cors()
                .and()
                .authorizeHttpRequests()
                .requestMatchers(Endpoints.BASE_URL + "/auth/**").permitAll()
                .requestMatchers(Endpoints.BASE_URL + "/dealer/**").hasRole(UserRole.DEALER.toString())
                .requestMatchers(Endpoints.BASE_URL + "/driver/**").hasRole(UserRole.DRIVER.toString());

        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(filterChainExceptionHandler, LogoutFilter.class);

        return http.build();
    }
}
