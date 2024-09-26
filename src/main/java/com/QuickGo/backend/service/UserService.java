package com.QuickGo.backend.service;

import com.QuickGo.backend.DTO.GeoLocationDriverDTO;
import org.springframework.http.HttpStatusCode;

import java.util.List;

public interface UserService {
    List<GeoLocationDriverDTO> findByUserCodes(List<String> userCodes);
}
