package com.QuickGo.backend.service;

import com.QuickGo.backend.dto.VehicleDTO;
import com.QuickGo.backend.dto.common.ResponseMessage;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;


public interface VehicleService {
    List<VehicleDTO> findAll();

    ResponseMessage saveVehicle(VehicleDTO vehicleDTO);

    ResponseMessage updateVehicle(int id, VehicleDTO vehicleData);

    ResponseEntity<Void> deleteVehicle(@PathVariable int id) throws Exception;


}
