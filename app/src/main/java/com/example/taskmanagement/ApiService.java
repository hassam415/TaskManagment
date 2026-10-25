package com.example.taskmanagement;

import com.google.gson.JsonObject;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Header;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiService {
@POST("auth/login")
Call<LoginResponse> login(@Body LoginRequest request);



    @POST("tasks")
    Call<JsonObject> createTask(
            @Header("Authorization") String token,
            @Body JsonObject request
    );


@GET("tasks")
    Call<JsonObject>getTasks(
            @Header("Authorization") String token
);
@GET("tasks/{id}")
Call<JsonObject> getSingleTask(
        @Header("Authorization")String token,
        @Path("id") String taskid
);
@GET("users/assignable")
    Call<JsonObject>getUsers(
            @Header("Authorization")String token
);
@DELETE("tasks/{id}")
    Call<JsonObject>deletTask(
            @Header("Authorization")String token ,
            @Path("id") String taskId
);
@PUT("tasks/{id}")
    Call<JsonObject>updateTask(
            @Header("Authorization") String token,
@Path("id") String taskID,
            @Body JsonObject jsonObject
);
@GET("teams")
    Call<JsonObject>getTeam(
            @Header("Authorization")String token
);
}

