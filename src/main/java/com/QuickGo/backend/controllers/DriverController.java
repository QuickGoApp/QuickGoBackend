package com.QuickGo.backend.controllers;

import com.QuickGo.backend.DTO.UserDTO;
import com.QuickGo.backend.exception.CustomException;
import com.QuickGo.backend.models.User;
import com.QuickGo.backend.service.DriverService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/quickGo/auth/driver")
@RequiredArgsConstructor
public class DriverController {
    @Autowired
    private DriverService driverService;

    @GetMapping("/drivers")
    public ResponseEntity<List<UserDTO>> getDrivers() throws CustomException {
        try {
            return driverService.getDrivers();
        } catch (CustomException e) {
            throw new CustomException(e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

    }

    @PutMapping("/updateDriver/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody UserDTO userData) throws CustomException {
        try {
            return driverService.updateDriver(id, userData);
        } catch (CustomException e) {
            throw new CustomException(e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

    }

    @DeleteMapping("/deleteDriver/{id}")
    public ResponseEntity<Void> deleteDriver(@PathVariable Long id) throws CustomException {
        try {
            return driverService.deleteDriver(id);
        } catch (CustomException e) {
            throw new CustomException(e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

}
