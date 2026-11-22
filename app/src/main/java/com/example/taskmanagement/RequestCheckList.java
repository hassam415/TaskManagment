package com.example.taskmanagement;

public class RequestCheckList {
    String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public RequestCheckList(String title) {
        this.title = title;
    }

    public RequestCheckList() {
    }
}
