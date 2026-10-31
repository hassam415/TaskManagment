package com.example.taskmanagement;

import com.example.taskmanagement.response.PaginateResponse;
import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    @POST("auth/login")
    Call<LoginResponse> login(@Body LoginRequest request);

    @POST("tasks")
    Call<Task> createTask(@Body JsonObject request);

    @GET("tasks")
    Call<PaginateResponse<Task>> getTasks(@Query("page") int page);
@GET("dashboard")
Call<ResponseDashBoard>getDashboard();
    @GET("tasks/{id}")
    Call<Task> getSingleTask(@Path("id") String taskid);

    @GET("users/assignable")
    Call<JsonObject> getUsers();

    @DELETE("tasks/{id}")
    Call<JsonObject> deletTask(@Path("id") String taskId);

    @PUT("tasks/{id}")
    Call<Task> updateTask(
            @Path("id") String taskID,
            @Body UpdateTask updateTask
    );

    @GET("teams")
    Call<JsonObject> getTeam();
}
