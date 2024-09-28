package com.QuickGo.backend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DriverCoordinateDto {
    private String driverId;
    private double latitude;
    private double longitude;
}
