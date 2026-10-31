package com.example.taskmanagement;

import com.google.gson.annotations.SerializedName;

public class ResponseDashBoard {
    @SerializedName("data")
    private DashboardData data;

    public DashboardData getData() {
        return data;
    }

    public void setData(DashboardData data) {
        this.data = data;
    }


}
