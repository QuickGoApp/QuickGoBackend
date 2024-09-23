package com.QuickGo.backend.service.impl;

import com.QuickGo.backend.DTO.TripRequestDetailDTO;
import com.QuickGo.backend.DTO.common.ResponseMessage;
import com.QuickGo.backend.exception.CustomException;
import com.QuickGo.backend.models.Trip;
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

}
