package com.example.taskmanagement;

import com.google.gson.JsonObject;

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
}