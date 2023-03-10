package com.vedant.TransportBookingBackend.repository;

import com.vedant.TransportBookingBackend.dao.Driver;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DriverRepository extends JpaRepository<Driver, String> {

    Driver findByUser_Username(String username);

    Page<Driver> findAllByInterestedRoutes_FromCityOrInterestedRoutes_ToCity(String fromCity, String toCity, Pageable pageable);

    Page<Driver> findAllByInterestedRoutes_FromStateOrInterestedRoutes_ToState(String fromState, String toState, Pageable pageable);

    Page<Driver> findAllByInterestedRoutes_FromCityAndInterestedRoutes_ToCity(String fromCity, String toCity, Pageable pageable);

    default Page<Driver> getAllDriversByRoute(String fromCity, String toCity, Pageable pageable) {
        return findAllByInterestedRoutes_FromCityAndInterestedRoutes_ToCity(fromCity, toCity, pageable);
    }

    default Page<Driver> getAllDriversByCity(String city, Pageable pageable) {
        return findAllByInterestedRoutes_FromCityOrInterestedRoutes_ToCity(city, city, pageable);
    }

    default Page<Driver> getAllDriversByState(String state, PageRequest pageRequest) {
        return findAllByInterestedRoutes_FromStateOrInterestedRoutes_ToState(state, state, pageRequest);
    }
}
