package com.example.taskmanagement;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DashboardData {
    @SerializedName("overview")
    public DashboardOverView dashboardOverView;
@SerializedName("status_distribution")
public StatusDistribution statusDistribution;
@SerializedName("priority_distribution")
public Priority_Distribution priorityDistribution;
@SerializedName("upcoming_deadlines")
public List<UpcomingDeadLine> upcomingDeadLine;
@SerializedName("team_workload")
public List<TeamWorkLoad>teamWorkLoad;
@SerializedName("pending_reviews")
public List<Review>reviews;

    public List<Review> getReviews() {
        return reviews;
    }

    public List<TeamWorkLoad> getTeamWorkLoad() {
        return teamWorkLoad;
    }

    public List<UpcomingDeadLine> getUpcomingDeadLine() {
        return upcomingDeadLine;
    }

    public StatusDistribution getStatusDistribution() {
        return statusDistribution;
    }

    public Priority_Distribution getPriorityDistribution() {
        return priorityDistribution;
    }

    public DashboardOverView getDashboardOverView() {
        return dashboardOverView;
    }
}
