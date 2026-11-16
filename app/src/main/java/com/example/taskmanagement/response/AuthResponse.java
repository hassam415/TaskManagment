package com.example.taskmanagement.response;

import com.example.taskmanagement.User;

public class AuthResponse {
    private User data;

    public User getData() {
        return data;
    }

    public void setData(User data) {
        this.data = data;
    }
}
