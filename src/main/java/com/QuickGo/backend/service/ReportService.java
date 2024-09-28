package com.QuickGo.backend.service;

import com.QuickGo.backend.dto.report.PaymentReportDto;
import com.QuickGo.backend.dto.report.ReportRequestDto;

import java.util.List;

public interface ReportService {

    List<PaymentReportDto> getPaymentReport(ReportRequestDto request);

}
