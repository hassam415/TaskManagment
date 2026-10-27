package com.example.taskmanagement;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UpdateTask {
    @SerializedName("title")
    private String title;
    @SerializedName("description")
    private String discription;
    @SerializedName("category")
    private String category;
    @SerializedName("priority")
    private String priority;
    @SerializedName("team_id")
    private int team_id;
    @SerializedName("estimated_hours")
   private String estimated_hours;
    @SerializedName("start_date")
    private String start_date;
    @SerializedName("due_date")
private String due_date;
    @SerializedName("reviewer_id")
    private int reviewer_id;
@SerializedName("assignee_ids")
    private List<Integer> assignee_ids;

    public UpdateTask(String title, String discription, String category, String priority, int team_id, String estimated_hours, String start_date, String due_date, int reviewer_id, List<Integer> assignee_ids) {
        this.title = title;
        this.discription = discription;
        this.category = category;
        this.priority = priority;
        this.team_id = team_id;
        this.estimated_hours = estimated_hours;
        this.start_date = start_date;
        this.due_date = due_date;
        this.reviewer_id = reviewer_id;
        this.assignee_ids = assignee_ids;
    }
}
