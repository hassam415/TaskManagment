package com.example.taskmanagement;

import java.util.List;

public class Comment {
    String comment;
    String id,username;
List<String>replies;
List<String> userReplies;

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Comment() {
    }

    public Comment(String comment, String id, String username, List<String> replies, List<String> userReplies) {
        this.comment = comment;
        this.id = id;
        this.username = username;
        this.replies = replies;
        this.userReplies = userReplies;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }


}
