package com.example.taskmanagement;

import java.util.List;

public class CreatTaskReq {
    String title,description,category,priority,estimated_hours,start_date,due_date;
    List<Integer>assignee_ids;

    public CreatTaskReq(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getEstimated_hours() {
        return estimated_hours;
    }

    public void setEstimated_hours(String estimated_hours) {
        this.estimated_hours = estimated_hours;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getDue_date() {
        return due_date;
    }

    public void setDue_date(String due_date) {
        this.due_date = due_date;
    }

    public List<Integer> getAssignee_ids() {
        return assignee_ids;
    }

    public void setAssignee_ids(List<Integer> assignee_ids) {
        this.assignee_ids = assignee_ids;
    }

    public CreatTaskReq() {
    }
}
