package com.secke.user_service.Model;


public class LoginRequest {
    private String name;
    private String password;
    // getters, setters

    public String getName() {
        return name;
    }

    public void setName(String username) {
        this.name = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
