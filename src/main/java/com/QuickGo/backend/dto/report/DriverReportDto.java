package com.QuickGo.backend.dto.report;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class DriverReportDto {
    private String driverCode;
    private String driverName;
    private String driverPhone;
    private Date registerDate;
    private int rating;
    private String vehicleName;
    private String vehicleNumber;
    private String vehicleType;
    private String vehicleColor;

    public DriverReportDto(String driverCode, String driverName, String driverPhone, Date registerDate, int rating, String vehicleName, String vehicleNumber, String vehicleType, String vehicleColor) {
        this.driverCode = driverCode;
        this.driverName = driverName;
        this.driverPhone = driverPhone;
        this.registerDate = registerDate;
        this.rating = rating;
        this.vehicleName = vehicleName;
        this.vehicleNumber = vehicleNumber;
        this.vehicleType = vehicleType;
        this.vehicleColor = vehicleColor;
    }

}
