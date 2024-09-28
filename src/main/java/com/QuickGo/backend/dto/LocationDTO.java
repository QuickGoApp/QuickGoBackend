package com.QuickGo.backend.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LocationDTO {
    private CoordinatesDTO pickupLocation;
    private CoordinatesDTO dropLocation;
    private String vehicleType;
    private String contactNumber;
}
