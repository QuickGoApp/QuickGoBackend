package com.QuickGo.backend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DriverCoordinateDto {
    private String driverCode;
    private double lat;
    private double lng;
}
