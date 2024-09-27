package com.QuickGo.backend.service;

import com.QuickGo.backend.DTO.UserDTO;
import com.QuickGo.backend.DTO.VehicleDTO;
import com.QuickGo.backend.models.User;
import com.QuickGo.backend.models.Vehicle;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;


public interface VehicleService {
    ResponseEntity<List<VehicleDTO>> getVehicles()throws Exception;

    ResponseEntity<?> saveVehicle(VehicleDTO vehicleDTO) throws Exception;

    ResponseEntity<Vehicle> updateVehicle(@PathVariable int id, @RequestBody VehicleDTO vehicleData)throws Exception;

    ResponseEntity<Void> deleteVehicle(@PathVariable int id)throws Exception;



}
