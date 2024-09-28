package com.QuickGo.backend.dto.report;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@RequiredArgsConstructor
public class PaymentReportDto {
    private String driverCode;
    private String driverName;
    private String passengerCode;
    private String passengerName;
    private Date tripDate;
    private double amount;

    public PaymentReportDto(String driverCode, String driverName, String passengerCode, String passengerName, Date tripDate, double amount) {
        this.driverCode = driverCode;
        this.driverName = driverName;
        this.passengerCode = passengerCode;
        this.passengerName = passengerName;
        this.tripDate = tripDate;
        this.amount = amount;
    }

}
