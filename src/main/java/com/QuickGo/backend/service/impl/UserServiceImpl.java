package com.QuickGo.backend.service.impl;

import com.QuickGo.backend.dto.CoordinatesDTO;
import com.QuickGo.backend.dto.DriverCoordinateDto;
import com.QuickGo.backend.dto.GeoLocationDriverDTO;
import com.QuickGo.backend.dto.UserDTO;
import com.QuickGo.backend.models.Role;
import com.QuickGo.backend.models.User;
import com.QuickGo.backend.models.enums.ERole;
import com.QuickGo.backend.repository.RoleRepository;
import com.QuickGo.backend.repository.UserRepository;
import com.QuickGo.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public List<GeoLocationDriverDTO> findByUserCodes(List<DriverCoordinateDto> request) {
        List<String> userCodes = request.stream()
                .map(DriverCoordinateDto::getDriverId)
                .toList();
        return userRepository.findByUserCodeIn(userCodes)
                .stream()
                .filter(user -> user.getVehicle() != null)
                .map(x -> toGeoLocationDriverDTO(x, request))
                .toList();
    }

    @Override
    public List<UserDTO> findByUserRole(ERole eRole) {

        Role role = roleRepository.findByName(eRole)
                .orElseThrow(() -> new RuntimeException("Role not found"));

        return userRepository.findByRolesContains(role).stream()
                .map(x -> toUserDto(x, role))
                .toList();

    }

    @Override
    public List<UserDTO> findAllUsers() {
        return userRepository.findAll().stream()
                .map(this::toUserDto)
                .toList();
    }

    private UserDTO toUserDto(User user) {
        Set<Role> roles = user.getRoles();
        Role role = roles.stream().findFirst().orElseThrow();
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
                user.getIsActive() == 1 ? "Active" : "Inactive"
        );

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

    private GeoLocationDriverDTO toGeoLocationDriverDTO(User driver, List<DriverCoordinateDto> request) {
        DriverCoordinateDto driverCoordinateDto = request.stream()
                .filter(x -> x.getDriverId().equals(driver.getUserCode()))
                .findFirst()
                .orElseThrow();
        return GeoLocationDriverDTO.builder()
                .coordinates(CoordinatesDTO.builder()
                        .lat(driverCoordinateDto.getLatitude())
                        .lng(driverCoordinateDto.getLongitude())
                        .build())
                .name(driver.getName())
                .type(driver.getVehicle().getType())
                .icon(driver.getVehicle().getIcon())
                .image(driver.getVehicle().getImage())
                .vehicleNumber(driver.getVehicle().getVehicleNumber())
                .color(driver.getVehicle().getColor())
                .rate(driver.getOverallRating())
                .seats(driver.getVehicle().getSeats())
                .isFavorite(true)
                .userCode(driver.getUserCode())
                .favoriteID(1)
                .build();
    }


}
