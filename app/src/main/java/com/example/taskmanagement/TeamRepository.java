package com.example.taskmanagement;

import com.example.taskmanagement.callback.ResponseCallback;
import com.example.taskmanagement.response.APIResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TeamRepository {
    ApiService apiService;

    public TeamRepository() {
        apiService=ApiClient.getRetrofit().create(ApiService.class);
    }
public void TeamList(ResponseCallback<List<Team>>responseCallback ){
        apiService.getTeamList().enqueue(new Callback<APIResponse<Team>>() {
            @Override
            public void onResponse(Call<APIResponse<Team>> call, Response<APIResponse<Team>> response) {
                responseCallback.onSuccess(response.body().getData(),"Success");
            }

            @Override
            public void onFailure(Call<APIResponse<Team>> call, Throwable throwable) {
responseCallback.onError(throwable.getMessage());
            }
        });

}
public void creatTeam(TeamRequest teamRequest,ResponseCallback<Team>responseCallback ){
        apiService.creatTeam(teamRequest).enqueue(new Callback<Team>() {
            @Override
            public void onResponse(Call<Team> call, Response<Team> response) {
                responseCallback.onSuccess(response.body(),"Success");
            }

            @Override
            public void onFailure(Call<Team> call, Throwable throwable) {
responseCallback.onError(throwable.getMessage());
            }
        });

}
public void getDepartment(ResponseCallback<List<TeamDepartment>>responseCallback){
        apiService.getDepartment().enqueue(new Callback<APIResponse<TeamDepartment>>() {
            @Override
            public void onResponse(Call<APIResponse<TeamDepartment>> call, Response<APIResponse<TeamDepartment>> response) {
                responseCallback.onSuccess(response.body().getData(),"Success");
            }

            @Override
            public void onFailure(Call<APIResponse<TeamDepartment>> call, Throwable throwable) {
responseCallback.onError(throwable.getMessage());
            }
        });
}
public void deletTeam(int id,ResponseCallback<Team>responseCallback){
        apiService.deletTeam(id).enqueue(new Callback<Team>() {
            @Override
            public void onResponse(Call<Team> call, Response<Team> response) {
                responseCallback.onSuccess(response.body(),"Success");
            }

            @Override
            public void onFailure(Call<Team> call, Throwable throwable) {
responseCallback.onError(throwable.getMessage());
            }
        });
}
public void updateTeam(int id,TeamRequest
 teamRequest,
        ResponseCallback<Team>responseCallback){
        apiService.updateTeam(id,teamRequest).enqueue(new Callback<Team>() {
            @Override
            public void onResponse(Call<Team> call, Response<Team> response) {
                responseCallback.onSuccess(response.body(),"Success");
            }

            @Override
            public void onFailure(Call<Team> call, Throwable throwable) {
responseCallback.onError(throwable.getMessage());
            }
        });
}
}
