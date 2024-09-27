package com.QuickGo.backend.controllers;

import com.QuickGo.backend.DTO.UserDTO;
import com.QuickGo.backend.DTO.VehicleDTO;
import com.QuickGo.backend.exception.CustomException;
import com.QuickGo.backend.models.User;
import com.QuickGo.backend.models.Vehicle;
import com.QuickGo.backend.service.VehicleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/quickGo/auth/vehicle")
public class VehicleController {
    @Autowired
    private VehicleService vehicleService;

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

    @CrossOrigin(origins = "*")
    @GetMapping("/vehicles")
    public ResponseEntity<List<VehicleDTO>> getVehicles() throws CustomException {
        try {
            return vehicleService.getVehicles();
        } catch (CustomException e) {
            throw new CustomException(e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @PutMapping("/updateVehicle/{id}")
    public ResponseEntity<Vehicle> updateVehicle(@PathVariable int id, @RequestBody VehicleDTO vehicleData) throws CustomException {
        try {
            return vehicleService.updateVehicle(id, vehicleData);
        } catch (CustomException e) {
            throw new CustomException(e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @DeleteMapping("/deleteVehicle/{id}")
    public ResponseEntity<Void> deleteDriver(@PathVariable int id) throws CustomException {
        try {
            return vehicleService.deleteVehicle(id);
        } catch (CustomException e) {
            throw new CustomException(e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
