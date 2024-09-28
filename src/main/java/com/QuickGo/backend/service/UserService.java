package com.QuickGo.backend.service;

import com.QuickGo.backend.dto.DriverCoordinateDto;
import com.QuickGo.backend.dto.GeoLocationDriverDTO;

import java.util.List;

public interface UserService {
    List<GeoLocationDriverDTO> findByUserCodes(List<DriverCoordinateDto> userCodes);
}
