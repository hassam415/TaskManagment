package com.example.taskmanagement;

public class UpdateProfileRequest {
    String name,job_title;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJob_title() {
        return job_title;
    }

    public void setJob_title(String job_title) {
        this.job_title = job_title;
    }

    public UpdateProfileRequest(String name, String job_title) {
        this.name = name;
        this.job_title = job_title;
    }

    public UpdateProfileRequest() {
    }
}
