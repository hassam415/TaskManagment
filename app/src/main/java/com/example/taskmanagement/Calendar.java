
package com.example.taskmanagement;

import com.google.gson.annotations.SerializedName;
import java.util.Map;

public class Calendar {

    String month;
    String month_label;
    String date_type;

    int completed_tasks;
    int total_tasks;
    int pending_tasks;

    Map<String, DaySummary> days;

    public Calendar() {
    }

    public String getMonth() {
        return month;
    }

    public String getMonth_label() {
        return month_label;
    }

    public String getDate_type() {
        return date_type;
    }

    public int getCompleted_tasks() {
        return completed_tasks;
    }

    public int getTotal_tasks() {
        return total_tasks;
    }

    public int getPending_tasks() {
        return pending_tasks;
    }

    public Map<String, DaySummary> getDays() {
        return days;
    }

    public static class DaySummary {

        String date;
        String day_of_week;

        int total_count;
        int completed_count;
        int pending_count;

        public String getDate() {
            return date;
        }

        public String getDay_of_week() {
            return day_of_week;
        }

        public int getTotal_count() {
            return total_count;
        }

        public int getCompleted_count() {
            return completed_count;
        }

        public int getPending_count() {
            return pending_count;
        }
    }
}
