package com.hibuddy.hibuddy_backend.dto.auth;

public class LoginResponseDTO {
    private String token;

    public LoginResponseDTO(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "LoginResponseDTO{" +
                "token='" + token + '\'' +
                '}';
    }
}