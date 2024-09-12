package com.QuickGo.backend.service.impl;

import com.QuickGo.backend.DTO.PrivilegeDTO;
import com.QuickGo.backend.DTO.common.ResponseMessage;
import com.QuickGo.backend.exception.CustomException;
import com.QuickGo.backend.models.Privilege;
import com.QuickGo.backend.models.PrivilegeDetails;
import com.QuickGo.backend.models.Role;
import com.QuickGo.backend.models.enums.ERole;
import com.QuickGo.backend.repository.PrivilegeDetailRepository;
import com.QuickGo.backend.repository.PrivilegeRepository;
import com.QuickGo.backend.repository.RoleRepository;
import com.QuickGo.backend.service.PrivilegeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PrivilegeServiceImpl implements PrivilegeService {

    @Autowired
    private PrivilegeRepository privilegeRepository;
    final ModelMapper modelMapper;

    @Autowired
    private PrivilegeDetailRepository privilegeDetailRepository;
    @Autowired
    RoleRepository roleRepository;


    public PrivilegeServiceImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public ResponseEntity<?> addPrivilege(PrivilegeDTO privilegeDTO) throws Exception {
        if (privilegeDTO.getPrivilegeName().isEmpty()) {
            throw new CustomException("privilege Name is empty");
        }
        privilegeDTO.setStatus(1);
        privilegeRepository.save(modelMapper.map(privilegeDTO, Privilege.class));

        return new ResponseEntity<>(new ResponseMessage(HttpStatus.OK.value(), "success"), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getAllPrivileges() throws Exception {
        List<PrivilegeDTO> privilegeDTOList = privilegeRepository.findAll().stream().map((element) -> modelMapper.map(element, PrivilegeDTO.class)).toList();
        return new ResponseEntity<>(new ResponseMessage(HttpStatus.OK.value(), "success", privilegeDTOList), HttpStatus.OK);

    }

    @Override
    public ResponseEntity<?> assignPrivileges(PrivilegeDTO privilegeDTO) throws Exception {
        if (privilegeDTO.getRole() == null || privilegeDTO.getRole().isEmpty()) {
            throw new CustomException("role is empty");
        }
        ERole eRole = ERole.valueOf(privilegeDTO.getRole());

        // Convert the role string to ERole enum
        eRole = ERole.valueOf(privilegeDTO.getRole());

        // Find the role using the ERole enum value
        Optional<Role> roleOptional = roleRepository.findByName(eRole);

        if (roleOptional.isPresent()) {
            Role role = roleOptional.get();  // Get the Role from Optional

            // Iterate through privileges and save or update PrivilegeDetails
            for (Privilege privilege : privilegeRepository.findAllById(privilegeDTO.getPrivilegeIds())) {
                PrivilegeDetails userPrivilegeDetails = new PrivilegeDetails();

                // Check if the privilege already exists for this role
                Optional<PrivilegeDetails> existingRecord = privilegeDetailRepository.findByPrivilegeAndRole(privilege, role);

                if (existingRecord.isPresent()) {
                    userPrivilegeDetails = existingRecord.get();  // Use the existing record if present
                }

                // Set privilege details and save
                userPrivilegeDetails.setPrivilege(privilege);
                userPrivilegeDetails.setRole(role);  // Associate the role
                userPrivilegeDetails.setStatus(privilegeDTO.getStatus());
                privilegeDetailRepository.save(userPrivilegeDetails);
            }
            return new ResponseEntity<>(new ResponseMessage(HttpStatus.OK.value(), "success"), HttpStatus.OK);
        } else {
            // Handle the case where the role is not found
            return new ResponseEntity<>(new ResponseMessage(HttpStatus.NOT_FOUND.value(), "Role not found"), HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<?> getRoleWisePrivilege(PrivilegeDTO privilegeDTO) throws Exception {

        // Check if the role is not empty
        if (privilegeDTO.getRole() != null && !privilegeDTO.getRole().isEmpty()) {

            ERole eRole = ERole.valueOf(privilegeDTO.getRole());

            // Find the role using the ERole enum value
            Optional<Role> roleOptional = roleRepository.findByName(eRole);

            // Check if the role is present
            if (roleOptional.isPresent()) {
                // Fetch privileges associated with the role
                List<PrivilegeDTO> privilegeDTOS = privilegeDetailRepository.findPrivilegeIdsByRole(roleOptional.get()).stream()
                        .map(this::toPrivilegeDto)
                        .toList();

                // Check if privilegeDTOS is not empty
                if (!privilegeDTOS.isEmpty()) {
                    return new ResponseEntity<>(new ResponseMessage(HttpStatus.OK.value(), "success", privilegeDTOS), HttpStatus.OK);
                } else {
                    throw new CustomException("Privilege details are empty");
                }
            } else {
                throw new CustomException("Role not found");
            }
        } else {
            throw new CustomException("Role details are empty");
        }
    }

    private PrivilegeDTO toPrivilegeDto(Privilege privilege) {
        return modelMapper.map(privilege, PrivilegeDTO.class);
    }

}
