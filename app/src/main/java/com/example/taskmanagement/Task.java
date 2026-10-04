package com.example.taskmanagement;

import android.net.Uri;


import com.google.firebase.Timestamp;

import java.util.List;

public class Task {
    String title,discription,status;
    List<String>selecteduser;
  List<String>selectedImges;
  String uid;
  Timestamp times_tamp;

    public Timestamp getTimes_tamp() {
        return times_tamp;
    }

    public void setTimes_tamp(Timestamp times_tamp) {
        this.times_tamp = times_tamp;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Task(String title, String discription, String status, List<String> selecteduser, List<String> selectedImges, String uid, Timestamp times_tamp) {
        this.title = title;
        this.discription = discription;
        this.status = status;
        this.selecteduser = selecteduser;
        this.selectedImges = selectedImges;
        this.uid = uid;
        this.times_tamp = times_tamp;
    }

    public List<String> getSelectedImges() {
        return selectedImges;
    }

    public void setSelectedImges(List<String> selectedImges) {
        this.selectedImges = selectedImges;
    }

    public List<String> getSelecteduser() {
        return selecteduser;
    }

    public void setSelecteduser(List<String> selecteduser) {
        this.selecteduser = selecteduser;
    }

    public Task() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDiscription() {
        return discription;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }

}
