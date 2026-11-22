package com.example.taskmanagement;

public class CheckListToggleResponse {
    private String message;
    private int task_progress;
    private CheckList data;

    public CheckListToggleResponse(String message, int task_progress, CheckList data) {
        this.message = message;
        this.task_progress = task_progress;
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getTask_progress() {
        return task_progress;
    }

    public void setTask_progress(int task_progress) {
        this.task_progress = task_progress;
    }

    public CheckList getData() {
        return data;
    }

    public void setData(CheckList data) {
        this.data = data;
    }
}
