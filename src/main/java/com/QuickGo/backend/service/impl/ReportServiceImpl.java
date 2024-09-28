package com.QuickGo.backend.service.impl;

import com.QuickGo.backend.Util.UtilService;
import com.QuickGo.backend.dto.report.PaymentReportDto;
import com.QuickGo.backend.dto.report.ReportRequestDto;
import com.QuickGo.backend.models.Trip;
import com.QuickGo.backend.repository.TripRepository;
import com.QuickGo.backend.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private UtilService utilService;
    @Autowired
    private TripRepository tripRepository;

    @Override
    public List<PaymentReportDto> getPaymentReport(ReportRequestDto request) {
        validateReportRequest(request);
        Date fromDate = utilService.getStartOfDay(request.getFromDate());
        Date toDate = utilService.getEndOfDay(request.getToDate());
        String driverCode = request.getDriverCode();

        List<Trip> data;
        if ("All".equalsIgnoreCase(driverCode)) {
            data = tripRepository.findByCreateDateTimeBetween(fromDate, toDate);
        }else {
            data = tripRepository.findByCreateDateTimeBetweenAndDriveCode(fromDate, toDate, driverCode);
        }

        return data.stream().map(this::toPaymentReportDto).toList();

    }

    private PaymentReportDto toPaymentReportDto(Trip trip) {
        return new PaymentReportDto(
                trip.getDriveCode(),
                trip.getDriver().getUserCode(),
                trip.getPassengerCode(),
                trip.getPassenger().getUserCode(),
                trip.getCreateDateTime(),
                trip.getTotalAmount()
        );
    }

    private void validateReportRequest(ReportRequestDto request) {
        if (request.getDriverCode() == null) {
            throw new IllegalArgumentException("Driver code is required");
        }
        if (request.getFromDate() == null || request.getToDate() == null) {
            throw new IllegalArgumentException("Date range is required");
        }
        if (request.getFromDate().after(request.getToDate())) {
            throw new IllegalArgumentException("Invalid date range");
        }
    }
}
