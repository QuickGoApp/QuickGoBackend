package com.QuickGo.backend.controllers;

import com.QuickGo.backend.DTO.VehicleDTO;
import com.QuickGo.backend.exception.CustomException;
import com.QuickGo.backend.service.VehicleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/quickGo/auth/vehicle")
public class VehicleController {
    @Autowired
    private VehicleService vehicleService;

    @CrossOrigin(origins = "*")
    @PostMapping("/vehicle")
    public ResponseEntity<?> saveVehicle(@Valid @RequestBody VehicleDTO vehicleDTO) throws CustomException {
        try {
            return vehicleService.saveVehicle(vehicleDTO);
        } catch (CustomException e) {
            throw new CustomException(e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
