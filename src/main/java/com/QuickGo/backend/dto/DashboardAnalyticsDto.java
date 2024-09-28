package com.QuickGo.backend.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DashboardAnalyticsDto {

    private long totalDrivers;
    private long totalPassengers;
    private long totalActiveTrips;
    private long totalCompletedTrips;

    public DashboardAnalyticsDto(long totalDrivers, long totalPassengers, long totalActiveTrips, long totalCompletedTrips) {
        this.totalDrivers = totalDrivers;
        this.totalPassengers = totalPassengers;
        this.totalActiveTrips = totalActiveTrips;
        this.totalCompletedTrips = totalCompletedTrips;
    }


}
