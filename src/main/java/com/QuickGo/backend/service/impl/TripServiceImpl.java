package com.QuickGo.backend.service.impl;

import com.QuickGo.backend.DTO.FavoriteDriverDTO;
import com.QuickGo.backend.DTO.TripRequestDetailDTO;
import com.QuickGo.backend.DTO.common.ResponseMessage;
import com.QuickGo.backend.exception.CustomException;
import com.QuickGo.backend.models.FavoriteDriver;
import com.QuickGo.backend.models.Trip;
import com.QuickGo.backend.repository.FavoriteDriverRepository;
import com.QuickGo.backend.repository.TripRepository;
import com.QuickGo.backend.service.TripService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TripServiceImpl implements TripService {

    @Autowired
    TripRepository tripRepository;
    @Autowired
    FavoriteDriverRepository favoriteDriverRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ResponseEntity<?> saveTripRequest(TripRequestDetailDTO requestDetailDTO) throws Exception {

        if (requestDetailDTO.getPassengerCode() == null || requestDetailDTO.getPassengerCode().isEmpty()) {
            throw new CustomException("Passenger code cannot be empty.");
        }
        if (requestDetailDTO.getDriveCode() == null || requestDetailDTO.getDriveCode().isEmpty()) {
            throw new CustomException("Driver code cannot be empty.");
        }
        if (requestDetailDTO.getPickupLat() == 0 || requestDetailDTO.getPickupLng() == 0) {
            throw new CustomException("Pickup location must have valid coordinates.");
        }
        if (requestDetailDTO.getDropLat() == 0 || requestDetailDTO.getDropLng() == 0) {
            throw new CustomException("Drop location must have valid coordinates.");
        }
        List<Trip> passengerRequests = tripRepository.findTripByPassengerCodeAndStatus(requestDetailDTO.getPassengerCode(), "REQUEST");
        if (!passengerRequests.isEmpty())
            return new ResponseEntity<>(new ResponseMessage(HttpStatus.TOO_MANY_REQUESTS.value(), "You have already requested a driver"), HttpStatus.OK);


        // Map DTO to entity
        Trip trip = modelMapper.map(requestDetailDTO, Trip.class);

        trip.setCreateDateTime(new Date());
        trip.setUpdateDateTime(new Date());
        trip.setStatus("REQUEST");
        trip.setIsActive(1);


        Trip savedTrip = tripRepository.save(trip);
        return new ResponseEntity<>(new ResponseMessage(HttpStatus.OK.value(), "success", savedTrip), HttpStatus.OK);

    }

    @Override
    public ResponseEntity<?> saveFavoriteDriver(FavoriteDriverDTO favoriteDriverDTO) throws Exception {
        if (favoriteDriverDTO.getPassengerCode() == null || favoriteDriverDTO.getPassengerCode().isEmpty()) {
            throw new CustomException("Passenger code cannot be empty.");
        }
        if (favoriteDriverDTO.getDriverCode() == null || favoriteDriverDTO.getDriverCode().isEmpty()) {
            throw new CustomException("Driver code cannot be empty.");
        }


        Optional<FavoriteDriver> value = favoriteDriverRepository.findFavoriteDriverByPassengerCodeAndDriverCode(favoriteDriverDTO.getPassengerCode(), favoriteDriverDTO.getDriverCode());
        if (value.isPresent()) {
            value.get().setUpdateDateTime(new Date());
            if (value.get().getIsActive() == 0) {
                value.get().setIsActive(1);
            } else if (value.get().getIsActive() == 1) {
                value.get().setIsActive(0);
            }
            FavoriteDriver update = favoriteDriverRepository.save(value.get());
            return new ResponseEntity<>(new ResponseMessage(HttpStatus.OK.value(), "success", update), HttpStatus.OK);
        } else {
            FavoriteDriver driver = modelMapper.map(favoriteDriverDTO, FavoriteDriver.class);
            driver.setIsActive(1);
            driver.setCreateDateTime(new Date());

            FavoriteDriver save = favoriteDriverRepository.save(driver);
            return new ResponseEntity<>(new ResponseMessage(HttpStatus.OK.value(), "success", save), HttpStatus.OK);

        }


    }

    @Override
    public ResponseEntity<?> getDriverTrip(FavoriteDriverDTO favoriteDriverDTO) throws Exception {
        // Check if the driverCode or passengerCode is null or empty
        if (favoriteDriverDTO.getDriverCode() == null || favoriteDriverDTO.getDriverCode().isEmpty()) {
            throw new CustomException("Driver code cannot be empty.");
        }

        List<Trip> trips = tripRepository.findTripByDriveCodeAndStatus(favoriteDriverDTO.getDriverCode(), "REQUEST");
        if (trips == null || trips.isEmpty()) {
            throw new CustomException("No trips found for the provided driver.");
        }

        // Convert the list of Trip entities to TripRequestDetailDTO using Stream
        List<TripRequestDetailDTO> tripDTOs = trips.stream()
                .map(trip -> TripRequestDetailDTO.builder()
                        .tripID(trip.getTripID())
                        .passengerCode(trip.getPassengerCode())
                        .driveCode(trip.getDriveCode())
                        .totalAmount(trip.getTotalAmount())
                        .paymentMethod(trip.getPaymentMethod())
                        .status(trip.getStatus())
                        .driverComment(trip.getDriverComment())
                        .pickupLat(trip.getPickupLat())
                        .pickupLng(trip.getPickupLng())
                        .dropLat(trip.getDropLat())
                        .dropLng(trip.getDropLng())
                        .passengerComment(trip.getPassengerComment())
                        .createDateTime(trip.getCreateDateTime() + "")
                        .updateDateTime(trip.getUpdateDateTime() + "")
                        .isActive(trip.getIsActive())
                        .build())
                .collect(Collectors.toList());

        // Return the list of TripRequestDetailDTO in the ResponseEntity
        return new ResponseEntity<>(new ResponseMessage(HttpStatus.OK.value(), "success", tripDTOs), HttpStatus.OK);
    }

    @Override
    public ResponseMessage cancelTripRequest(TripRequestDetailDTO requestDetailDTO) {

        if (requestDetailDTO.getPassengerCode() == null || requestDetailDTO.getPassengerCode().isEmpty()) {
            return new ResponseMessage(HttpStatus.BAD_REQUEST.value(), "Passenger code cannot be empty.");
        }

        List<Trip> passengerRequests = tripRepository.findTripByPassengerCodeAndDriveCodeAndStatus(requestDetailDTO.getPassengerCode(), requestDetailDTO.getDriveCode(), "REQUEST");
        if (passengerRequests.isEmpty()) {
            return new ResponseMessage(HttpStatus.NOT_FOUND.value(), "You have not requested any driver.");
        }

        Trip trip = passengerRequests.get(0);
        trip.setStatus("CANCELLED");
        trip.setPassengerComment("Trip request cancelled by passenger");
        trip.setUpdateDateTime(new Date());
        tripRepository.save(trip);

        return new ResponseMessage(HttpStatus.OK.value(), "Trip request cancelled successfully.");

    }

    @Override
    public ResponseMessage acceptTripRequest(TripRequestDetailDTO requestDetailDTO) throws CustomException {

        if (requestDetailDTO.getPassengerCode() == null || requestDetailDTO.getPassengerCode().isEmpty()) {
            return new ResponseMessage(HttpStatus.BAD_REQUEST.value(), "Passenger code cannot be empty.");
        }

        tripRepository
                .findTripByPassengerCodeAndDriveCodeAndStatus(requestDetailDTO.getPassengerCode(), requestDetailDTO.getDriveCode(), "REQUEST")
                .stream()
                .findFirst()
                .map(trip -> {
                    trip.setStatus("ACCEPTED");
                    trip.setDriverComment("Trip request accepted by driver");
                    trip.setUpdateDateTime(new Date());
                    return tripRepository.save(trip);
                })
                .orElseThrow(() -> new CustomException("You have not requested any driver."));

        List<Trip> driverRequests = tripRepository.findTripByDriveCodeAndStatus(requestDetailDTO.getDriveCode(), "REQUEST")
                .stream()
                .peek(driverRequest -> {
                    driverRequest.setStatus("CANCELLED");
                    driverRequest.setDriverComment("Trip request cancelled by system due to accepting another trip request");
                    driverRequest.setUpdateDateTime(new Date());
                })
                .collect(Collectors.toList());

        tripRepository.saveAll(driverRequests);

        return new ResponseMessage(HttpStatus.OK.value(), "Trip request accepted successfully.");
    }


}
