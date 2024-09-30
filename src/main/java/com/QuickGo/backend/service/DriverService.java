package com.QuickGo.backend.service;

import com.QuickGo.backend.dto.UserDTO;
import com.QuickGo.backend.models.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface DriverService {
    ResponseEntity<Void> deleteDriver(@PathVariable Long id)throws Exception;
    List<UserDTO> getDrivers();
    List<UserDTO> getIdleDrivers();
}
