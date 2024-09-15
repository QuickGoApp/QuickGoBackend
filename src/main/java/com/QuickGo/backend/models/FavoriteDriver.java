package com.QuickGo.backend.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "favorite_driver")
public class FavoriteDriver {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "passenger_code")
    private String passengerCode;
    @Column(name = "driver_code")
    private String driverCode;

    @Column(name = "create_date_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createDateTime;

    @Column(name = "update_date_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateDateTime;
    @Column(name = "is_active",columnDefinition = "integer default 1")
    private int isActive ;
}
