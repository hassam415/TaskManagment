package com.example.taskmanagement;

import android.util.JsonReader;
import android.util.Log;

import com.example.taskmanagement.callback.ResponseCallback;
import com.example.taskmanagement.response.APIResponse;
import com.example.taskmanagement.response.AttachmentResponse;
import com.example.taskmanagement.response.CheckListResponse;
import com.example.taskmanagement.response.PaginateResponse;
import com.example.taskmanagement.response.TaskResponse;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.io.File;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApiTaskRepository {
    private ApiService apiService;

    public ApiTaskRepository() {
        apiService = ApiClient.getRetrofit().create(ApiService.class);
    }

    public interface TaskCallBack {
        void OnSuccess(JsonObject jsonReader);

        void Onerror(String message);
    }

    public void creatTask(Task task, List<Integer> selectedUserIds, ResponseCallback<Task> callback) {

        String token = ApiSesson.getAccess_Token();

        if (token == null || token.trim().isEmpty()) {
            callback.onError("Access token nahi mila. Dobara login karo.");
            return;
        }

        if (task.getTitle() == null || task.getTitle().trim().isEmpty()) {
            callback.onError("Task title required hai.");
            return;
        }

        String priority = task.getPeriority();

        if (priority == null || priority.trim().isEmpty()) {
            callback.onError("Priority select karo.");
            return;
        }

        JsonObject request = new JsonObject();

        request.addProperty("title", task.getTitle().trim());
        request.addProperty(
                "priority",
                priority.trim().toLowerCase(java.util.Locale.ROOT)
        );
        request.addProperty("project_id",task.getProject_id());
        request.addProperty("team_id", task.getTeamId());

            request.addProperty("description", task.getDescription().trim());
        request.addProperty("reviewer_id", task.getReviewer_id());
        if (task.getCategory() != null
                && !task.getCategory().trim().isEmpty()) {
            request.addProperty("category", task.getCategory().trim());
        }
        if (task.getEstimated_hours() != null && !task.getEstimated_hours().trim().isEmpty()) {
            request.addProperty("estimated_hours", task.getEstimated_hours().trim());
        }

        // Selected users ki integer IDs
        if (selectedUserIds != null && !selectedUserIds.isEmpty()) {
            JsonArray assigneeIds = new JsonArray();

            for (Integer id : selectedUserIds) {
                if (id != null && id > 0) {
                    assigneeIds.add(id);
                }
            }

            if (assigneeIds.size() > 0) {
                request.add("assignee_ids", assigneeIds);
            }
        }
        // Start date
        if (task.getStart_date() != null
                && !task.getStart_date().trim().isEmpty()) {

            request.addProperty(
                    "start_date",
                    task.getStart_date().trim()
            );
        }


// Due date
        if (task.getDue_date() != null
                && !task.getDue_date().trim().isEmpty()) {

            request.addProperty(
                    "due_date",
                    task.getDue_date().trim()
            );
        }
        Log.d("TASK_API", "Assignee IDs being sent: "
                + request.get("assignee_ids"));

        Log.d("TASK_API", "Final Request JSON: " + request);

        Log.d("TASK_API", "Request JSON: " + request);

        apiService.createTask(request)
                .enqueue(new Callback<Task>() {

                    @Override
                    public void onResponse(
                            Call<Task> call,
                            Response<Task> response) {

                        Log.d("TASK_API", "HTTP Code: " + response.code());

                        if (response.isSuccessful() && response.body() != null) {
                            Log.d("TASK_API", "Task created: " + response.body());
                            callback.onSuccess(response.body(), "");

                        } else {
                            String error = "HTTP " + response.code();

                            try {
                                if (response.errorBody() != null) {
                                    error += ": " + response.errorBody().string();
                                }
                            } catch (Exception e) {
                                Log.e("TASK_API", "Error reading error body", e);
                            }

                            Log.e("TASK_API", error);
                            callback.onError(error);
                        }
                    }

                    @Override
                    public void onFailure(Call<Task> call, Throwable t) {
                        Log.e("TASK_API", "Network error", t);

                        callback.onError(
                                t.getMessage() != null
                                        ? t.getMessage()
                                        : "Network error"
                        );
                    }
                });
    }

    public void getTask(int page,ResponseCallback<PaginateResponse<Task>> taskCallBack) {
        apiService.getTasks(page).enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<PaginateResponse<Task>> call, Response<PaginateResponse<Task>> response) {
                if (response.body()!=null){
                    taskCallBack.onSuccess(response.body(),"Sucess");
                }


                Log.e("onSuccess: ", response.message() + "");

            }

            @Override
            public void onFailure(Call<PaginateResponse<Task>> call, Throwable throwable) {
                taskCallBack.onError(throwable.getMessage());
                Log.e("onFailure: ", throwable.getLocalizedMessage() + "");

            }
        });
//        Response<Task>
    }

    public void getSingleTask(String taskId, ResponseCallback<Task> taskCallBack) {
        apiService.getSingleTask(taskId).enqueue(new Callback<TaskResponse>() {
            @Override
            public void onResponse(Call<TaskResponse> call, Response<TaskResponse> response) {
                taskCallBack.onSuccess(response.body().getData(),"Success");
            }

            @Override
            public void onFailure(Call<TaskResponse> call, Throwable throwable) {

            }
        });
    }

    public void DeleteTask(String id, TaskCallBack taskCallBack) {
        apiService.deletTask(id).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                taskCallBack.OnSuccess(response.body());
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable throwable) {
                taskCallBack.Onerror(throwable.getMessage());
            }
        });

    }

    public void updateTask(String id, UpdateTask task, ResponseCallback<Task> taskCallBack) {
        apiService.updateTask(id, task).enqueue(new Callback<Task>() {
            @Override
            public void onResponse(Call<Task> call, Response<Task> response) {
                taskCallBack.onSuccess(response.body(), "");
            }

            @Override
            public void onFailure(Call<Task> call, Throwable throwable) {
                taskCallBack.onError(throwable.getMessage());
            }
        });
    }

    public void getTeams(TaskCallBack taskCallBack) {
        apiService.getTeam().enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                taskCallBack.OnSuccess(response.body());
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable throwable) {
                taskCallBack.Onerror(throwable.getMessage());
            }
        });
    }
    public void getteam(ResponseCallback responseCallback){
        apiService.getteam().enqueue(new Callback<TeamResponse>() {
            @Override
            public void onResponse(Call<TeamResponse> call, Response<TeamResponse> response) {
                List<Team> teams=response.body().getData();
                responseCallback.onSuccess(teams,"Suesses");
            }

            @Override
            public void onFailure(Call<TeamResponse> call, Throwable throwable) {
responseCallback.onError(throwable.getMessage());
            }
        });
    }
    public void getRole(ResponseCallback<List<Role>> responseCallback) {

        apiService.getRole().enqueue(new Callback<RoleResponse>() {

            @Override
            public void onResponse(Call<RoleResponse> call,
                                   Response<RoleResponse> response) {

                Log.d("ROLE_API", "URL: " + call.request().url());
                Log.d("ROLE_API", "HTTP CODE: " + response.code());

                if (response.isSuccessful() && response.body() != null) {

                    // API ka complete parsed response
                    String json = new com.google.gson.Gson()
                            .toJson(response.body());

                    Log.d("ROLE_API", "RESPONSE: " + json);

                    List<Role> roles = response.body().getData();

                    if (roles != null && !roles.isEmpty()) {
                        Log.d("ROLE_API", "Role count: " + roles.size());
                        responseCallback.onSuccess(roles, "Successfully loaded");
                    } else {
                        Log.e("ROLE_API", "Data is null or empty");
                        responseCallback.onError("Role list is empty");
                    }

                } else {

                    try {
                        Log.e("ROLE_API", "ERROR BODY: " +
                                (response.errorBody() != null
                                        ? response.errorBody().string()
                                        : "No error body"));
                    } catch (Exception e) {
                        Log.e("ROLE_API", "Error reading response", e);
                    }

                    responseCallback.onError("API Error: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<RoleResponse> call, Throwable t) {
                Log.e("ROLE_API", "Request failed", t);
                responseCallback.onError(t.getMessage());
            }
        });
    }
    public void getCheckList(String taskId,ResponseCallback<List<CheckList>>responseCallback){
        apiService.getCheckList(taskId).enqueue(new Callback<PaginateResponse<CheckList>>() {
            @Override
            public void onResponse(Call<PaginateResponse<CheckList>> call, Response<PaginateResponse<CheckList>> response) {
                responseCallback.onSuccess(response.body().getData(),"Success");
            }

            @Override
            public void onFailure(Call<PaginateResponse<CheckList>> call, Throwable throwable) {
responseCallback.onError(throwable.getMessage());
            }
        });
    }
    public void creatCheckList(String taskid,RequestCheckList checkList,ResponseCallback<CheckList>responseCallback){
        apiService.creatCheckList(checkList,taskid).enqueue(new Callback<CheckListResponse>() {
            @Override
            public void onResponse(Call<CheckListResponse> call, Response<CheckListResponse> response) {
                responseCallback.onSuccess(response.body().getData(),"Succes");
            }

            @Override
            public void onFailure(Call<CheckListResponse> call, Throwable throwable) {

            }
        });
    }
    public void deletCheckList(String taskId,int checkListId,ResponseCallback<CheckList>responseCallback){
        apiService.deletCheckListt(taskId,checkListId).enqueue(new Callback<CheckList>() {
            @Override
            public void onResponse(Call<CheckList> call, Response<CheckList> response) {
                responseCallback.onSuccess(response.body(),"Success");
            }

            @Override
            public void onFailure(Call<CheckList> call, Throwable throwable) {
responseCallback.onError(throwable.getMessage());
            }
        });
    }
    public void toggleCheckList(String taskId,int checkListId,ResponseCallback<CheckList>responseCallback){
        apiService.toggleCheckList(taskId,checkListId).enqueue(new Callback<CheckListToggleResponse>() {
            @Override
            public void onResponse(Call<CheckListToggleResponse> call, Response<CheckListToggleResponse> response) {
                responseCallback.onSuccess(response.body().getData(),"Success");
            }

            @Override
            public void onFailure(Call<CheckListToggleResponse> call, Throwable throwable) {
responseCallback.onError(throwable.getMessage());
            }
        });
    }
    public void getTaskActivity(String taskid,ResponseCallback<List<TaskActivity>> responseCallback){
        apiService.getTaskActivity(taskid).enqueue(new Callback<PaginateResponse<TaskActivity>>() {
            @Override
            public void onResponse(Call<PaginateResponse<TaskActivity>> call, Response<PaginateResponse<TaskActivity>> response) {
                responseCallback.onSuccess(response.body().getData(),"Success");
            }

            @Override
            public void onFailure(Call<PaginateResponse<TaskActivity>> call, Throwable throwable) {
responseCallback.onError(throwable.getMessage());
            }
        });
    }
    public void getAttachement(String taskId,ResponseCallback<List<Attachement>>responseCallback){
        apiService.getAttachment(taskId).enqueue(new Callback<APIResponse<Attachement>>() {
            @Override
            public void onResponse(Call<APIResponse<Attachement>> call, Response<APIResponse<Attachement>> response) {
                if (response.body()!=null){
                    responseCallback.onSuccess(response.body().getData(),"Success");
                }
            }

            @Override
            public void onFailure(Call<APIResponse<Attachement>> call, Throwable throwable) {
responseCallback.onError(throwable.getMessage());
            }
        });
    }
    public void uploadAttachment(String taskid, File file,ResponseCallback<Attachement>
                                 responseCallback){
        RequestBody requestBody = RequestBody.create(
                MediaType.parse("image/jpeg"),
                file
        );
        MultipartBody.Part filepart=MultipartBody.Part.createFormData("file", file.getName(),requestBody);
apiService.uploadAttachement(taskid,filepart).enqueue(new Callback<AttachmentResponse>() {
    @Override
    public void onResponse(Call<AttachmentResponse> call, Response<AttachmentResponse> response) {
        responseCallback.onSuccess(response.body().getData(),"Succcess");
    }

    @Override
    public void onFailure(Call<AttachmentResponse> call, Throwable throwable) {
responseCallback.onError(throwable.getMessage());
    }
});
    }
    public void deleteAttachement(String taskId,int attachmentId,ResponseCallback<Attachement>responseCallback){
        apiService.DeleteAttachemnt(taskId,attachmentId).enqueue(new Callback<Attachement>() {
            @Override
            public void onResponse(Call<Attachement> call, Response<Attachement> response) {
                responseCallback.onSuccess(response.body(),"Succcess");
            }

            @Override
            public void onFailure(Call<Attachement> call, Throwable throwable) {
responseCallback.onError(throwable.getMessage());
            }
        });
    }
}
