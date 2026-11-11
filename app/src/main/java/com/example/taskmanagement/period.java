package com.example.taskmanagement;

public class period {
    String start_date,end_date,preset;

    public period(String start_date, String end_date, String preset) {
        this.start_date = start_date;
        this.end_date = end_date;
        this.preset = preset;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public String getPreset() {
        return preset;
    }

    public void setPreset(String preset) {
        this.preset = preset;
    }
}
