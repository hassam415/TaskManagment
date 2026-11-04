package com.example.taskmanagement;

import com.example.taskmanagement.callback.ResponseCallback;
import com.example.taskmanagement.response.APIResponse;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProjectRepository {
    private ApiService apiService;

    public ProjectRepository() {
        apiService = ApiClient.getRetrofit().create(ApiService.class);
    }

    public void getProjectList(ResponseCallback<List<Project>> responseCallback) {
        apiService.getProject().enqueue(new Callback<APIResponse<Project>>() {
            @Override
            public void onResponse(Call<APIResponse<Project>> call, Response<APIResponse<Project>> response) {
                responseCallback.onSuccess(response.body().getData(), "Projects Loaded");

            }

            @Override
            public void onFailure(Call<APIResponse<Project>> call, Throwable throwable) {

            }
        });
    }

    public void createProject(ProjectRequest requestProject, ResponseCallback<ProjectResponse> responseCallback) {
        apiService.creatProject(requestProject).enqueue(new Callback<ProjectResponse>() {
            @Override
            public void onResponse(Call<ProjectResponse> call, Response<ProjectResponse> response) {
                responseCallback.onSuccess(response.body(), "Success");
            }

            @Override
            public void onFailure(Call<ProjectResponse> call, Throwable throwable) {
                responseCallback.onError(throwable.getMessage());
            }
        });
    }

    public void deleteProject(int id, ResponseCallback<Project> responseCallback) {
        apiService.deleteProject(id).enqueue(new Callback<Project>() {
            @Override
            public void onResponse(Call<Project> call, Response<Project> response) {
                responseCallback.onSuccess(response.body(),"Success");
            }

            @Override
            public void onFailure(Call<Project> call, Throwable throwable) {

            }
        });
    }
    public void updateProject(int id,ProjectRequest projectRequest,ResponseCallback<Project> responseCallback){
        apiService.updateProject(id, projectRequest).enqueue(new Callback<Project>() {
            @Override
            public void onResponse(Call<Project> call, Response<Project> response) {
                responseCallback.onSuccess(response.body(),"Success");
            }

            @Override
            public void onFailure(Call<Project> call, Throwable throwable) {
responseCallback.onError(throwable.getMessage());
            }
        });
    }

}
