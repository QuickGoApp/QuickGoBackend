package com.QuickGo.backend.service.impl;

import com.QuickGo.backend.dto.CoordinatesDTO;
import com.QuickGo.backend.dto.DriverCoordinateDto;
import com.QuickGo.backend.dto.GeoLocationDriverDTO;
import com.QuickGo.backend.models.User;
import com.QuickGo.backend.repository.UserRepository;
import com.QuickGo.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

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
