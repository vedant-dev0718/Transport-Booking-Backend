package com.vedant.TransportBookingBackend.dao;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import java.util.Date;

@Data
@Entity
@Table(name = "bookings")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Booking {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    private String bookingId;

    @OneToOne
    @JoinColumn(name = "dealer_id", referencedColumnName = "dealerId")
    private Dealer dealers;

    @OneToOne
    @JoinColumn(name = "driver_id", referencedColumnName = "driverId")
    private Driver driver;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date bookedOn;

    private String fromCity;

    private String toCity;

    @Temporal(TemporalType.DATE)
    private Date bookingDate;
}
