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
                .favoriteID(1)
                .build();
        locationDriverDTOS.add(dtoValues);

        GeoLocationDriverDTO dtoValues2 = GeoLocationDriverDTO.builder()
                .coordinates(CoordinatesDTO.builder().lat(6.911522).lng(79.867240).build())
                .name("Siripala")// get user
                .type("bicycle")//get vehicle
                .icon("assets/img/vehicle/bicicon.png") // this type wise hardcode (Car/three_wheel/bike)
                .image("https://www.lendingexpert.co.uk/wp-content/uploads/2018/10/Motorbike-finance-guide-and-information.jpg")//get vehicle table
                .vehicleNumber("ACC-4433")//get vehicle table
                .color("green")//get vehicle table
                .rate(2) // user table
                .seats(1)//get vehicle table
                .isFavorite(false) //this mean passenger wise check favorite driver. get is active (1 or 0)
                .userCode("U0034") // get user table
                .favoriteID(2)  // this mean passenger wise check favorite driver. get the table id
                .build();
        locationDriverDTOS.add(dtoValues2);

        return new ResponseEntity<>(new ResponseMessage(HttpStatus.OK.value(), "success", locationDriverDTOS), HttpStatus.OK);

    }
}
