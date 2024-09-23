package com.QuickGo.backend.DTO;

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
    private Double totalAmount;
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
    private int isActive;
}
