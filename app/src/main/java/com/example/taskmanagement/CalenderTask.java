package com.example.taskmanagement;

import java.util.List;

public class CalenderTask {
    String date,formatted_date,day_of_week;
    int total_tasks;
    List<Task> data;

    public CalenderTask(String date, String formatted_date, String day_of_week, int total_tasks, List<Task> data) {
        this.date = date;
        this.formatted_date = formatted_date;
        this.day_of_week = day_of_week;
        this.total_tasks = total_tasks;
        this.data = data;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getFormatted_date() {
        return formatted_date;
    }

    public void setFormatted_date(String formatted_date) {
        this.formatted_date = formatted_date;
    }

    public String getDay_of_week() {
        return day_of_week;
    }

    public void setDay_of_week(String day_of_week) {
        this.day_of_week = day_of_week;
    }

    public int getTotal_tasks() {
        return total_tasks;
    }

    public void setTotal_tasks(int total_tasks) {
        this.total_tasks = total_tasks;
    }

    public List<Task> getData() {
        return data;
    }

    public void setData(List<Task> data) {
        this.data = data;
    }
}
