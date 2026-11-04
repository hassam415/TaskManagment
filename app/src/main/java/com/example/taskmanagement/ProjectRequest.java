package com.example.taskmanagement;

import java.util.List;

public class ProjectRequest {
    String name,key,description,start_date,target_end_date,status;
    int lead_user_id,team_id;
    List<Integer> member_ids;
    double tasks_count,completed_tasks_count,progress_percentage;
 private  ProjectTeam team;
// private ProjectStatus status;
 private Projectlead lead;

    public double getTasks_count() {
        return tasks_count;
    }

    public void setTasks_count(double tasks_count) {
        this.tasks_count = tasks_count;
    }

    public double getCompleted_tasks_count() {
        return completed_tasks_count;
    }

    public void setCompleted_tasks_count(double completed_tasks_count) {
        this.completed_tasks_count = completed_tasks_count;
    }

    public double getProgress_percentage() {
        return progress_percentage;
    }

    public void setProgress_percentage(double progress_percentage) {
        this.progress_percentage = progress_percentage;
    }

    public Projectlead getLead() {
        return lead;
    }

    public void setLead(Projectlead lead) {
        this.lead = lead;
    }

    public ProjectTeam getTeam() {
        return team;
    }

    public void setTeam(ProjectTeam team) {
        this.team = team;
    }

    public ProjectRequest(String name, String key, String description, String start_date, String target_end_date, int lead_user_id, int team_id, List<Integer> member_ids, double tasks_count, double completed_tasks_count, double progress_percentage, ProjectTeam team, String status, Projectlead lead) {
        this.name = name;
        this.key = key;
        this.description = description;
        this.start_date = start_date;
        this.target_end_date = target_end_date;
        this.lead_user_id = lead_user_id;
        this.team_id = team_id;
        this.member_ids = member_ids;
        this.tasks_count = tasks_count;
        this.completed_tasks_count = completed_tasks_count;
        this.progress_percentage = progress_percentage;
        this.team = team;
        this.status = status;
        this.lead = lead;
    }

    public ProjectRequest() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getTarget_end_date() {
        return target_end_date;
    }

    public void setTarget_end_date(String target_end_date) {
        this.target_end_date = target_end_date;
    }

    public int getLead_user_id() {
        return lead_user_id;
    }

    public void setLead_user_id(int lead_user_id) {
        this.lead_user_id = lead_user_id;
    }

    public int getTeam_id() {
        return team_id;
    }

    public void setTeam_id(int team_id) {
        this.team_id = team_id;
    }

    public List<Integer> getMember_ids() {
        return member_ids;
    }

    public void setMember_ids(List<Integer> member_ids) {
        this.member_ids = member_ids;
    }
}
