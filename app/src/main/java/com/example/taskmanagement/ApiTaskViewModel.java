package com.example.taskmanagement;

import com.example.taskmanagement.callback.ResponseCallback;
import com.example.taskmanagement.response.PaginateResponse;
import com.google.gson.JsonObject;

import java.io.File;
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

    public void getTask(int page,ResponseCallback<PaginateResponse<Task>> taskCallBack) {
        apiTaskRepository.getTask(page,taskCallBack);
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
    public void getteam(ResponseCallback<List<Team>> responseCallback){
        apiTaskRepository.getteam(responseCallback);
    }
   public void getRole(ResponseCallback<List<Role>> responseCallback){
        apiTaskRepository.getRole(responseCallback);
   }
   public void getCheckList(String taskId,ResponseCallback<List<CheckList>>responseCallback){
        apiTaskRepository.getCheckList(taskId,responseCallback);
   }
   public void creatCheckList(String taskId,RequestCheckList checkList,ResponseCallback<CheckList>responseCallback){
        apiTaskRepository.creatCheckList(taskId,checkList,responseCallback);
   }
   public void deletCheckList(String taskId,int checkListId,ResponseCallback<CheckList>responseCallback){
        apiTaskRepository.deletCheckList(taskId,checkListId,responseCallback);
   }
   public void toggleCheclist(String taskId,int checkListId,ResponseCallback<CheckList>responseCallback){
        apiTaskRepository.toggleCheckList(taskId,checkListId,responseCallback);
   }
   public void getTaskActivity(String taskId,ResponseCallback<List<TaskActivity>>responseCallback){
        apiTaskRepository.getTaskActivity(taskId,responseCallback);
   }
   public void getAttachment(String taskId,ResponseCallback<List<Attachement>>responseCallback){
        apiTaskRepository.getAttachement(taskId,responseCallback);
   }
   public void uploadAttachment(String taskid, File file,ResponseCallback<Attachement>responseCallback){
        apiTaskRepository.uploadAttachment(taskid,file,responseCallback);
   }
   public void deletAttachement(String taskID,int attachementId,ResponseCallback<Attachement>responseCallback){
        apiTaskRepository.deleteAttachement(taskID,attachementId,responseCallback);
   }
}
