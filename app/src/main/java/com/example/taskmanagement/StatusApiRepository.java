package com.example.taskmanagement;

import android.view.View;

import com.example.taskmanagement.callback.ResponseCallback;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StatusApiRepository {
    public void getStatusList(ResponseCallback<List<String>>callback){
        List<String>statusList=new ArrayList<>(Arrays.asList(
                "Status",
                "draft",
                "assigned",
                "in Progress",
                "on Hold",
                "in Review",
                "changes Requested",
                "completed",
                "cancelled"

        ));
        callback.onSuccess(statusList,"Success");
    }

}
