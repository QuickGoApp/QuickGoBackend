package com.QuickGo.backend.service.impl;

import com.QuickGo.backend.DTO.CoordinatesDTO;
import com.QuickGo.backend.DTO.GeoLocationDriverDTO;
import com.QuickGo.backend.DTO.LocationDTO;
import com.QuickGo.backend.DTO.common.ResponseMessage;
import com.QuickGo.backend.service.LocationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LocationServiceImpl implements LocationService {
    @Override
    public ResponseEntity<?> getGeolocationDrivers(LocationDTO locationDTO) throws Exception {

//        rest template using cll the anuradha api. and get the values list
       List <GeoLocationDriverDTO> locationDriverDTOS = new ArrayList<>();

        GeoLocationDriverDTO dtoValues = GeoLocationDriverDTO.builder()
                .coordinates(CoordinatesDTO.builder().lat(6.845000).lng(79.935457).build())
                .name("keshan")
                .type("Three wheel")
                .icon("assets/img/vehicle/tuckicon.png")
                .image("https://www.shutterstock.com/image-illustration/auto-rickshaw-bajaj-tuktuk-3d-260nw-2187568303.jpg")
                .vehicleNumber("ABC-123")
                .color("blue")
                .rate(5)
                .seats(3)
                .isFavorite(true)
                .userCode("U001")
                .build();
        locationDriverDTOS.add(dtoValues);

        GeoLocationDriverDTO dtoValues2 = GeoLocationDriverDTO.builder()
                .coordinates(CoordinatesDTO.builder().lat(6.911522).lng(79.867240).build())
                .name("Siripala")
                .type("bicycle")
                .icon("assets/img/vehicle/bicicon.png")
                .image("https://www.lendingexpert.co.uk/wp-content/uploads/2018/10/Motorbike-finance-guide-and-information.jpg")
                .vehicleNumber("ACC-4433")
                .color("green")
                .rate(2)
                .seats(1)
                .isFavorite(true)
                .userCode("U0034")
                .build();
        locationDriverDTOS.add(dtoValues2);

        return new ResponseEntity<>(new ResponseMessage(HttpStatus.OK.value(), "success", locationDriverDTOS), HttpStatus.OK);

    }
}
