package com.QuickGo.backend.DTO;
import lombok.Data;

import java.util.Set;

@Data
public class VehicleDTO {
    private String vehicle_name;
    private String vehicle_number;
    private String type;
    private String color;
    private Double vehicle_conditions;
    private int seats;
    private Long selectedDriver;

    public VehicleDTO() {
    }

    public VehicleDTO(String vehicle_name, String vehicle_number, String type, String color, Double vehicle_conditions, int seats, Long selectedDriver) {
        this.vehicle_name = vehicle_name;
        this.vehicle_number = vehicle_number;
        this.type = type;
        this.color = color;
        this.vehicle_conditions = vehicle_conditions;
        this.seats = seats;
        this.selectedDriver = selectedDriver;
    }

    public String getVehicle_name() {
        return vehicle_name;
    }

    public void setVehicle_name(String vehicle_name) {
        this.vehicle_name = vehicle_name;
    }

    public String getVehicle_number() {
        return vehicle_number;
    }

    public void setVehicle_number(String vehicle_number) {
        this.vehicle_number = vehicle_number;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Double getVehicle_conditions() {
        return vehicle_conditions;
    }

    public void setVehicle_conditions(Double vehicle_conditions) {
        this.vehicle_conditions = vehicle_conditions;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    public Long getSelectedDriver() {
        return selectedDriver;
    }

    public void setSelectedDriver(Long selectedDriver) {
        this.selectedDriver = selectedDriver;
    }
}
