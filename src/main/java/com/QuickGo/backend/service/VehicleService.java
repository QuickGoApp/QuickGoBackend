package com.QuickGo.backend.service;

import com.QuickGo.backend.DTO.VehicleDTO;
import org.springframework.http.ResponseEntity;


public interface VehicleService {
    ResponseEntity<?> saveVehicle(VehicleDTO vehicleDTO) throws Exception;


}
