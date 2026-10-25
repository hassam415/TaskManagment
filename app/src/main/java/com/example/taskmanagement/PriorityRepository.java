package com.example.taskmanagement;

import com.example.taskmanagement.callback.ResponseCallback;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PriorityRepository {
    public void getPriority(ResponseCallback<List<String>> callback){
        List<String>priorityList=new ArrayList<>(Arrays.asList(
                "Priority",
                "low",
                "normal",
                "high",
                "urgent"
        ));
        callback.onSuccess(priorityList,"Success");
    }
}
