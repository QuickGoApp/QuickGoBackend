package com.QuickGo.backend.repository;

import com.QuickGo.backend.models.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface TripRepository extends JpaRepository<Trip,Integer> {

    List<Trip> findTripByPassengerCodeAndDriveCodeAndStatus(String passengerCode,String driverCode, String status);

    List<Trip> findTripByDriveCodeAndStatus( String driverCode, String status);

    List<Trip> findTripByPassengerCodeAndStatus(String passengerCode, String request);

    List<Trip> findByCreateDateTimeBetween(Date fromDate, Date toDate);

    List<Trip> findByCreateDateTimeBetweenAndDriveCode(Date fromDate, Date toDate, String driverCode);

    List<Trip> findByCreateDateTimeBetweenAndPassengerCode(Date fromDate, Date toDate, String passengerCode);

    List<Trip> findByCreateDateTimeBetweenAndStatus(Date fromDate, Date toDate, String status);

    List<Trip> findByCreateDateTimeBetweenAndDriveCodeAndStatus(Date fromDate, Date toDate, String driverCode, String status);

    List<Trip> findByCreateDateTimeBetweenAndPassengerCodeAndStatus(Date fromDate, Date toDate, String passengerCode, String status);

    List<Trip> findByCreateDateTimeBetweenAndDriveCodeAndPassengerCode(Date fromDate, Date toDate, String driverCode, String passengerCode);

    List<Trip> findByCreateDateTimeBetweenAndDriveCodeAndPassengerCodeAndStatus(Date fromDate, Date toDate, String driverCode, String passengerCode, String status);




}
