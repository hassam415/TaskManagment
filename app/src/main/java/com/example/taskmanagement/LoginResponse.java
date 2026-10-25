package com.example.taskmanagement;

public class LoginResponse {
    String name,access_token,token_type;
    private  User user;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getToken_type() {
        return token_type;
    }

    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LoginResponse(String name, String access_token, String token_type, User user) {
        this.name = name;
        this.access_token = access_token;
        this.token_type = token_type;
        this.user = user;
    }

    public LoginResponse() {
    }
}
