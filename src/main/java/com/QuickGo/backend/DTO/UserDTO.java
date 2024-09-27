package com.QuickGo.backend.DTO;

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
    private String is_active;


    public UserDTO() {
    }

    public UserDTO(Long id, String name, String user_code, String address, String email, String mobile_num, String username, String password, String role_id, String is_active) {
        this.id = id;
        this.name = name;
        this.user_code = user_code;
        this.address = address;
        this.email = email;
        this.mobile_num = mobile_num;
        this.username = username;
        this.password = password;
        this.role_id = role_id;
        this.is_active = is_active;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUser_code() {
        return user_code;
    }

    public void setUser_code(String user_code) {
        this.user_code = user_code;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile_num() {
        return mobile_num;
    }

    public void setMobile_num(String mobile_num) {
        this.mobile_num = mobile_num;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole_id() {
        return role_id;
    }

    public void setRole_id(String role_id) {
        this.role_id = role_id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIs_active() {
        return is_active;
    }

    public void setIs_active(String is_active) {
        this.is_active = is_active;
    }
}
