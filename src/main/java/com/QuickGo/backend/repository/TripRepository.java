package com.QuickGo.backend.repository;

import com.QuickGo.backend.models.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TripRepository extends JpaRepository<Trip,Integer> {

    List<Trip> findTripByPassengerCodeAndDriveCodeAndStatus(String passengerCode,String driverCode, String status);

    List<Trip> findTripByDriveCodeAndStatus( String driverCode, String status);

    List<Trip> findTripByPassengerCodeAndStatus(String passengerCode, String request);
}
