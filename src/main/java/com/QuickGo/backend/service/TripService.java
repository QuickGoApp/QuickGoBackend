package com.QuickGo.backend.service;

import com.QuickGo.backend.DTO.FavoriteDriverDTO;
import com.QuickGo.backend.DTO.TripRequestDetailDTO;
import org.springframework.http.ResponseEntity;

public interface TripService {
    ResponseEntity<?> saveTripRequest(TripRequestDetailDTO requestDetailDTO)throws Exception;

    ResponseEntity<?> saveFavoriteDriver(FavoriteDriverDTO favoriteDriverDTO)throws Exception;

    ResponseEntity<?> getDriverTrip(FavoriteDriverDTO favoriteDriverDTO)throws Exception;
}
