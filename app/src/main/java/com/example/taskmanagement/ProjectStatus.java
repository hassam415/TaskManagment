package com.example.taskmanagement;

public class ProjectStatus {
 String   value, label,color;

    public ProjectStatus(String value, String label, String color) {
        this.value = value;
        this.label = label;
        this.color = color;
    }

    public ProjectStatus() {
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
