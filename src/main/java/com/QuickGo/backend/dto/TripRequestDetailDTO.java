package com.QuickGo.backend.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TripRequestDetailDTO {
    private Integer tripID;
    private String passengerCode;
    private String driveCode;
    private double totalAmount;
    private String paymentMethod;
    private String status;
    private String driverComment;
    private double pickupLat;
    private double pickupLng;
    private double dropLat;
    private double dropLng;
    private String passengerComment;
    private String createDateTime;
    private String updateDateTime;
    private String friendContact;
    private String contactNumber;
    private int isActive;
}
