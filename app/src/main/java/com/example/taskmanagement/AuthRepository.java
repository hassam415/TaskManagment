package com.example.taskmanagement;

import android.widget.Toast;

import com.example.taskmanagement.callback.ResponseCallback;
import com.example.taskmanagement.response.AuthResponse;
import com.example.taskmanagement.response.LogoutResponse;
import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthRepository {

    private final ApiService apiService;

    public AuthRepository() {
        apiService = ApiClient.getRetrofit().create(ApiService.class);
    }

    public interface LoginCallBack {
        void OnSuccess(LoginResponse loginResponse);
        void OnError(String message);
    }
    public interface TaskCallBack{
        void OnSuccess(JsonObject jsonReader);
        void Onerror(String message);
    }
    public void login(String email, String password, LoginCallBack callback) {

        LoginRequest request = new LoginRequest(email, password);

        apiService.login(request).enqueue(new Callback<>() {

            @Override
            public void onResponse(
                    Call<LoginResponse> call,
                    Response<LoginResponse> response
            ) {
                if (response.isSuccessful() && response.body() != null) {

                    LoginResponse result = response.body();

                    if (result.getAccess_token() != null
                            && !result.getAccess_token().isEmpty()) {

                        callback.OnSuccess(result);

                    } else {
                        callback.OnError("API response mein token nahi mila.");
                    }

                } else {
                    callback.OnError("Login failed. HTTP code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                callback.OnError("API connection failed: " + t.getMessage());
            }
        });
    }
    public void getUser(TaskCallBack loginCallBack){
        apiService.getUsers().enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonObject reuslt = response.body();
                loginCallBack.OnSuccess(reuslt);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable throwable) {

            }
        });
    }
    public void creatUser(RequestUser requestUser, ResponseCallback responseCallback){
        apiService.creatUser(requestUser).enqueue(new Callback<RequestUser>() {
            @Override
            public void onResponse(Call<RequestUser> call, Response<RequestUser> response) {
                responseCallback.onSuccess(response.body(),"Success");
            }

            @Override
            public void onFailure(Call<RequestUser> call, Throwable throwable) {
responseCallback.onError(throwable.getMessage());
            }
        });
    }
    public void delete( int Id,ResponseCallback responseCallback){
        apiService.delete(Id).enqueue(new Callback<RequestUser>() {
            @Override
            public void onResponse(Call<RequestUser> call, Response<RequestUser> response) {
                responseCallback.onSuccess(response.body(),"Deleted SuccessFully");
            }

            @Override
            public void onFailure(Call<RequestUser> call, Throwable throwable) {
responseCallback.onError(throwable.getMessage());
            }
        });
    }
    public void getListUser(ResponseCallback<List<User>> responseCallback){
        apiService.getUser().enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.body()!=null){
                    responseCallback.onSuccess(response.body().getData(),"Sucess");
                }

            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable throwable) {

            }
        });
    }
    public  void updateUser(int userID,RequestUser requestUser,ResponseCallback responseCallback){
        apiService.updateUser(userID,requestUser).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                responseCallback.onSuccess(response.body(),"Updated SuccessFully");
            }

            @Override
            public void onFailure(Call<User> call, Throwable throwable) {

            }
        });
    }
    public void getAuthMe(ResponseCallback<User>responseCallback){
        apiService.getAuthMe().enqueue(new Callback<AuthResponse>() {
            @Override
            public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {
                if (response.body()!=null){
                    responseCallback.onSuccess(response.body().getData(),"Succcess");
                }

            }

            @Override
            public void onFailure(Call<AuthResponse> call, Throwable throwable) {
responseCallback.onError(throwable.getMessage());
            }
        });
    }
    public void UpdateCurrentMe(UpdateProfileRequest request,ResponseCallback<AuthResponse>responseCallback){
        apiService.UpdateAuth(request).enqueue(new Callback<AuthResponse>() {
            @Override
            public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {
                responseCallback.onSuccess(response.body(),"Success");
            }

            @Override
            public void onFailure(Call<AuthResponse> call, Throwable throwable) {
responseCallback.onError(throwable.getMessage());
            }
        });
    }
    public void Logout(ResponseCallback<LogoutResponse>responseCallback){
        apiService.logout().enqueue(new Callback<LogoutResponse>() {
            @Override
            public void onResponse(Call<LogoutResponse> call, Response<LogoutResponse> response) {
                responseCallback.onSuccess(response.body(),"Success");
            }

            @Override
            public void onFailure(Call<LogoutResponse> call, Throwable throwable) {
responseCallback.onError(throwable.getMessage());
            }
        });
    }

}