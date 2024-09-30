package com.QuickGo.backend.service.impl;

import com.QuickGo.backend.dto.UserDTO;
import com.QuickGo.backend.models.Role;
import com.QuickGo.backend.models.User;
import com.QuickGo.backend.models.enums.ERole;
import com.QuickGo.backend.repository.RoleRepository;
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
    private RoleRepository roleRepository;
    @Autowired
    private UserService userService;

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

    @Override
    public List<UserDTO> getIdleDrivers() {
        Role role = roleRepository.findByName(ERole.ROLE_DRIVER)
                .orElseThrow(() -> new RuntimeException("Role not found"));

        return userRepository.findByRolesContains(role).stream()
                .filter(x -> x.getVehicle() == null)
                .map(x -> toUserDto(x, role))
                .toList();

    }

    private UserDTO toUserDto(User user, Role role) {
        return new UserDTO(
                user.getId(),
                user.getName(),
                user.getUserCode(),
                user.getAddress(),
                user.getEmail(),
                user.getMobileNum(),
                user.getUsername(),
                user.getPassword(),
                role.getName().toString(),
                role.getName().toString(),
                user.getIsActive() == 1 ? "Active" : "Inactive"
        );

    }
}
