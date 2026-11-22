package com.example.taskmanagement;

import java.util.List;

public class Comment {
    String comment;
    int task_id,id;
    String username,created_at;
    private User user;
List<String>replies;
List<String> userReplies;

    public Comment() {
    }

    public Comment(String comment, int task_id, int id, String username, String created_at, User user, List<String> replies, List<String> userReplies) {
        this.comment = comment;
        this.task_id = task_id;
        this.id = id;
        this.username = username;
        this.created_at = created_at;
        this.user = user;
        this.replies = replies;
        this.userReplies = userReplies;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getTask_id() {
        return task_id;
    }

    public void setTask_id(int task_id) {
        this.task_id = task_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<String> getReplies() {
        return replies;
    }

    public void setReplies(List<String> replies) {
        this.replies = replies;
    }

    public List<String> getUserReplies() {
        return userReplies;
    }

    public void setUserReplies(List<String> userReplies) {
        this.userReplies = userReplies;
    }
}
