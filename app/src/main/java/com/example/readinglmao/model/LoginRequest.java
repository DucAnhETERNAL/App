package com.example.readinglmao.model;

public class LoginRequest {
    private String userNameOrEmail;
    private String password;

    public LoginRequest(String userNameOrEmail, String password) {
        this.userNameOrEmail = userNameOrEmail;
        this.password = password;
    }
}

