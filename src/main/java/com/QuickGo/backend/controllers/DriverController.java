package com.QuickGo.backend.controllers;

import com.QuickGo.backend.dto.DriverCoordinateDto;
import com.QuickGo.backend.dto.UserDTO;
import com.QuickGo.backend.dto.common.ResponseMessage;
import com.QuickGo.backend.exception.CustomException;
import com.QuickGo.backend.models.User;
import com.QuickGo.backend.service.DriverService;
import com.QuickGo.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/quickGo/driver")
public class DriverController {
    private final UserService userService;
    @Autowired
    private DriverService driverService;

    @GetMapping("/drivers")
    public ResponseEntity<ResponseMessage> getDrivers() {
        return ResponseEntity.ok(
                new ResponseMessage(
                        HttpStatus.OK.value(),
                        "success",
                        driverService.getDrivers()
                )
        );

    }

    @GetMapping("/drivers/idle")
    public ResponseEntity<ResponseMessage> getIdleDrivers() {
        return ResponseEntity.ok(
                new ResponseMessage(
                        HttpStatus.OK.value(),
                        "success",
                        driverService.getIdleDrivers()
                )
        );

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

    @PostMapping(value = "/getGeolocationDrivers")
    public ResponseEntity<ResponseMessage> findByUserCodes(@RequestBody List<DriverCoordinateDto> userCodes) {
        return ResponseEntity.ok(
                new ResponseMessage(
                        HttpStatus.OK.value(),
                        "success",
                        userService.findByUserCodes(userCodes)
                )
        );
    }
}
