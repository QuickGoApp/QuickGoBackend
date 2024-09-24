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

@Service
public class TripServiceImpl implements TripService {
    @Autowired
    TripRepository repository;
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

        // Map DTO to entity
        Trip trip = modelMapper.map(requestDetailDTO, Trip.class);

        trip.setCreateDateTime(new Date());
        trip.setUpdateDateTime(new Date());
        trip.setTotalAmount(0.00);
        trip.setStatus("REQUEST");
        trip.setIsActive(1);


        Trip savedTrip = repository.save(trip);
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

        if (favoriteDriverDTO.getId() > 0){
            FavoriteDriver referenceById = favoriteDriverRepository.getReferenceById(favoriteDriverDTO.getId());
            referenceById.setUpdateDateTime(new Date());
            referenceById.setIsActive(0);
            return new ResponseEntity<>(new ResponseMessage(HttpStatus.OK.value(), "success", referenceById), HttpStatus.OK);

        }
        FavoriteDriver driver = modelMapper.map(favoriteDriverDTO, FavoriteDriver.class);
        driver.setIsActive(1);
        driver.setCreateDateTime(new Date());

        FavoriteDriver save = favoriteDriverRepository.save(driver);
        return new ResponseEntity<>(new ResponseMessage(HttpStatus.OK.value(), "success", save), HttpStatus.OK);

    }

}
