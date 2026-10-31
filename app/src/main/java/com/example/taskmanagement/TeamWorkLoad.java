package com.example.taskmanagement;

public class TeamWorkLoad {
    int id,active_tasks_count,completed_tasks_count,overdue_tasks_count;
    double estimated_hours;
    String name,email,job_title,capacity_status;

    public TeamWorkLoad(int id, int active_tasks_count, int completed_tasks_count, int overdue_tasks_count, double estimated_hours, String name, String email, String job_title, String capacity_status) {
        this.id = id;
        this.active_tasks_count = active_tasks_count;
        this.completed_tasks_count = completed_tasks_count;
        this.overdue_tasks_count = overdue_tasks_count;
        this.estimated_hours = estimated_hours;
        this.name = name;
        this.email = email;
        this.job_title = job_title;
        this.capacity_status = capacity_status;
    }

    public TeamWorkLoad() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getActive_tasks_count() {
        return active_tasks_count;
    }

    public void setActive_tasks_count(int active_tasks_count) {
        this.active_tasks_count = active_tasks_count;
    }

    public double getEstimated_hours() {
        return estimated_hours;
    }

    public void setEstimated_hours(double estimated_hours) {
        this.estimated_hours = estimated_hours;
    }

    public int getCompleted_tasks_count() {
        return completed_tasks_count;
    }

    public void setCompleted_tasks_count(int completed_tasks_count) {
        this.completed_tasks_count = completed_tasks_count;
    }

    public int getOverdue_tasks_count() {
        return overdue_tasks_count;
    }

    public void setOverdue_tasks_count(int overdue_tasks_count) {
        this.overdue_tasks_count = overdue_tasks_count;
    }



    public void setEstimated_hours(int estimated_hours) {
        this.estimated_hours = estimated_hours;
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

    public String getCapacity_status() {
        return capacity_status;
    }

    public void setCapacity_status(String capacity_status) {
        this.capacity_status = capacity_status;
    }
}

