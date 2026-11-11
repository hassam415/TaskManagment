package com.example.taskmanagement;

public class MemberBreakdown {
    int id;
String name,email,job_title;

double assigned_count,completed_count,active_count,overdue_count,on_time_rate,logged_hours;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getJob_title() {
        return job_title;
    }

    public void setJob_title(String job_title) {
        this.job_title = job_title;
    }

    public double getAssigned_count() {
        return assigned_count;
    }

    public void setAssigned_count(double assigned_count) {
        this.assigned_count = assigned_count;
    }

    public double getCompleted_count() {
        return completed_count;
    }

    public void setCompleted_count(double completed_count) {
        this.completed_count = completed_count;
    }

    public double getActive_count() {
        return active_count;
    }

    public void setActive_count(double active_count) {
        this.active_count = active_count;
    }

    public double getOverdue_count() {
        return overdue_count;
    }

    public void setOverdue_count(double overdue_count) {
        this.overdue_count = overdue_count;
    }

    public double getOn_time_rate() {
        return on_time_rate;
    }

    public void setOn_time_rate(double on_time_rate) {
        this.on_time_rate = on_time_rate;
    }

    public double getLogged_hours() {
        return logged_hours;
    }

    public void setLogged_hours(double logged_hours) {
        this.logged_hours = logged_hours;
    }

    public MemberBreakdown(int id, String name, String email, String job_title, double assigned_count, double completed_count, double active_count, double overdue_count, double on_time_rate, double logged_hours) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.job_title = job_title;
        this.assigned_count = assigned_count;
        this.completed_count = completed_count;
        this.active_count = active_count;
        this.overdue_count = overdue_count;
        this.on_time_rate = on_time_rate;
        this.logged_hours = logged_hours;
    }
}

