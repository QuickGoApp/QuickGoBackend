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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;


@Service
public class VehicleServiceImpl implements VehicleService {
    @Autowired
    private VehicleRepository vehicleRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public ResponseEntity<List<VehicleDTO>> getVehicles() throws Exception {
        List<User> usersWithVehicle = userRepository.findByVehicleIsNotNull();
        List<VehicleDTO> vehicleDTOs = new ArrayList<>();

        for (User user : usersWithVehicle) {
            VehicleDTO vehicleDTO = new VehicleDTO();
            Vehicle vehicle = vehicleRepository.findById((long) user.getVehicle().getVehicleid()).orElse(null);

            if (vehicle != null) {
                vehicleDTO.setId(vehicle.getVehicleid());
                vehicleDTO.setVehicle_name(vehicle.getVehicleName());
                vehicleDTO.setVehicle_number(vehicle.getVehicleNumber());
                vehicleDTO.setType(vehicle.getType());
                vehicleDTO.setColor(vehicle.getColor());
                vehicleDTO.setVehicle_conditions(vehicle.getVehicleConditions());
                vehicleDTO.setSeats(vehicle.getSeats());
                String status = (vehicle.getIsActive() == 1) ? "Available" : "Not Available";
                vehicleDTO.setIsActive(status);
                vehicleDTO.setSelectedDriverName(user.getName());
                vehicleDTOs.add(vehicleDTO);
            }
        }
        return ResponseEntity.ok(vehicleDTOs);
    }

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
        return ResponseEntity.ok(new ResponseMessage("Saved vehicle successfully!"));
    }

    @Override
    public ResponseEntity<Vehicle> updateVehicle(int id, VehicleDTO vehicleData) throws Exception {
        Optional<Vehicle> existVehicle = vehicleRepository.findById((long) id);


        if (existVehicle.isPresent()) {
            Vehicle vehicle = existVehicle.get();
            // Update vehicle details
            vehicle.setVehicleName(vehicleData.getVehicle_name());
            vehicle.setVehicleNumber(vehicleData.getVehicle_number());
            vehicle.setType(vehicleData.getType());
            vehicle.setColor(vehicleData.getColor());
            vehicle.setVehicleConditions(vehicleData.getVehicle_conditions());
            vehicle.setSeats(vehicleData.getSeats());
            vehicle.setUser(vehicleData.getSelectedDriver());

            Vehicle updatedVehicle = vehicleRepository.save(vehicle);

            // Return response with updated vehicle details
            return ResponseEntity.ok(updatedVehicle);
        } else {
            // Return 404 Not Found if vehicle is not present
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @Override
    public ResponseEntity<Void> deleteVehicle(int id) throws Exception {
        Optional<Vehicle> vehicleOptional = vehicleRepository.findById((long) id);

        if (vehicleOptional.isPresent()) {
            Vehicle vehicle = vehicleOptional.get();
            vehicle.setIsActive(0);
            vehicleRepository.save(vehicle);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
