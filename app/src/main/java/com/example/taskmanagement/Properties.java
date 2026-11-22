package com.example.taskmanagement;

public class Properties {
   String old_status,new_status,reason;

    public Properties() {
    }

    public Properties(String old_status, String new_status, String reason) {
        this.old_status = old_status;
        this.new_status = new_status;
        this.reason = reason;
    }

    public String getOld_status() {
        return old_status;
    }

    public void setOld_status(String old_status) {
        this.old_status = old_status;
    }

    public String getNew_status() {
        return new_status;
    }

    public void setNew_status(String new_status) {
        this.new_status = new_status;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}

