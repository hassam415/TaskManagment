package com.example.taskmanagement;

import java.util.List;

public class ProjectListResponse {
    List<Project> data;

    public List<Project> getData() {
        return data;
    }

    public void setData(List<Project> data) {
        this.data = data;
    }
}
