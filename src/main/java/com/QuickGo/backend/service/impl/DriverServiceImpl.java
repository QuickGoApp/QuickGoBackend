package com.QuickGo.backend.service.impl;

import com.QuickGo.backend.dto.UserDTO;
import com.QuickGo.backend.models.User;
import com.QuickGo.backend.models.enums.ERole;
import com.QuickGo.backend.repository.UserRepository;
import com.QuickGo.backend.service.DriverService;
import com.QuickGo.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DriverServiceImpl implements DriverService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    @Override
    public ResponseEntity<User> updateDriver(Long id, UserDTO userData) throws Exception {
        Optional<User> existUser = userRepository.findById(id);

        if (existUser.isPresent()) {
            User user = existUser.get();
            // Update user details
            user.setName(userData.getName());
            user.setUserCode(userData.getUser_code());
            user.setAddress(userData.getAddress());
            user.setEmail(userData.getEmail());
            user.setMobileNum(userData.getMobile_num());
            user.setUsername(userData.getUsername());


            User updatedUser = userRepository.save(user);

            // Return response with updated user details
            return ResponseEntity.ok(updatedUser);
        } else {
            // Return 404 Not Found if user is not available
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @Override
    public ResponseEntity<Void> deleteDriver(Long id) throws Exception {
        Optional<User> userOptional = userRepository.findById(id);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setIsActive(0);
            userRepository.save(user);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Override
    public List<UserDTO> getDrivers() {
        return userService.findByUserRole(ERole.ROLE_DRIVER);
    }
}
