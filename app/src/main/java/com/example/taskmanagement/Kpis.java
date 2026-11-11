package com.example.taskmanagement;

public class Kpis {
    double total_created,total_completed,total_overdue,on_time_rate,avg_cycle_time_days,total_estimated_hours,total_actual_hours,total_active;

    public double getTotal_active() {
        return total_active;
    }

    public void setTotal_active(double total_active) {
        this.total_active = total_active;
    }

    public Kpis() {
    }

    public Kpis(double total_created, double total_completed, double total_overdue, double on_time_rate, double avg_cycle_time_days, double total_estimated_hours, double total_actual_hours) {
        this.total_created = total_created;
        this.total_completed = total_completed;
        this.total_overdue = total_overdue;
        this.on_time_rate = on_time_rate;
        this.avg_cycle_time_days = avg_cycle_time_days;
        this.total_estimated_hours = total_estimated_hours;
        this.total_actual_hours = total_actual_hours;
    }

    public double getTotal_created() {
        return total_created;
    }

    public void setTotal_created(double total_created) {
        this.total_created = total_created;
    }

    public double getTotal_completed() {
        return total_completed;
    }

    public void setTotal_completed(double total_completed) {
        this.total_completed = total_completed;
    }

    public double getTotal_overdue() {
        return total_overdue;
    }

    public void setTotal_overdue(double total_overdue) {
        this.total_overdue = total_overdue;
    }

    public double getOn_time_rate() {
        return on_time_rate;
    }

    public void setOn_time_rate(double on_time_rate) {
        this.on_time_rate = on_time_rate;
    }

    public double getAvg_cycle_time_days() {
        return avg_cycle_time_days;
    }

    public void setAvg_cycle_time_days(double avg_cycle_time_days) {
        this.avg_cycle_time_days = avg_cycle_time_days;
    }

    public double getTotal_estimated_hours() {
        return total_estimated_hours;
    }

    public void setTotal_estimated_hours(double total_estimated_hours) {
        this.total_estimated_hours = total_estimated_hours;
    }

    public double getTotal_actual_hours() {
        return total_actual_hours;
    }

    public void setTotal_actual_hours(double total_actual_hours) {
        this.total_actual_hours = total_actual_hours;
    }
}
