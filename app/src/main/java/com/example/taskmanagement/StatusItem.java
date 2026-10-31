package com.example.taskmanagement;

import com.google.gson.annotations.SerializedName;

public class StatusItem {
    @SerializedName("label")
    public String label;
    @SerializedName("color")
    public String colour;
    @SerializedName("count")
    public String count;
}
