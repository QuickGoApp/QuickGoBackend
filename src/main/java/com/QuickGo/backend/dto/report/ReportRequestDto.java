package com.QuickGo.backend.dto.report;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ReportRequestDto {
    private Date fromDate;
    private Date toDate;
    private String driverCode;
    private String passengerCode;
    private String status;
}
