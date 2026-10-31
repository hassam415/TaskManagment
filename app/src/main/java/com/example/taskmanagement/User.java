package com.example.taskmanagement;

import java.util.List;

public class User {
    String name ,password,email,address,theme,timezone,status ;
    int id,role_id;
String job_title,role;
List<Integer>team_ids;


    public int getId() {
        return id;
    }

    public User(String name, String email, String password, String address, String theme, String timezone, String status, int id, int role_id, String job_title, String role, List<Integer> team_ids) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.address = address;
        this.theme = theme;
        this.timezone = timezone;
        this.status = status;
        this.id = id;
        this.role_id = role_id;
        this.job_title = job_title;
        this.role = role;
        this.team_ids = team_ids;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
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

    public void setId(int id) {
        this.id = id;
    }

    public User() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    public String getJob_title() {
        return job_title;
    }

    public void setJob_title(String job_title) {
        this.job_title = job_title;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
