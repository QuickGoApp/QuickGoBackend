package com.QuickGo.backend.service.impl;

import com.QuickGo.backend.Util.IdGenerationUtil;
import com.QuickGo.backend.Util.UtilService;
import com.QuickGo.backend.controllers.UserController;
import com.QuickGo.backend.dto.CoordinatesDTO;
import com.QuickGo.backend.dto.DriverCoordinateDto;
import com.QuickGo.backend.dto.GeoLocationDriverDTO;
import com.QuickGo.backend.dto.UserDTO;
import com.QuickGo.backend.dto.common.ResponseMessage;
import com.QuickGo.backend.exception.CustomException;
import com.QuickGo.backend.models.Role;
import com.QuickGo.backend.models.User;
import com.QuickGo.backend.models.enums.ERole;
import com.QuickGo.backend.repository.RoleRepository;
import com.QuickGo.backend.repository.UserRepository;
import com.QuickGo.backend.service.MailService;
import com.QuickGo.backend.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    IdGenerationUtil idGenerationUtil;
    @Autowired
    private MailService mailService;

    @Override
    public List<GeoLocationDriverDTO> findByUserCodes(List<DriverCoordinateDto> request) {
        List<String> userCodes = request.stream()
                .map(DriverCoordinateDto::getDriverId)
                .toList();
        return userRepository.findByUserCodeIn(userCodes)
                .stream()
                .filter(user -> user.getVehicle() != null)
                .map(x -> toGeoLocationDriverDTO(x, request))
                .toList();
    }

    @Override
    public List<UserDTO> findByUserRole(ERole eRole) {

        Role role = roleRepository.findByName(eRole)
                .orElseThrow(() -> new RuntimeException("Role not found"));

        return userRepository.findByRolesContains(role).stream()
                .map(x -> toUserDto(x, role))
                .toList();

    }

    @Override
    public List<UserDTO> findAllUsers() {
        return userRepository.findAll().stream()
                .map(this::toUserDto)
                .toList();
    }

    @Override
    public ResponseMessage update(UserDTO userData) {
        return userRepository.findById(userData.getId())
                .map(x -> {
                    x.setName(userData.getName());
                    x.setAddress(userData.getAddress());
                    x.setEmail(userData.getEmail());
                    x.setMobileNum(userData.getMobile_num());
                    x.setUsername(userData.getUsername());
                    if (!userData.getPassword().isEmpty()) {
                        x.setPassword(encoder.encode(userData.getPassword()));
                    }
                    userRepository.save(x);
                    return new ResponseMessage(HttpStatus.OK.value(), "User updated successfully", null);
                })
                .orElse(new ResponseMessage(HttpStatus.NOT_FOUND.value(), "User not found", null));
    }

    @Override
    public UserDTO findByCode(String userCode) {
        return userRepository.findByUserCode(userCode)
                .map(this::toUserDto)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public ResponseMessage otpSend(UserDTO userData) throws Exception {
        if (!userData.getEmail().isEmpty()) {
            Optional<User> byEmail = userRepository.findByEmail(userData.getEmail());
            if (byEmail.isPresent()) {
                UserDTO userDTO = toUserDto(byEmail.get());
                String otp = idGenerationUtil.otpGenerator();
                userDTO.setOtp(otp);
                mailService.sendEmailAsync(byEmail.get().getEmail(), "Password Reset Request – Your One-Time Password (OTP) Inside", generateForgotPasswordEmailBody(otp));
                return new ResponseMessage(HttpStatus.OK.value(), "success", userDTO);
            } else {
                throw new CustomException("Can't find a user");
            }
        }
        throw new CustomException("email is empty");
    }


    private UserDTO toUserDto(User user) {
        Set<Role> roles = user.getRoles();
        Role role = roles.stream().findFirst().orElseThrow();

        return new UserDTO(
                user.getId(),
                user.getName(),
                user.getUserCode(),
                user.getAddress(),
                user.getEmail(),
                user.getMobileNum(),
                user.getUsername(),
                user.getPassword(),
                user.getOverallRating(),
                role.getName().toString(),
                user.getIsActive() == 1 ? "Active" : "Inactive"
        );

    }


    private UserDTO toUserDto(User user, Role role) {
        return new UserDTO(
                user.getId(),
                user.getName(),
                user.getUserCode(),
                user.getAddress(),
                user.getEmail(),
                user.getMobileNum(),
                user.getUsername(),
                user.getPassword(),
                role.getName().toString(),
                role.getName().toString(),
                user.getIsActive() == 1 ? "Active" : "Inactive"
        );

    }

    private GeoLocationDriverDTO toGeoLocationDriverDTO(User driver, List<DriverCoordinateDto> request) {
        DriverCoordinateDto driverCoordinateDto = request.stream()
                .filter(x -> x.getDriverId().equals(driver.getUserCode()))
                .findFirst()
                .orElseThrow();
        return GeoLocationDriverDTO.builder()
                .coordinates(CoordinatesDTO.builder()
                        .lat(driverCoordinateDto.getLatitude())
                        .lng(driverCoordinateDto.getLongitude())
                        .build())
                .name(driver.getName())
                .type(driver.getVehicle().getType())
                .icon(driver.getVehicle().getIcon())
                .image(driver.getVehicle().getImage())
                .vehicleNumber(driver.getVehicle().getVehicleNumber())
                .color(driver.getVehicle().getColor())
                .rate(driver.getOverallRating())
                .seats(driver.getVehicle().getSeats())
                .isFavorite(true)
                .userCode(driver.getUserCode())
                .favoriteID(1)
                .build();
    }

    public String generateForgotPasswordEmailBody(String otp) {
        String emailTemplate = """
            <!DOCTYPE html>
            <html lang="en">
            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>Forgot Password Request</title>
                <style>
                    body {
                        font-family: Arial, sans-serif;
                        background-color: #f4f4f4;
                        margin: 0;
                        padding: 0;
                    }
                    .container {
                        background-color: #ffffff;
                        width: 80%%;
                        max-width: 600px;
                        margin: 20px auto;
                        padding: 20px;
                        border-radius: 10px;
                        box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
                    }
                    .header {
                        background-color: #007bff;
                        color: #ffffff;
                        text-align: center;
                        padding: 10px;
                        border-top-left-radius: 10px;
                        border-top-right-radius: 10px;
                    }
                    .header h1 {
                        margin: 0;
                        font-size: 24px;
                    }
                    .content {
                        margin: 20px 0;
                        line-height: 1.6;
                    }
                    .content p {
                        margin: 10px 0;
                    }
                    .otp {
                        font-weight: bold;
                        font-size: 18px;
                        color: #007bff;
                    }
                    .footer {
                        text-align: center;
                        color: #666666;
                        font-size: 14px;
                        padding-top: 10px;
                        border-top: 1px solid #dddddd;
                    }
                    .footer p {
                        margin: 5px 0;
                    }
                </style>
            </head>
            <body>
                <div class="container">
                    <div class="header">
                        <h1>Password Reset Request</h1>
                    </div>
                    <div class="content">
                        <p>Dear User,</p>
                        <p>You have requested a One-Time Password (OTP) to reset your password.</p>
                        <p>Your OTP is:</p>
                        <p class="otp">%s</p>
                        <p>Please use this OTP to reset your password. For security reasons, the OTP will expire in 10 minutes.</p>
                        <p>If you did not request a password reset, please contact our support team immediately.</p>
                    </div>
                    <div class="footer">
                        <p>&copy; 2024 Your Company Name. All rights reserved.</p>
                    </div>
                </div>
            </body>
            </html>
            """;

        return emailTemplate.formatted(otp);
    }



}
