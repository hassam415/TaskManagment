package com.example.taskmanagement;

public class User {
    String name ,password,email,address , id;
String job_title,role;


    public String getId() {
        return id;
    }

    public User(String name, String password, String email, String address, String id, String role, String job_title) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.address = address;
        this.id = id;
        this.role = role;
        this.job_title = job_title;
    }

    public void setId(String id) {
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
