package com.QuickGo.backend.service.impl;

import com.QuickGo.backend.DTO.UserDTO;
import com.QuickGo.backend.DTO.VehicleDTO;
import com.QuickGo.backend.DTO.common.ResponseMessage;
import com.QuickGo.backend.models.Role;
import com.QuickGo.backend.models.User;
import com.QuickGo.backend.models.Vehicle;
import com.QuickGo.backend.repository.UserRepository;
import com.QuickGo.backend.repository.VehicleRepository;
import com.QuickGo.backend.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class VehicleServiceImpl implements VehicleService {
    @Autowired
    private VehicleRepository vehicleRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public ResponseEntity<?> saveVehicle(VehicleDTO vehicleDTO) throws Exception {
        Vehicle vehicle = new Vehicle();
        vehicle.setVehicleName(vehicleDTO.getVehicle_name());
        vehicle.setVehicleNumber(vehicleDTO.getVehicle_number());
        vehicle.setType(vehicleDTO.getType());
        vehicle.setColor(vehicleDTO.getColor());
        vehicle.setVehicleConditions(vehicleDTO.getVehicle_conditions());
        vehicle.setSeats(vehicleDTO.getSeats());
        vehicle.setIsActive(1);
        Vehicle vehicles = vehicleRepository.save(vehicle);

        Optional<User> driver = userRepository.findById(vehicleDTO.getSelectedDriver());
        if (driver.isPresent()) {
            User driverUser = driver.get();
            driverUser.setVehicle(vehicles);
            userRepository.save(driverUser);
        }
        return ResponseEntity.ok(new ResponseMessage("User registered successfully!"));
    }
}
