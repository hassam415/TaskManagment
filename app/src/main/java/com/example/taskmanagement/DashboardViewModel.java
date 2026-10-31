package com.example.taskmanagement;

import androidx.lifecycle.ViewModel;

import com.example.taskmanagement.callback.ResponseCallback;

import java.util.List;

public class DashboardViewModel extends ViewModel {
    public DashboardRepository dashboardRepository;

    public DashboardViewModel(DashboardRepository dashboardRepository) {
        this.dashboardRepository = dashboardRepository;
    }
    public void getTotalTask(ResponseCallback<Integer>callback){
        dashboardRepository.getTask(callback);

    }
    public void getTotalUser(ResponseCallback<Integer>callback){
        dashboardRepository.getUser(callback);
    }
public void getStatus(String statusId,ResponseCallback<Integer>callback){
        dashboardRepository.getTaskStatus(statusId,callback);
}
public void getSingleTaskStatus(String statusId,String id,ResponseCallback<Integer>callback ){
        dashboardRepository.getSingleUserTask(statusId,id,callback);
}
public void getTaskByApi(ResponseCallback<ResponseDashBoard>callback){
        dashboardRepository.getTaskApi(callback);
}
}
