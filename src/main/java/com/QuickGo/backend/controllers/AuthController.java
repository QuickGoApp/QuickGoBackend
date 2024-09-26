package com.QuickGo.backend.controllers;

import com.QuickGo.backend.DTO.*;
import com.QuickGo.backend.DTO.common.ResponseMessage;
import com.QuickGo.backend.Util.IdGenerationUtil;
import com.QuickGo.backend.models.Privilege;
import com.QuickGo.backend.models.Role;
import com.QuickGo.backend.models.User;
import com.QuickGo.backend.models.Vehicle;
import com.QuickGo.backend.models.enums.ERole;
import com.QuickGo.backend.repository.PrivilegeDetailRepository;
import com.QuickGo.backend.repository.RoleRepository;
import com.QuickGo.backend.repository.UserRepository;
import com.QuickGo.backend.repository.VehicleRepository;
import com.QuickGo.backend.security.jwt.JwtUtils;
import com.QuickGo.backend.security.services.UserDetailsImpl;
import com.google.gson.Gson;
import io.jsonwebtoken.io.Decoders;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.sql.Driver;
import java.util.*;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/quickGo/auth")
@RequiredArgsConstructor
public class AuthController {
    final AuthenticationManager authenticationManager;

    final UserRepository userRepository;

    final RoleRepository roleRepository;

    final PasswordEncoder encoder;

    final JwtUtils jwtUtils;

    final IdGenerationUtil idGenerationUtil;

    @Autowired
    final PrivilegeDetailRepository privilegeDetailRepository;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequestDTO loginRequest) {

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());

        // Fetch roles as Set<Role>
        Set<Role> rolesSet = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + userDetails.getUsername()))
                .getRoles();

        // Process each Role object
        List<String> privileges = new ArrayList<>();
        for (Role role : rolesSet) {
            privileges = privilegeDetailRepository.findPrivilegeIdsByRole(role)
                    .stream()
                    .map(Privilege::getPrivilegeName)
                    .collect(Collectors.toList());
        }
        String userCode = null;
        Optional<User> byId = userRepository.findById(userDetails.getId());
        if (byId.isPresent()) {
            userCode = byId.get().getUserCode();
        }
        return ResponseEntity.ok(new JwtResponseDTO(jwt, userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(), roles, privileges,userCode));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequestDTO signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity.badRequest().body(new ResponseMessage("Error: Username is already taken!"));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new ResponseMessage("Error: Email is already in use!"));
        }

        // Create new user's account
       User user = new User(signUpRequest.getUsername(), signUpRequest.getEmail(), encoder.encode(signUpRequest.getPassword()));
        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_PASSENGER).orElseThrow(() -> new RuntimeException("Error: ROLE_PASSENGER Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "ROLE_ADMIN" -> {
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN).orElseThrow(() -> new RuntimeException("Error:ROLE_ADMIN Role is not found."));
                        roles.add(adminRole);
                    }
                    case "ROLE_DRIVER" -> {
                        Role driverRole = roleRepository.findByName(ERole.ROLE_DRIVER)
                                .orElseThrow(() -> new RuntimeException("Error: ROLE_DRIVER Role is not found."));
                        roles.add(driverRole);
                    }
                    case "ROLE_TELEPHONE_OPERATOR" -> {
                        Role telephoneOperatorRole = roleRepository.findByName(ERole.ROLE_TELEPHONE_OPERATOR)
                                .orElseThrow(() -> new RuntimeException("Error: ROLE_TELEPHONE_OPERATOR Role is not found."));
                        roles.add(telephoneOperatorRole);
                    }
                    default -> {
                        Role userRole = roleRepository.findByName(ERole.ROLE_PASSENGER).orElseThrow(() -> new RuntimeException("Error: ROLE_PASSENGER Role is not found."));
                        roles.add(userRole);
                    }
                }
            });
        }

        user.setRoles(roles);
        user.setMobileNum(signUpRequest.getMobile_num());
        user.setName(signUpRequest.getName());
        user.setAddress(signUpRequest.getAddress());
        user.setUserCode(idGenerationUtil.userCodeGenerator());
        user.setIsActive(1);
        userRepository.save(user);
        return ResponseEntity.ok(new ResponseMessage("User registered successfully!"));
    }
}
