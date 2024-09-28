package com.QuickGo.backend.dto.report;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@RequiredArgsConstructor
public class TripReportDto {
    private String driverCode;
    private String driverName;
    private String passengerCode;
    private String passengerName;
    private Date tripDate;
    private double amount;
    private String status;

    public TripReportDto(
            String driverCode,
            String driverName,
            String passengerCode,
            String passengerName,
            Date tripDate,
            double amount,
            String status

    ) {
        this.driverCode = driverCode;
        this.driverName = driverName;
        this.passengerCode = passengerCode;
        this.passengerName = passengerName;
        this.tripDate = tripDate;
        this.amount = amount;
        this.status = status;
    }

}
