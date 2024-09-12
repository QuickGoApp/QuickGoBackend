package com.QuickGo.backend.service;

import com.QuickGo.backend.DTO.PrivilegeDTO;
import org.springframework.http.ResponseEntity;

public interface PrivilegeService {

    ResponseEntity<?> addPrivilege(PrivilegeDTO privilegeDTO) throws Exception;

    ResponseEntity<?> getAllPrivileges() throws Exception;

    ResponseEntity<?> assignPrivileges(PrivilegeDTO privilegeDTO) throws Exception;

    ResponseEntity<?> getRoleWisePrivilege(PrivilegeDTO privilegeDTO)throws Exception;
}
