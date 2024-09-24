package com.QuickGo.backend.service.impl;

import com.QuickGo.backend.DTO.UserDTO;
import com.QuickGo.backend.models.Role;
import com.QuickGo.backend.models.User;
import com.QuickGo.backend.repository.UserRepository;
import com.QuickGo.backend.service.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class DriverServiceImpl implements DriverService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public ResponseEntity<List<UserDTO>> getDrivers() throws Exception {
        List<User> drivers = userRepository.findByRoles_Id(2); // Assuming 2 is the RoleId for drivers
        List<UserDTO> driverDTOs = new ArrayList<>();
        for (User user: drivers) {
            UserDTO userDTO = new UserDTO();
            userDTO.setId(user.getId());
            //userDTO.setRole_id(user.getRoles());
            Set<Role> roles = user.getRoles();
            String roleName = roles.stream()
                    .findFirst()
                    .map(role -> role.getName().name())  // Get the name of the ERole enum
                    .orElse("No role assigned");

            userDTO.setRole_id(roleName);

            userDTO.setName(user.getName());
            userDTO.setUser_code(user.getUserCode());
            userDTO.setAddress(user.getAddress());
            userDTO.setEmail(user.getEmail());
            userDTO.setMobile_num(user.getMobileNum());
            userDTO.setUsername(user.getUsername());
            userDTO.setPassword(user.getPassword());
            String status = (user.getIsActive() == 1) ? "Active" : "Inactive";
            userDTO.setIs_active(status);
            driverDTOs.add(userDTO);
        }

        return ResponseEntity.ok(driverDTOs);
    }

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

            // Return response with updated user
            return ResponseEntity.ok(updatedUser);
        } else {
            // Return 404 Not Found if user is not present
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @Override
    public ResponseEntity<Void> deleteDriver(Long id) throws Exception {
        Optional<User> userOptional = userRepository.findById(id);

        if (userOptional.isPresent()) {
            userRepository.deleteById(id);
            return ResponseEntity.ok().build();  // Return a 200 OK response with no content
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();  // Return 404 if user not found
        }
    }
}
