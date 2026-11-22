package com.example.taskmanagement;

public class CheckListCompletBY {
    int id ;
    String name;

    public CheckListCompletBY() {
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

    public CheckListCompletBY(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
