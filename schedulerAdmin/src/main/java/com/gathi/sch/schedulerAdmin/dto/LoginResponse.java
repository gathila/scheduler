package com.gathi.sch.schedulerAdmin.dto;

public class LoginResponse {

    private String username;
    private String jwtToken;
    private String refreshToken;

    public LoginResponse(String username, String jwtToken) {
        this.username = username;
        this.jwtToken = jwtToken;
    }

    public LoginResponse(String username, String jwtToken, String refreshToken) {
        this.username = username;
        this.jwtToken = jwtToken;
        this.refreshToken = refreshToken;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getJwtToken() {
        return jwtToken;
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }
}
