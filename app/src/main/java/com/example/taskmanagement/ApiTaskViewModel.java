package com.example.taskmanagement;

import com.google.gson.JsonObject;

import java.util.List;

public class ApiTaskViewModel {
    public ApiTaskRepository apiTaskRepository;

    public ApiTaskViewModel(ApiTaskRepository apiTaskRepository) {
        this.apiTaskRepository = apiTaskRepository;
    }

    public void creatTask(
            Task task,
            List<Integer> selectedUserIds,
            ApiTaskRepository.TaskCallBack callback) {

        apiTaskRepository.creatTask(task, selectedUserIds, callback);
    }
public void getTask(ApiTaskRepository.TaskCallBack taskCallBack){
        apiTaskRepository.getTask(taskCallBack);
}
public void getSingleTask(String taskId,ApiTaskRepository.TaskCallBack taskCallBack){
        apiTaskRepository.getSingleTask(taskId,taskCallBack);
}
public void delettask(String taskId, ApiTaskRepository.TaskCallBack taskCallBack){
        apiTaskRepository.DeleteTask(taskId,taskCallBack);
}
public void updateTask(String id, JsonObject request, ApiTaskRepository.TaskCallBack taskCallBack){
        apiTaskRepository.updateTask(id,request,taskCallBack);
}
public void getTeam(ApiTaskRepository.TaskCallBack taskCallBack){
        apiTaskRepository.getTeams(taskCallBack);
}
}
