package com.example.taskmanagement.response;

import com.example.taskmanagement.CheckList;

public class CheckListResponse {

    private String message;
    private int progress_percentage;
    private CheckList data;

    public String getMessage() {
        return message;
    }

    public int getProgress_percentage() {
        return progress_percentage;
    }

    public CheckList getData() {
        return data;
    }
}