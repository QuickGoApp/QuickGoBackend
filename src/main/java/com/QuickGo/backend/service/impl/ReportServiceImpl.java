package com.QuickGo.backend.service.impl;

import com.QuickGo.backend.Util.UtilService;
import com.QuickGo.backend.dto.report.TripReportDto;
import com.QuickGo.backend.dto.report.ReportRequestDto;
import com.QuickGo.backend.dto.report.RequestCancellationReportDto;
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
    public List<TripReportDto> getTripReport(ReportRequestDto request) {
        validateReportRequest(request);
        Date fromDate = utilService.getStartOfDay(request.getFromDate());
        Date toDate = utilService.getEndOfDay(request.getToDate());
        String driverCode = request.getDriverCode();
        String passengerCode = request.getPassengerCode();
        String status = request.getStatus();

        List<Trip> data;
        if ("All".equalsIgnoreCase(driverCode) && "All".equalsIgnoreCase(passengerCode) && "All".equalsIgnoreCase(status)) {
            data = tripRepository.findByCreateDateTimeBetween(fromDate, toDate);
        } else if ("All".equalsIgnoreCase(driverCode) && "All".equalsIgnoreCase(passengerCode)) {
            data = tripRepository.findByCreateDateTimeBetweenAndStatus(fromDate, toDate, status);
        } else if ("All".equalsIgnoreCase(driverCode) && "All".equalsIgnoreCase(status)) {
            data = tripRepository.findByCreateDateTimeBetweenAndPassengerCode(fromDate, toDate, passengerCode);
        } else if ("All".equalsIgnoreCase(passengerCode) && "All".equalsIgnoreCase(status)) {
            data = tripRepository.findByCreateDateTimeBetweenAndDriveCode(fromDate, toDate, driverCode);
        } else if ("All".equalsIgnoreCase(driverCode)) {
            data = tripRepository.findByCreateDateTimeBetweenAndPassengerCodeAndStatus(fromDate, toDate, passengerCode, status);
        } else if ("All".equalsIgnoreCase(passengerCode)) {
            data = tripRepository.findByCreateDateTimeBetweenAndDriveCodeAndStatus(fromDate, toDate, driverCode, status);
        } else if ("All".equalsIgnoreCase(status)) {
            data = tripRepository.findByCreateDateTimeBetweenAndDriveCodeAndPassengerCode(fromDate, toDate, driverCode, passengerCode);
        } else {
            data = tripRepository.findByCreateDateTimeBetweenAndDriveCodeAndPassengerCodeAndStatus(fromDate, toDate, driverCode, passengerCode, status);
        }

        return data.stream().map(this::toTripReportDto).toList();

    }


    private TripReportDto toTripReportDto(Trip trip) {
        return new TripReportDto(
                trip.getDriveCode(),
                trip.getDriver().getUserCode(),
                trip.getPassengerCode(),
                trip.getPassenger().getUserCode(),
                trip.getCreateDateTime(),
                trip.getTotalAmount(),
                trip.getStatus()
        );
    }

    private void validateReportRequest(ReportRequestDto request) {
        if (request.getDriverCode() == null) {
            throw new IllegalArgumentException("Driver code is required");
        }
        if (request.getPassengerCode() == null) {
            throw new IllegalArgumentException("Passenger code is required");
        }
        if (request.getFromDate() == null || request.getToDate() == null) {
            throw new IllegalArgumentException("Date range is required");
        }
        if (request.getFromDate().after(request.getToDate())) {
            throw new IllegalArgumentException("Invalid date range");
        }
        if (utilService.getDaysBetween(request.getFromDate(), request.getToDate()) > 100) {
            throw new IllegalArgumentException("Date range should not be more than 100 days");
        }

    }
}
