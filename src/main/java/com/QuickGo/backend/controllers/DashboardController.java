package com.QuickGo.backend.controllers;

import com.QuickGo.backend.dto.common.ResponseMessage;
import com.QuickGo.backend.dto.report.ReportRequestDto;
import com.QuickGo.backend.exception.CustomException;
import com.QuickGo.backend.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/quickGo/dashboard")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    @GetMapping("/admin/analytics")
    public ResponseEntity<ResponseMessage> getDashboardAnalytics() throws CustomException {
        return ResponseEntity.ok(
                new ResponseMessage(
                        HttpStatus.OK.value(),
                        "Success",
                        dashboardService.getDashboardAnalytics()
                )
        );
    }

}
