package com.example.taskmanagement;

public class LoginRequest {

    private String email;
    private String password;
    private String device_name;

    public LoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
        this.device_name = "mobile-app";
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getDevice_name() {
        return device_name;
    }
}