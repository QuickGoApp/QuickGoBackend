package com.QuickGo.backend.controllers;

import com.QuickGo.backend.dto.common.ResponseMessage;
import com.QuickGo.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
