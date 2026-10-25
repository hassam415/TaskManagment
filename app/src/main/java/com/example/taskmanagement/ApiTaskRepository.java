package com.example.taskmanagement;

import android.util.JsonReader;
import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApiTaskRepository {
    private ApiService apiService;

    public ApiTaskRepository() {
        apiService=ApiClient.getRetrofit().create(ApiService.class);
    }
    public interface TaskCallBack{
        void OnSuccess(JsonObject jsonReader);
        void Onerror(String message);
    }

    public void creatTask(
            Task task,
            List<Integer> selectedUserIds,
            TaskCallBack callback) {

        String token = ApiSesson.getAccess_Token();

        if (token == null || token.trim().isEmpty()) {
            callback.Onerror("Access token nahi mila. Dobara login karo.");
            return;
        }

        if (task.getTitle() == null || task.getTitle().trim().isEmpty()) {
            callback.Onerror("Task title required hai.");
            return;
        }

        String priority = task.getPeriority();

        if (priority == null || priority.trim().isEmpty()) {
            callback.Onerror("Priority select karo.");
            return;
        }

        JsonObject request = new JsonObject();

        request.addProperty("title", task.getTitle().trim());
        request.addProperty(
                "priority",
                priority.trim().toLowerCase(java.util.Locale.ROOT)
        );
request.addProperty("team_id", task.getTeamId());
        if (task.getDiscription() != null
                && !task.getDiscription().trim().isEmpty()) {
            request.addProperty("description", task.getDiscription().trim());
        }

        if (task.getCategory() != null
                && !task.getCategory().trim().isEmpty()) {
            request.addProperty("category", task.getCategory().trim());
        }
        if (task.getEstimated_hours()!=null&& !task.getEstimated_hours().trim().isEmpty()){
            request.addProperty("estimated_hours",task.getEstimated_hours().trim());
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

        apiService.createTask("Bearer " + token, request)
                .enqueue(new Callback<JsonObject>() {

                    @Override
                    public void onResponse(
                            Call<JsonObject> call,
                            Response<JsonObject> response) {

                        Log.d("TASK_API", "HTTP Code: " + response.code());

                        if (response.isSuccessful() && response.body() != null) {
                            Log.d("TASK_API", "Task created: " + response.body());
                            callback.OnSuccess(response.body());

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
                            callback.Onerror(error);
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        Log.e("TASK_API", "Network error", t);

                        callback.Onerror(
                                t.getMessage() != null
                                        ? t.getMessage()
                                        : "Network error"
                        );
                    }
                });
    }

    public void getTask(TaskCallBack taskCallBack){
        String token=ApiSesson.getAccess_Token();
        apiService.getTasks("Bearer " + token).enqueue(new Callback<JsonObject>() {
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
    public void getSingleTask(String taskId,TaskCallBack taskCallBack){
        String token=ApiSesson.getAccess_Token();
        apiService.getSingleTask("Bearer " + token,taskId).enqueue(new Callback<JsonObject>() {
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
    public void DeleteTask(String id,TaskCallBack taskCallBack){
        String token=ApiSesson.getAccess_Token();
        apiService.deletTask("Bearer " + token,id).enqueue(new Callback<JsonObject>() {
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
    public void updateTask(String id ,JsonObject request,TaskCallBack taskCallBack){
        String token=ApiSesson.getAccess_Token();
        apiService.updateTask("Bearer " + token,id,request).enqueue(new Callback<JsonObject>() {
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
    public void getTeams(TaskCallBack taskCallBack){
        String token=ApiSesson.getAccess_Token();
        apiService.getTeam("Bearer " +token).enqueue(new Callback<JsonObject>() {
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
}
