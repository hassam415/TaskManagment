package com.example.taskmanagement;

import com.google.gson.annotations.SerializedName;

public class Priority_Distribution {
    @SerializedName("low")
    public Priority_item low;

    @SerializedName("normal")
    public Priority_item normal;

    @SerializedName("high")
    public Priority_item high;
    @SerializedName("urgent")
    public Priority_item urgent;

    public Priority_item getLow() {
        return low;
    }

    public void setLow(Priority_item low) {
        this.low = low;
    }

    public Priority_item getNormal() {
        return normal;
    }

    public void setNormal(Priority_item normal) {
        this.normal = normal;
    }

    public Priority_item getHigh() {
        return high;
    }

    public void setHigh(Priority_item high) {
        this.high = high;
    }

    public Priority_item getUrgent() {
        return urgent;
    }

    public void setUrgent(Priority_item urgent) {
        this.urgent = urgent;
    }
}
