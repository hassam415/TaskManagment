package com.example.taskmanagement;

import com.google.gson.annotations.SerializedName;

public class StatusDistribution {
    @SerializedName("draft")
    private StatusItem draft;
    @SerializedName("assigned")
    private StatusItem assigned;

    @SerializedName("in_progress")
    private StatusItem inProgress;

    @SerializedName("on_hold")
    private StatusItem onHold;

    @SerializedName("in_review")
    private StatusItem inReview;

    @SerializedName("changes_requested")
    private StatusItem changesRequested;

    @SerializedName("completed")
    private StatusItem completed;

    @SerializedName("cancelled")
    private StatusItem cancelled;

    public StatusItem getDraft() {
        return draft;
    }

    public void setDraft(StatusItem draft) {
        this.draft = draft;
    }

    public StatusItem getAssigned() {
        return assigned;
    }

    public void setAssigned(StatusItem assigned) {
        this.assigned = assigned;
    }

    public StatusItem getInProgress() {
        return inProgress;
    }

    public void setInProgress(StatusItem inProgress) {
        this.inProgress = inProgress;
    }

    public StatusItem getOnHold() {
        return onHold;
    }

    public void setOnHold(StatusItem onHold) {
        this.onHold = onHold;
    }

    public StatusItem getInReview() {
        return inReview;
    }

    public void setInReview(StatusItem inReview) {
        this.inReview = inReview;
    }

    public StatusItem getChangesRequested() {
        return changesRequested;
    }

    public void setChangesRequested(StatusItem changesRequested) {
        this.changesRequested = changesRequested;
    }

    public StatusItem getCompleted() {
        return completed;
    }

    public void setCompleted(StatusItem completed) {
        this.completed = completed;
    }

    public StatusItem getCancelled() {
        return cancelled;
    }

    public void setCancelled(StatusItem cancelled) {
        this.cancelled = cancelled;
    }
}
