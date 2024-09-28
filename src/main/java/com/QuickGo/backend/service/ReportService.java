package com.QuickGo.backend.service;

import com.QuickGo.backend.dto.report.TripReportDto;
import com.QuickGo.backend.dto.report.ReportRequestDto;
import com.QuickGo.backend.dto.report.RequestCancellationReportDto;

import java.util.List;

public interface ReportService {

    List<TripReportDto> getTripReport(ReportRequestDto request);

}
