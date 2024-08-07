package com.QuickGo.backend.exception;

import lombok.Data;

import java.util.Date;
@Data
public class ErrorResponse {
    private int statusCode;
    private String message;
    private Date dateTime;
}
