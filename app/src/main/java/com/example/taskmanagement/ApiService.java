package com.example.taskmanagement;

import com.example.taskmanagement.response.APIResponse;
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
    Call<ResponseDashBoard> getDashboard();

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

    @GET("roles")
    Call<RoleResponse> getRole();

    @GET("teams")
    Call<TeamResponse> getteam();

    @POST("users")
    Call<RequestUser> creatUser(@Body RequestUser userRequest);

    @GET("users")
    Call<UserResponse> getUser();

    @DELETE("users/{id}")
    Call<RequestUser> delete(@Path("id") int id);

    @PUT("users/{id}")
    Call<User> updateUser(@Path("id") int id,
                          @Body RequestUser requestUser);

    @POST("projects")
    Call<ProjectResponse> creatProject(@Body ProjectRequest requestProject);

    @GET("projects")
    Call<APIResponse<Project>> getProject();

    @DELETE("projects/{id}")
    Call<Project> deleteProject(@Path("id") int id);
    @PUT("projects/{id}")
    Call<Project>updateProject(@Path("id") int id,
                               @Body ProjectRequest  projectRequest);
    @POST("teams")
    Call<Team> creatTeam(@Body TeamRequest teamRequest);
    @GET("teams")
    Call<APIResponse<Team>> getTeamList();
    @GET("departments")
    Call<APIResponse<TeamDepartment>> getDepartment();
    @DELETE("teams/{id}")
    Call<Team> deletTeam(@Path("id") int id);
    @PUT("teams/{id}")
    Call<Team>updateTeam(@Path("id")int id,
                         @Body TeamRequest
teamRequest                         );
}
