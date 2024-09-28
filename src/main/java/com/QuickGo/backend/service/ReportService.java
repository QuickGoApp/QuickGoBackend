package com.QuickGo.backend.service;

import com.QuickGo.backend.dto.UserDTO;
import com.QuickGo.backend.dto.report.TripReportDto;
import com.QuickGo.backend.dto.report.ReportRequestDto;

import java.util.List;

public interface ReportService {

    List<TripReportDto> getTripReport(ReportRequestDto request);

    List<UserDTO> getDriverReport(ReportRequestDto request);
}
