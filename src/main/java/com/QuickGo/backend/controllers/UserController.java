package com.QuickGo.backend.controllers;

import com.QuickGo.backend.dto.UserDTO;
import com.QuickGo.backend.dto.common.ResponseMessage;
import com.QuickGo.backend.models.enums.ERole;
import com.QuickGo.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/quickGo/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/all")
    public ResponseEntity<ResponseMessage> findAllUsers() {
        return ResponseEntity.ok(
                new ResponseMessage(
                        HttpStatus.OK.value(),
                        "Success",
                        userService.findAllUsers()
                )
        );
    }

    @GetMapping("/passengers")
    public ResponseEntity<ResponseMessage> findAllPassengers() {
        return ResponseEntity.ok(
                new ResponseMessage(
                        HttpStatus.OK.value(),
                        "Success",
                        userService.findByUserRole(ERole.ROLE_PASSENGER)
                )
        );
    }

    @PostMapping("/by/code")
    public ResponseEntity<ResponseMessage> findByCode(@RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(
                new ResponseMessage(
                        HttpStatus.OK.value(),
                        "Success",
                        userService.findByCode(userDTO.getUser_code())
                )
        );
    }

    @PutMapping("/update")
    public ResponseEntity<ResponseMessage> updateUser(@RequestBody UserDTO userData) {
        return ResponseEntity.ok(userService.update(userData));
    }


}
