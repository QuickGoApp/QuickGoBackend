package com.QuickGo.backend.controllers;

import com.QuickGo.backend.DTO.GeoLocationDriverDTO;
import com.QuickGo.backend.service.UserService;
import com.QuickGo.backend.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/quickGo/driver")
public class DriverController {

    private final UserService userService;

    @PostMapping(value = "/by/userCodes")
    public ResponseEntity<List<GeoLocationDriverDTO>> findByUserCodes(@RequestBody List<String> userCodes) {
        return  ResponseEntity.ok(userService.findByUserCodes(userCodes));
    }

}
