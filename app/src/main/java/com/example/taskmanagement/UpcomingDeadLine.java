package com.example.taskmanagement;

public class UpcomingDeadLine {
    int id;
    String reference_code,title,status,priority,due_date,progress_percentage;

private Team team;


    public Team getTeam() {
        return team;
    }

    public UpcomingDeadLine() {
    }

    public UpcomingDeadLine(int id, String reference_code, String title, String status, String priority, String due_date, String progress_percentage) {
        this.id = id;
        this.reference_code = reference_code;
        this.title = title;
        this.status = status;
        this.priority = priority;
        this.due_date = due_date;
        this.progress_percentage = progress_percentage;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getDue_date() {
        return due_date;
    }

    public void setDue_date(String due_date) {
        this.due_date = due_date;
    }

    public String getProgress_percentage() {
        return progress_percentage;
    }

    public void setProgress_percentage(String progress_percentage) {
        this.progress_percentage = progress_percentage;
    }
}

