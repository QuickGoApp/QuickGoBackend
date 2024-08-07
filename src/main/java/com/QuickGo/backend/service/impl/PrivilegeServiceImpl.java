package com.QuickGo.backend.service.impl;

import com.QuickGo.backend.DTO.PrivilegeDTO;
import com.QuickGo.backend.DTO.common.ResponseMessage;
import com.QuickGo.backend.exception.CustomException;
import com.QuickGo.backend.models.Privilege;
import com.QuickGo.backend.repository.PrivilegeRepository;
import com.QuickGo.backend.service.PrivilegeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PrivilegeServiceImpl implements PrivilegeService {

    @Autowired
    private PrivilegeRepository privilegeRepository;
    final ModelMapper modelMapper;

    public PrivilegeServiceImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public ResponseEntity<?> addPrivilege(PrivilegeDTO privilegeDTO) throws Exception {
        if (privilegeDTO.getPrivilegeName().isEmpty()){
            throw new CustomException("privilege Name is empty");
        }
        privilegeDTO.setStatus(1);
        privilegeRepository.save(modelMapper.map(privilegeDTO, Privilege.class));

        return new ResponseEntity<>(new ResponseMessage(HttpStatus.OK.value(), "success"), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getAllPrivileges() throws Exception {
        List<PrivilegeDTO> privilegeDTOList = privilegeRepository.findAll().stream().map((element) -> modelMapper.map(element, PrivilegeDTO.class)).toList();
//        GeneralUtil.isListEmptyException(privilegeDTOList);
        return new ResponseEntity<>(new ResponseMessage(HttpStatus.OK.value(), "success",privilegeDTOList), HttpStatus.OK);

    }

    @Override
    public ResponseEntity<?> assignPrivileges(PrivilegeDTO privilegeDTO) throws Exception {
        return null;
    }
}
