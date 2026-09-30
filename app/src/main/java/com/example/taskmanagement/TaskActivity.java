package com.example.taskmanagement;

import com.google.firebase.Timestamp;

public class TaskActivity {
    String taskId,userId,Detail,status,detail,id;
    Timestamp time_stamp;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public TaskActivity() {
    }

    public TaskActivity(String taskId, String userId, String detail, String status, String detail1, Timestamp time_stamp, String id) {
        this.taskId = taskId;
        this.userId = userId;
        Detail = detail;
        this.status = status;
        this.detail = detail1;
        this.time_stamp = time_stamp;
        this.id = id;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDetail() {
        return Detail;
    }

    public void setDetail(String detail) {
        Detail = detail;
    }

    public Timestamp getTime_stamp() {
        return time_stamp;
    }

    public void setTime_stamp(Timestamp time_stamp) {
        this.time_stamp = time_stamp;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
