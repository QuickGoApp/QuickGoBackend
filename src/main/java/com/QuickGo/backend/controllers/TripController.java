package com.QuickGo.backend.controllers;

import com.QuickGo.backend.DTO.TripRequestDetailDTO;
import com.QuickGo.backend.exception.CustomException;
import com.QuickGo.backend.service.TripService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/quickGo/trip")
public class TripController {
    @Autowired
    TripService tripService;

    @PostMapping(value = "/saveTripRequest")
    ResponseEntity<?> saveTripRequest(@RequestBody TripRequestDetailDTO requestDetailDTO) throws Exception{
        try {
            return tripService.saveTripRequest(requestDetailDTO);
        } catch (CustomException e) {
            throw new CustomException( e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

}
