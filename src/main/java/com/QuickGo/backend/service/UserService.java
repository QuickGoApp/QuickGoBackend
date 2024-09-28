package com.QuickGo.backend.service;

import com.QuickGo.backend.dto.DriverCoordinateDto;
import com.QuickGo.backend.dto.GeoLocationDriverDTO;
import com.QuickGo.backend.dto.UserDTO;
import com.QuickGo.backend.dto.common.ResponseMessage;
import com.QuickGo.backend.models.enums.ERole;

import java.util.List;

public interface UserService {
    List<GeoLocationDriverDTO> findByUserCodes(List<DriverCoordinateDto> userCodes);
    List<UserDTO> findByUserRole(ERole eRole);

    List<UserDTO> findAllUsers();

    ResponseMessage update(Long id, UserDTO userData);
}
