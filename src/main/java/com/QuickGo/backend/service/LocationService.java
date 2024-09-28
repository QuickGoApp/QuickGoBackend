package com.QuickGo.backend.service;

import com.QuickGo.backend.dto.LocationDTO;
import org.springframework.http.ResponseEntity;

public interface LocationService {
    ResponseEntity<?> getGeolocationDrivers(LocationDTO locationDTO)throws Exception;
}
