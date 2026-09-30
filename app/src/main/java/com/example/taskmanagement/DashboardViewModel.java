package com.example.taskmanagement;

import androidx.lifecycle.ViewModel;

import com.example.taskmanagement.callback.ResponseCallback;

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
    public void getTotalAssign(ResponseCallback<Integer> callback){
        dashboardRepository.getAssignTask(callback);
    }
    public void getToDo(ResponseCallback<Integer> callback){
        dashboardRepository.getToDo(callback);
    }
    public void getProgress(ResponseCallback<Integer>callback){
        dashboardRepository.getInProgress(callback);

    }
    public void getComplet(ResponseCallback<Integer>callback){
        dashboardRepository.getCompletTask(callback);
    }
    public void getSingleTodo(String id,ResponseCallback<Integer> callback){
        dashboardRepository.getSingleToDo(id,callback);
    }
    public void getSingleInProgress(String id,ResponseCallback<Integer> callback){
        dashboardRepository.getSingleInProgress(id,callback);
    }
    public void getSingleReview(String id,ResponseCallback<Integer>callback){
        dashboardRepository.getSingleReviewTask(id,callback);
    }
    public void getSingelComplt(String id,ResponseCallback<Integer>callback){
        dashboardRepository.getSingleCompletTask(id,callback);
    }
}
