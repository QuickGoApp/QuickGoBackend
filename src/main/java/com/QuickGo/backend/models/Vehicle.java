package com.QuickGo.backend.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "vehicle")
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer vehicleID;

    @Column(name = "rating",columnDefinition = "integer default 0")
    private int rating;

    @Column(name = "create_date_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createDateTime;

    @Column(name = "update_date_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateDateTime;

    @Column(name = "type")
    private String type;

    @Column(name = "vehicle_name")
    private String vehicleName;

    @Column(name = "vehicle_number")
    private String vehicleNumber;

    @Column(name = "color")
    private String color;

    @Column(name = "seats")
    private int seats;

    @Column(name = "vehicle_conditions")
    private Double vehicleCondition;

    @Column(name = "is_active", columnDefinition = "integer default 1")
    private int isActive;


}
