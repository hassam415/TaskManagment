package com.example.taskmanagement;

public class Team {
    int id;
    String name;


    public void setId(int id) {
        this.id = id;
    }

    public Team(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Team() {
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    @Override
    public String toString() {
        return name;
    }
}
