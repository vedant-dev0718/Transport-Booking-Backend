package com.vedant.TransportBookingBackend.dao;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

@Data
@Entity
@Table(name = "routes")
@AllArgsConstructor
@NoArgsConstructor
public class Route {

    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid2")
    private String routeId;

    @NotBlank(message = "From City must not be blank!")
    private String fromCity;

    @NotBlank(message = "From State must not be blank!")
    private String fromState;

    @NotBlank(message = "To city must not be blank!")
    private String toCity;

    @NotBlank(message = "To state must not be blank!")
    private String toState;
}
