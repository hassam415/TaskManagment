package com.example.taskmanagement.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class APIResponse<T>{

    List<T> data;

    public List<T> getData() {
        return data;
    }
}
