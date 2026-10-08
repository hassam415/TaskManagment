package com.example.taskmanagement;

public class Periority {
    String id,name;

    public Periority(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public Periority() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
