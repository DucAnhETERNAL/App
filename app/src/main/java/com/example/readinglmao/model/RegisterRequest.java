package com.example.readinglmao.model;

public class RegisterRequest {
    private String email;
    private String password;
    private String username;
    private String confirmpassword;

    public RegisterRequest(String email, String password, String username, String confirmpassword) {
        this.email = email;
        this.password = password;
        this.username = username;
        this.confirmpassword = confirmpassword;
    }
}

