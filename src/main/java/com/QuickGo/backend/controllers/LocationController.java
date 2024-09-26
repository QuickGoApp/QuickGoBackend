package com.QuickGo.backend.controllers;

import com.QuickGo.backend.DTO.LocationDTO;
import com.QuickGo.backend.DTO.PrivilegeDTO;
import com.QuickGo.backend.exception.CustomException;
import com.QuickGo.backend.service.LocationService;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/quickGo/location")
public class LocationController {

    @Autowired
    LocationService locationService;
    Gson gson  = new Gson();

    @PostMapping(value = "/getGeolocationDrivers")
    ResponseEntity<?> getGeolocationDrivers(@RequestBody LocationDTO locationDTO) throws Exception{
        try {
            return locationService.getGeolocationDrivers(locationDTO);
        } catch (CustomException e) {
            throw new CustomException( e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

}
