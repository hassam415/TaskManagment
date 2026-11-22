package com.example.taskmanagement;

import com.google.gson.annotations.SerializedName;

public class CheckList {
    int id,task_id;
    String title,completed_at;
    private boolean is_completed;
    public CheckList() {
    }
    @SerializedName("completed_by")
    private CheckListCompletBY completed_by;

    public CheckList(int id, int task_id, String title, String completed_at, CheckListCompletBY completed_by) {
        this.id = id;
        this.task_id = task_id;
        this.title = title;
        this.completed_at = completed_at;
        this.completed_by = completed_by;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCompleted_at() {
        return completed_at;
    }

    public void setCompleted_at(String completed_at) {
        this.completed_at = completed_at;
    }

    public CheckListCompletBY getCompleted_by() {
        return completed_by;
    }

    public void setCompleted_by(CheckListCompletBY completed_by) {
        this.completed_by = completed_by;
    }
    public boolean isIs_completed() {
        return is_completed;
    }

    public void setIs_completed(boolean is_completed) {
        this.is_completed = is_completed;
    }
}
