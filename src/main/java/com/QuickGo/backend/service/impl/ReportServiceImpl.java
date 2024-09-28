package com.QuickGo.backend.service.impl;

import com.QuickGo.backend.Util.UtilService;
import com.QuickGo.backend.dto.ReportRequestDto;
import com.QuickGo.backend.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private UtilService utilService;

    @Override
    public void paymentReport(ReportRequestDto request) {

        Date fromDate = utilService.getStartOfDay(request.getFromDate());
        Date toDate = utilService.getEndOfDay(request.getToDate());
        String driverCode = request.getDriverCode();




    }
}
