package com.QuickGo.backend.dto;
import lombok.Data;

@Data
public class VehicleDTO {
    private int id;
    private String vehicle_name;
    private String vehicle_number;
    private String type;
    private String color;
    private String icon;
    private String image;
    private Double vehicle_conditions;
    private int seats;
    private Long selectedDriver;
    private String selectedDriverName;
    private String isActive;

}
