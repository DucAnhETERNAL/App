package com.example.readinglmao.model;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {
    @SerializedName("userDTO")
    private UserDTO userDTO;

    @SerializedName("isAdmin")
    private boolean isAdmin;

    public UserDTO getUserDTO() {
        return userDTO;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public static class UserDTO {
        private int id;
        private String userName;
        private String email;
        private String role;
        private String status;

        public int getId() {
            return id;
        }

        public String getUserName() {
            return userName;
        }

        public String getEmail() {
            return email;
        }

        public String getRole() {
            return role;
        }

        public String getStatus() {
            return status;
        }
    }
}
