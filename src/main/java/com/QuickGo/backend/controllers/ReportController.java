package com.QuickGo.backend.controllers;

import com.QuickGo.backend.dto.common.ResponseMessage;
import com.QuickGo.backend.dto.report.ReportRequestDto;
import com.QuickGo.backend.exception.CustomException;
import com.QuickGo.backend.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/quickGo/location")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @GetMapping("/drivers")
    public ResponseEntity<ResponseMessage> getDrivers(@RequestBody ReportRequestDto request) throws CustomException {
        return ResponseEntity.ok(
                new ResponseMessage(
                        HttpStatus.OK.value(),
                        "Success",
                        reportService.getPaymentReport(request)
                )
        );
    }

}
