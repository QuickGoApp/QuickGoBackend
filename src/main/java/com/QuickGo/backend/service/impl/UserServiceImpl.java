package com.QuickGo.backend.service.impl;

import com.QuickGo.backend.DTO.CoordinatesDTO;
import com.QuickGo.backend.DTO.GeoLocationDriverDTO;
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
    public List<GeoLocationDriverDTO> findByUserCodes(List<String> userCodes) {
        return userRepository.findByUserCodeIn(userCodes)
                .stream()
                .filter(user -> user.getVehicle() != null)
                .map(this::toGeoLocationDriverDTO)
                .toList();
    }

    private GeoLocationDriverDTO toGeoLocationDriverDTO(User driver) {
        return GeoLocationDriverDTO.builder()
                .name(driver.getName())
                .type(driver.getVehicle().getType())
                .icon(driver.getVehicle().getIcon())
                .image(driver.getVehicle().getImage())
                .vehicleNumber(driver.getVehicle().getVehicleNumber())
                .color(driver.getVehicle().getColor())
//                .rate(driver.getVehicle().getRate())
                .seats(driver.getVehicle().getSeats())
//                .isFavorite(driver.isFavorite())
                .userCode(driver.getUserCode())
//                .favoriteID(driver.getFavoriteID())
                .build();
    }


}
