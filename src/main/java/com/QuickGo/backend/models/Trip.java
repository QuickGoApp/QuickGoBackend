package com.QuickGo.backend.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "trip")
public class Trip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer tripID;

    @Column(name = "passenger_code")
    private String passengerCode;

    @Column(name = "drive_code")
    private String driveCode;

    @Column(name = "total_amount")
    private Double totalAmount;

    @Column(name = "payment_method")
    private String paymentMethod;

    @Column(name = "status")
    private String status;

    @Column(name = "driver_comment")
    private String driverComment;

    @Column(name = "pickup_lat")
    private double pickupLat;

    @Column(name = "pickup_lng")
    private double pickupLng;

    @Column(name = "drop_lat")
    private double dropLat;

    @Column(name = "drop_lng")
    private double dropLng;

    @Column(name = "passenger_comment")
    private String passengerComment;

    @Column(name = "create_date_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createDateTime;

    @Column(name = "update_date_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateDateTime;

    @Column(name = "is_active",columnDefinition = "integer default 1")
    private int isActive;

}
