package com.example.taskmanagement;

public class DaySummary {
    String date,day_of_week;
    double total_count,completed_count,pending_count;

    public DaySummary() {
    }

    public DaySummary(String date, String day_of_week, double total_count, double completed_count, double pending_count) {
        this.date = date;
        this.day_of_week = day_of_week;
        this.total_count = total_count;
        this.completed_count = completed_count;
        this.pending_count = pending_count;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDay_of_week() {
        return day_of_week;
    }

    public void setDay_of_week(String day_of_week) {
        this.day_of_week = day_of_week;
    }

    public double getTotal_count() {
        return total_count;
    }

    public void setTotal_count(double total_count) {
        this.total_count = total_count;
    }

    public double getCompleted_count() {
        return completed_count;
    }

    public void setCompleted_count(double completed_count) {
        this.completed_count = completed_count;
    }

    public double getPending_count() {
        return pending_count;
    }

    public void setPending_count(double pending_count) {
        this.pending_count = pending_count;
    }
}
