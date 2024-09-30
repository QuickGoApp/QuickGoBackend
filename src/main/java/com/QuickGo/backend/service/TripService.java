package com.QuickGo.backend.service;

import com.QuickGo.backend.dto.FavoriteDriverDTO;
import com.QuickGo.backend.dto.TripRequestDetailDTO;
import com.QuickGo.backend.dto.common.ResponseMessage;
import org.springframework.http.ResponseEntity;

public interface TripService {
    ResponseEntity<?> saveTripRequest(TripRequestDetailDTO requestDetailDTO)throws Exception;

    ResponseEntity<?> saveFavoriteDriver(FavoriteDriverDTO favoriteDriverDTO)throws Exception;

    ResponseEntity<?> getDriverTrip(FavoriteDriverDTO favoriteDriverDTO)throws Exception;

    ResponseMessage cancelTripRequest(TripRequestDetailDTO requestDetailDTO);

    ResponseMessage driverCancelTripRequest(TripRequestDetailDTO requestDetailDTO);

    ResponseMessage acceptTripRequest(TripRequestDetailDTO requestDetailDTO) throws Exception;

    ResponseMessage endTripRequest(TripRequestDetailDTO requestDetailDTO);
}
