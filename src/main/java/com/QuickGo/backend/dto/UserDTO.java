package com.QuickGo.backend.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class UserDTO {
    private Long id;
    private String name;
    private String user_code;
    private String address;
    private String email;
    private String mobile_num;
    private String username;
    private String password;
    private String role_id;
    private String role_name;
    private String is_active;

    public UserDTO(Long id, String name, String user_code, String address, String email, String mobile_num, String username, String password, String role_id, String role_name, String is_active) {
        this.id = id;
        this.name = name;
        this.user_code = user_code;
        this.address = address;
        this.email = email;
        this.mobile_num = mobile_num;
        this.username = username;
        this.password = password;
        this.role_id = role_id;
        this.role_name = role_name;
        this.is_active = is_active;
    }
}
