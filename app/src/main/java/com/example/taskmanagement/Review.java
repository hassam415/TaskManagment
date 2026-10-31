package com.example.taskmanagement;

public class Review {
    int id;
    String reference_code,title;

    public Review(int id, String reference_code, String title) {
        this.id = id;
        this.reference_code = reference_code;
        this.title = title;
    }

    public Review() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getReference_code() {
        return reference_code;
    }

    public void setReference_code(String reference_code) {
        this.reference_code = reference_code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
