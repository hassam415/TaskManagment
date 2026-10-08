package com.example.taskmanagement;

import com.example.taskmanagement.callback.ResponseCallback;

import java.util.List;

public class StatusViewModel {
    StatusRepository statusRepository;

    public StatusViewModel(StatusRepository statusRepository) {
        this.statusRepository = statusRepository;
    }
    public void addStatus(String status, ResponseCallback<Status>responseCallback){
        statusRepository.addStatus(status,responseCallback);
    }
    public void getList(ResponseCallback<List<Status>>responseCallback){
        statusRepository.getList(responseCallback);
    }
    public void updateStatus(String id,String status,ResponseCallback<Status>responseCallback){
        statusRepository.updateStatus(id,status,responseCallback);
    }
    public void getSingleStatus(String id,ResponseCallback<Status>responseCallback){
        statusRepository.getSingleStatus(id,responseCallback);
    }
}
