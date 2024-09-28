package com.QuickGo.backend.controllers;

import com.QuickGo.backend.dto.VehicleDTO;
import com.QuickGo.backend.dto.common.ResponseMessage;
import com.QuickGo.backend.exception.CustomException;
import com.QuickGo.backend.service.VehicleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/quickGo/vehicle")
public class VehicleController {
    @Autowired
    private VehicleService vehicleService;

    @PostMapping("/save")
    public ResponseEntity<?> saveVehicle(@Valid @RequestBody VehicleDTO vehicleDTO) {
        return ResponseEntity.ok(vehicleService.saveVehicle(vehicleDTO));
    }

    @GetMapping("/all")
    public ResponseEntity<ResponseMessage> findAll() {
        return ResponseEntity.ok(
                new ResponseMessage(
                        200,
                        "Success",
                        vehicleService.findAll()
                )
        );

    }

    @PutMapping("/updateVehicle/{id}")
    public ResponseEntity<ResponseMessage> updateVehicle(@PathVariable int id, @RequestBody VehicleDTO vehicleData) {
        return ResponseEntity.ok(vehicleService.updateVehicle(id, vehicleData));
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
