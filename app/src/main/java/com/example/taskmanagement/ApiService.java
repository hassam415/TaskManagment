package com.example.taskmanagement;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiService {
    @POST("auth/login")
    Call<LoginResponse> login(@Body LoginRequest request);

    @POST("tasks")
    Call<JsonObject> createTask(@Body JsonObject request);

    @GET("tasks")
    Call<JsonObject> getTasks();

    @GET("tasks/{id}")
    Call<JsonObject> getSingleTask(@Path("id") String taskid);

    @GET("users/assignable")
    Call<JsonObject> getUsers();

    @DELETE("tasks/{id}")
    Call<JsonObject> deletTask(@Path("id") String taskId);

    @PUT("tasks/{id}")
    Call<JsonObject> updateTask(
            @Path("id") String taskID,
            @Body UpdateTask updateTask
    );

    @GET("teams")
    Call<JsonObject> getTeam();
}
