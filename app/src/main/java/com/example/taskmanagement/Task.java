
package com.example.taskmanagement;

import com.google.firebase.Timestamp;
import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Locale;

public class Task {

    // Existing Firebase fields
    private String title;
    private String discription;
    private String statusId;
    private String periorityId;
    private String categoryId;
    private List<String> selecteduser;
    private List<String> selectedImges;
    private String id;
    private Timestamp times_tamp;

    // REST API fields
    private String description;
    private String category;
    private String estimated_hours;
    private String start_date;
    private String due_date;
    private String reference_code;
    private String progress_percentage;

    public String assignee_ids;

    // API priority object
    @SerializedName("priority")
    private TaskDetail priority;

    // API status object
    private TaskDetail status;

    // Separate Team model
    @SerializedName("team")
    private Team team;

    // API team ID used when creating/updating a task
    @SerializedName("team_id")
    private int team_id;

    public Task() {
        // Required by Gson and Firebase
    }

    // Existing constructor preserved
    public Task(
            String title,
            String discription,
            String statusId,
            String periorityId,
            String categoryId,
            String estimated_hours,
            String start_date,
            String due_date,
            String reference_code,
            String progress_percentage,
            List<String> selecteduser,
            List<String> selectedImges,
            String id,
            Timestamp times_tamp,
            String assignee_ids) {

        this.title = title;
        this.discription = discription;
        this.statusId = statusId;
        this.periorityId = periorityId;
        this.categoryId = categoryId;
        this.estimated_hours = estimated_hours;
        this.start_date = start_date;
        this.due_date = due_date;
        this.reference_code = reference_code;
        this.progress_percentage = progress_percentage;
        this.selecteduser = selecteduser;
        this.selectedImges = selectedImges;
        this.id = id;
        this.assignee_ids = assignee_ids;
        this.times_tamp = times_tamp;

        setPeriority(periorityId);
    }

    // Nested class for API priority and status objects
    public static class TaskDetail {

        private int id;
        private String value;
        private String label;
        private String name;

        public TaskDetail() {
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getLabel() {
            if (label != null && !label.isEmpty()) {
                return label;
            }

            if (value != null && !value.isEmpty()) {
                return value.substring(0, 1).toUpperCase(Locale.ROOT)
                        + value.substring(1).toLowerCase(Locale.ROOT);
            }

            return "";
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    // Title
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    // Firebase description
    public String getDiscription() {
        return discription;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }

    // API description with Firebase fallback
    public String getDescription() {
        return description != null ? description : discription;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // API priority object
    public TaskDetail getPriority() {
        return priority;
    }

    public void setPriority(TaskDetail priority) {
        this.priority = priority;

        if (priority != null && priority.getValue() != null) {
            this.periorityId = priority.getValue();
        }
    }

    // Priority value for existing code
    public String getPeriority() {
        if (priority != null && priority.getValue() != null) {
            return priority.getValue();
        }
        return periorityId;
    }

    public void setPeriority(String priorityValue) {
        this.periorityId = priorityValue;

        if (priorityValue == null || priorityValue.trim().isEmpty()) {
            this.priority = null;
            return;
        }

        String value = priorityValue.trim().toLowerCase(Locale.ROOT);

        TaskDetail detail = new TaskDetail();
        detail.setValue(value);
        detail.setLabel(
                value.substring(0, 1).toUpperCase(Locale.ROOT)
                        + value.substring(1)
        );

        this.priority = detail;
    }

    public String getPeriorityId() {
        return periorityId;
    }

    public void setPeriorityId(String periorityId) {
        this.periorityId = periorityId;
    }

    // Status object
    public TaskDetail getStatus() {
        return status;
    }

    public void setStatus(TaskDetail status) {
        this.status = status;
    }

    // Existing Firebase status ID
    public String getStatusId() {
        return statusId;
    }

    public void setStatusId(String statusId) {
        this.statusId = statusId;
    }

    // Category
    public String getCategory() {
        return category != null ? category : categoryId;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    // Estimated hours
    public String getEstimated_hours() {
        return estimated_hours;
    }

    public void setEstimated_hours(String estimated_hours) {
        this.estimated_hours = estimated_hours;
    }

    // Start date
    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    // Due date
    public String getDue_date() {
        return due_date;
    }

    public void setDue_date(String due_date) {
        this.due_date = due_date;
    }

    // Reference code
    public String getReference_code() {
        return reference_code;
    }

    public void setReference_code(String reference_code) {
        this.reference_code = reference_code;
    }

    // Progress percentage
    public String getProgress_percentage() {
        return progress_percentage;
    }

    public void setProgress_percentage(String progress_percentage) {
        this.progress_percentage = progress_percentage;
    }

    // Separate Team model
    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    // Team ID
    public int getTeam_id() {
        return team_id;
    }

    public void setTeam_id(int team_id) {
        this.team_id = team_id;
    }

    // Alternative camelCase methods
    public int getTeamId() {
        return team_id;
    }

    public void setTeamId(int teamId) {
        this.team_id = teamId;
    }

    public void setTeamName(String teamName) {
        if (this.team == null) {
            this.team = new Team();
        }
        this.team.setName(teamName);
    }

    // Task ID
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    // Firebase timestamp
    public Timestamp getTimes_tamp() {
        return times_tamp;
    }

    public void setTimes_tamp(Timestamp times_tamp) {
        this.times_tamp = times_tamp;
    }

    // Assigned users
    public List<String> getSelecteduser() {
        return selecteduser;
    }

    public void setSelecteduser(List<String> selecteduser) {
        this.selecteduser = selecteduser;
    }

    // Selected images
    public List<String> getSelectedImges() {
        return selectedImges;
    }

    public void setSelectedImges(List<String> selectedImges) {
        this.selectedImges = selectedImges;
    }

    // Assignee IDs
    public String getAssignee_ids() {
        return assignee_ids;
    }

    public void setAssignee_ids(String assignee_ids) {
        this.assignee_ids = assignee_ids;
    }
}
