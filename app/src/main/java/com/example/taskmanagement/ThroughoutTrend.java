package com.example.taskmanagement;

public class ThroughoutTrend {
    String label;
    double created,completed;

    public ThroughoutTrend() {
    }

    public ThroughoutTrend(String label, double created, double completed) {
        this.label = label;
        this.created = created;
        this.completed = completed;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public double getCreated() {
        return created;
    }

    public void setCreated(double created) {
        this.created = created;
    }

    public double getCompleted() {
        return completed;
    }

    public void setCompleted(double completed) {
        this.completed = completed;
    }
}