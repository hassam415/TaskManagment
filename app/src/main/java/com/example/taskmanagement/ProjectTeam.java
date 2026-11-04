package com.example.taskmanagement;

public class ProjectTeam {
    int id;
    String name;

    public ProjectTeam(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public ProjectTeam() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
