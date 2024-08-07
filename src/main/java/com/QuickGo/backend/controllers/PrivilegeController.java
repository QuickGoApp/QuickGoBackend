package com.QuickGo.backend.controllers;

import com.QuickGo.backend.DTO.PrivilegeDTO;
import com.QuickGo.backend.exception.CustomException;
import com.QuickGo.backend.service.PrivilegeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/power-gym/privilege")
public class PrivilegeController {
    @Autowired
    private PrivilegeService privilegeService;

    @PostMapping(value = "addPrivilege")
    ResponseEntity<?> addNewPrivilege(@RequestBody PrivilegeDTO privilegeDTO) throws Exception{
        try {
            return privilegeService.addPrivilege(privilegeDTO);
        } catch (CustomException e) {
            throw new CustomException( e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @PostMapping(value = "getAllPrivileges")
    ResponseEntity<?> getAllPrivileges() throws Exception{
        try {
            return privilegeService.getAllPrivileges();
        } catch (CustomException e) {
            throw new CustomException(e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
