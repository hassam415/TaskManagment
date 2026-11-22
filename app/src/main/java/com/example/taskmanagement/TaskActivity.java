package com.example.taskmanagement;

import com.google.firebase.Timestamp;

public class TaskActivity {
    String description,action,formatted_created_at;
private User user;
private Properties properties;

    public String getFormatted_created_at() {
        return formatted_created_at;
    }

    public void setFormatted_created_at(String formatted_created_at) {
        this.formatted_created_at = formatted_created_at;
    }

    int id,task_id;


    public TaskActivity() {
    }

    public TaskActivity(String description, String action, User user, Properties properties, int id, int task_id) {
        this.description = description;
        this.action = action;
        this.user = user;
        this.properties = properties;
        this.id = id;
        this.task_id = task_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTask_id() {
        return task_id;
    }

    public void setTask_id(int task_id) {
        this.task_id = task_id;
    }
}
