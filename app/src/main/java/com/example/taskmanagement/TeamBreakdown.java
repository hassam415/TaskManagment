package com.example.taskmanagement;

public class TeamBreakdown {
    int id;
    String name;
    double total_created,completed,active,overdue,on_time_percentage,actual_hours,estimated_hours;

    public TeamBreakdown(int id, String name, double total_created, double completed, double active, double overdue, double on_time_percentage, double actual_hours, double estimated_hours) {
        this.id = id;
        this.name = name;
        this.total_created = total_created;
        this.completed = completed;
        this.active = active;
        this.overdue = overdue;
        this.on_time_percentage = on_time_percentage;
        this.actual_hours = actual_hours;
        this.estimated_hours = estimated_hours;
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

    public double getTotal_created() {
        return total_created;
    }

    public void setTotal_created(double total_created) {
        this.total_created = total_created;
    }

    public double getCompleted() {
        return completed;
    }

    public void setCompleted(double completed) {
        this.completed = completed;
    }

    public double getActive() {
        return active;
    }

    public void setActive(double active) {
        this.active = active;
    }

    public double getOverdue() {
        return overdue;
    }

    public void setOverdue(double overdue) {
        this.overdue = overdue;
    }

    public double getOn_time_percentage() {
        return on_time_percentage;
    }

    public void setOn_time_percentage(double on_time_percentage) {
        this.on_time_percentage = on_time_percentage;
    }

    public double getActual_hours() {
        return actual_hours;
    }

    public void setActual_hours(double actual_hours) {
        this.actual_hours = actual_hours;
    }

    public double getEstimated_hours() {
        return estimated_hours;
    }

    public void setEstimated_hours(double estimated_hours) {
        this.estimated_hours = estimated_hours;
    }
}
