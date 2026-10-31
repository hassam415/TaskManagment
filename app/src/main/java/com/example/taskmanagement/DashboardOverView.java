package com.example.taskmanagement;

import com.google.gson.annotations.SerializedName;

public class DashboardOverView {
    @SerializedName("total_tasks")
    public String totaltask;
    @SerializedName( "completed")
    public String complete;
    @SerializedName("in_progress")
    public String inprogress;
    @SerializedName("in_review")
    public String inreview;
    @SerializedName("on_hold")
    public String onHold;
    @SerializedName("overdue")
    public String overDue;
    @SerializedName("completion_rate")
    public String completion_rate;
    @SerializedName("total_estimated_hours")
    public String totalhours;
    @SerializedName("total_actual_hours")
    public String totalactualhours;

    public String getTotaltask() {
        return totaltask;
    }

    public void setTotaltask(String totaltask) {
        this.totaltask = totaltask;
    }

    public String getComplete() {
        return complete;
    }

    public void setComplete(String complete) {
        this.complete = complete;
    }

    public String getInprogress() {
        return inprogress;
    }

    public void setInprogress(String inprogress) {
        this.inprogress = inprogress;
    }

    public String getInreview() {
        return inreview;
    }

    public void setInreview(String inreview) {
        this.inreview = inreview;
    }

    public String getOnHold() {
        return onHold;
    }

    public void setOnHold(String onHold) {
        this.onHold = onHold;
    }

    public String getOverDue() {
        return overDue;
    }

    public void setOverDue(String overDue) {
        this.overDue = overDue;
    }

    public String getCompletion_rate() {
        return completion_rate;
    }

    public void setCompletion_rate(String completion_rate) {
        this.completion_rate = completion_rate;
    }

    public String getTotalhours() {
        return totalhours;
    }

    public void setTotalhours(String totalhours) {
        this.totalhours = totalhours;
    }

    public String getTotalactualhours() {
        return totalactualhours;
    }

    public void setTotalactualhours(String totalactualhours) {
        this.totalactualhours = totalactualhours;
    }
}
