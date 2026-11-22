package com.example.taskmanagement;

import com.example.taskmanagement.response.APIResponse;
import com.example.taskmanagement.response.AuthResponse;
import com.example.taskmanagement.response.CheckListResponse;
import com.example.taskmanagement.response.LogoutResponse;
import com.example.taskmanagement.response.PaginateResponse;
import com.example.taskmanagement.response.TaskResponse;
import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
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
    Call<TaskResponse> getSingleTask(@Path("id") String taskid);

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
                         @Body TeamRequest teamRequest );
    @GET("calendar/summary")
    Call<CalenderResponse> getCalender(@Query("month")String month);
    @GET("calendar/tasks")
    Call<CalenderTask> getCalenderTasks(@Query("date")String date,@Query("date_type")String dateType);
    @GET("reports")
    Call<ReportResponse>getReport();
    @GET("reports")
    Call<ReportResponse>getfilterReport(@Query("date_preset")String datePreset,
                                      @Query("start_date")String startDate,
                                      @Query("end_date")String endDate,
                                      @Query("team_id")int id);
@GET("auth/me")
    Call<AuthResponse>getAuthMe();
@PUT("auth/me")
    Call<AuthResponse>UpdateAuth(@Body UpdateProfileRequest request);
@POST("auth/logout")
    Call<LogoutResponse>logout();
@GET("tasks/{task}/comments")
    Call<PaginateResponse<Comment>> getApiComment(@Path("task")String taskid);
@POST("tasks/{task}/comments")
    Call<Comment> creatComment(@Body RequestComment requestComment,@Path("task") String idTask );
@DELETE("tasks/{task}/comments/{comment}")
    Call<Comment> DeleteComment(@Path("task") String taskId,@Path("comment")int id);
@GET("tasks/{task}/checklists")
    Call<PaginateResponse<CheckList>> getCheckList(@Path("task") String taskID);
@POST("tasks/{task}/checklists")
    Call<CheckListResponse> creatCheckList(@Body RequestCheckList checkList, @Path("task")String taskid);
@DELETE("tasks/{task}/checklists/{checklist}")
    Call<CheckList> deletCheckListt(@Path("task")String taskId,@Path("checklist")int id);
@PATCH("tasks/{task}/checklists/{checklist}/toggle")
    Call<CheckListToggleResponse> toggleCheckList(@Path("task")String taskId,@Path("checklist")int checkId);
@GET("tasks/{task}/activities")
    Call<PaginateResponse<TaskActivity>> getTaskActivity(@Path("task")String taskId);
}
