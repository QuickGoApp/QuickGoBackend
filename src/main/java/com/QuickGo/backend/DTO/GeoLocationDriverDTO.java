package com.QuickGo.backend.DTO;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GeoLocationDriverDTO {
    private CoordinatesDTO coordinates; // To hold lat and lng
    private String name;
    private String type;
    private String icon;
    private String image;
    private String vehicleNumber;
    private String color;
    private double rate;
    private int seats;
    private boolean isFavorite;
    private String userCode;
}
