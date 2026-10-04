package com.example.taskmanagement;

import android.net.Uri;

import androidx.lifecycle.ViewModel;

import com.example.taskmanagement.callback.ResponseCallback;
import com.google.firebase.Timestamp;

import java.util.List;

public class TaskViewModel extends ViewModel {
    TaskRepository taskRepository;

    public TaskViewModel(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }
    public void saveData(String title , String discription, List<String >selecteduser, List<String>images, ResponseCallback<Task> responseCallback){
        taskRepository.saveDate(title,discription,selecteduser,images,responseCallback);
    }
    public void getTask(ResponseCallback<List<Task>> responseCallback){
        taskRepository.getTask(responseCallback);
    }
    public void getSingleTask(String id,ResponseCallback<Task>responseCallback ){
        taskRepository.getSingleTask(id,responseCallback);
    }
    public void removeuser(String id,ResponseCallback responseCallback){
        taskRepository.removeuser(id ,responseCallback);
    }
    public void getTaskCount(String id,ResponseCallback responseCallback){
        taskRepository.getUserTaskCount(id,responseCallback);
    }
    public void getSingleUserTask(String id,ResponseCallback<List<Task>> responseCallback){
        taskRepository.getSingleUserTask(id ,responseCallback);
    }
    public void getTaskActivity(String id,ResponseCallback<List<TaskActivity>> responseCallback){
        taskRepository.getTaskActivity(id, responseCallback);

    }
    public void updateStatus(String taskId,String status,ResponseCallback responseCallback){
        taskRepository.updateStatus(taskId,status,responseCallback);
    }
    public void getTaskByStatus(String status,ResponseCallback<List<Task>>responseCallback){
        taskRepository.getTaskByStatus(status,responseCallback);
    }
    public void getSingleTaskByStatus(String id,String status,ResponseCallback<Task>responseCallback){
        taskRepository.getSingleTaskByStatus(id,status,responseCallback);
    }
    public void getTaskByFilter(String status,Timestamp fromDate,Timestamp toDate,ResponseCallback<List<Task>> responseCallback){
        taskRepository.setTaskByFilter(status,fromDate,toDate,responseCallback);
    }
}
