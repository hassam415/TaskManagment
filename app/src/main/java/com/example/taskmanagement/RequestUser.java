package com.example.taskmanagement;

import java.util.List;

public class RequestUser {
    String name,email,password,job_title,status;
    int role_id;
    List<Integer> team_ids;

    public RequestUser(String name, String email, String password, String job_title, String status, int role_id, List<Integer> team_ids) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.job_title = job_title;
        this.status = status;
        this.role_id = role_id;
        this.team_ids = team_ids;
    }

    public RequestUser() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getJob_title() {
        return job_title;
    }

    public void setJob_title(String job_title) {
        this.job_title = job_title;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getRole_id() {
        return role_id;
    }

    public void setRole_id(int role_id) {
        this.role_id = role_id;
    }

    public List<Integer> getTeam_ids() {
        return team_ids;
    }

    public void setTeam_ids(List<Integer> team_ids) {
        this.team_ids = team_ids;
    }
}
