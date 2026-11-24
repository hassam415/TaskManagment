package com.example.taskmanagement.response;

import com.example.taskmanagement.Attachement;

public class AttachmentResponse {
    String message;
    private Attachement data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Attachement getData() {
        return data;
    }

    public void setData(Attachement data) {
        this.data = data;
    }
}
