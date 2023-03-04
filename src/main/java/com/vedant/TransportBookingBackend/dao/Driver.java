package com.vedant.TransportBookingBackend.dao;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;

@Data
@Entity
@Table(name = "drivers")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Driver {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    private String driverId;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "username", referencedColumnName = "username")
    private User user;

    private Integer age;
    private String truckNumber;
    private Integer truckCapacity;
    private String transporterName;
    private Double drivingExperience;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "driver_id", referencedColumnName = "driverId")
    private List<Route> interestedRoutes;
}
