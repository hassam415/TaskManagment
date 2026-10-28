package com.example.taskmanagement;

import com.example.taskmanagement.callback.ResponseCallback;
import com.example.taskmanagement.response.PaginateResponse;
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
            ResponseCallback<Task> callback) {

        apiTaskRepository.creatTask(task, selectedUserIds, callback);
    }

    public void getTask(ResponseCallback<PaginateResponse<Task>> taskCallBack) {
        apiTaskRepository.getTask(taskCallBack);
    }

    public void getSingleTask(String taskId, ResponseCallback<Task> taskCallBack) {
        apiTaskRepository.getSingleTask(taskId, taskCallBack);
    }

    public void delettask(String taskId, ApiTaskRepository.TaskCallBack taskCallBack) {
        apiTaskRepository.DeleteTask(taskId, taskCallBack);
    }

    public void updateTask(String id, UpdateTask task,ResponseCallback<Task> taskCallBack) {
        apiTaskRepository.updateTask(id, task, taskCallBack);
    }

    public void getTeam(ApiTaskRepository.TaskCallBack taskCallBack) {
        apiTaskRepository.getTeams(taskCallBack);
    }
}
