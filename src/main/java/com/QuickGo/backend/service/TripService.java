package com.QuickGo.backend.service;

import com.QuickGo.backend.DTO.FavoriteDriverDTO;
import com.QuickGo.backend.DTO.TripRequestDetailDTO;
import com.QuickGo.backend.DTO.common.ResponseMessage;
import org.springframework.http.ResponseEntity;

public interface TripService {
    ResponseEntity<?> saveTripRequest(TripRequestDetailDTO requestDetailDTO)throws Exception;

    ResponseEntity<?> saveFavoriteDriver(FavoriteDriverDTO favoriteDriverDTO)throws Exception;

    ResponseEntity<?> getDriverTrip(FavoriteDriverDTO favoriteDriverDTO)throws Exception;

    ResponseMessage cancelTripRequest(TripRequestDetailDTO requestDetailDTO);
}
