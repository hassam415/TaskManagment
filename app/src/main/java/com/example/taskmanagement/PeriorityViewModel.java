package com.example.taskmanagement;

import com.example.taskmanagement.callback.ResponseCallback;

import java.util.List;

public class PeriorityViewModel {
    PeriorityRepository periorityRepository;

    public PeriorityViewModel(PeriorityRepository periorityRepository) {
        this.periorityRepository = periorityRepository;
    }
    public void addPeriority(String periority, ResponseCallback<Periority>responseCallback){
        periorityRepository.savePeriority(periority,responseCallback);
    }
    public void getList(ResponseCallback<List<Periority>>responseCallback){
        periorityRepository.getList(responseCallback);
    }
    public void updatePeriority(String Id,String periority,ResponseCallback<Periority>responseCallback){
        periorityRepository.updatePeriority(Id,periority,responseCallback);
    }
}
