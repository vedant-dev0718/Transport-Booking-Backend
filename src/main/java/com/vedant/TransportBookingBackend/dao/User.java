package com.vedant.TransportBookingBackend.dao;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vedant.TransportBookingBackend.enums.UserRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {

    @Id
    private String username;

    private String name;

    private String email;

    private String mobileNumber;

    @JsonIgnore
    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole role;
}
