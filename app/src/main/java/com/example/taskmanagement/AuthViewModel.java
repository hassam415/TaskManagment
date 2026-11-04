package com.example.taskmanagement;

import com.example.taskmanagement.callback.ResponseCallback;

import java.util.List;

public class AuthViewModel {
    private AuthRepository authRepository;

    public AuthViewModel(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    public void login(String email, String password, AuthRepository.LoginCallBack loginCallBack) {
        authRepository.login(email, password, loginCallBack);
    }

    public void getUserList(AuthRepository.TaskCallBack taskCallBack) {
        authRepository.getUser(taskCallBack);
    }
    public void creatUser(RequestUser requestUser, ResponseCallback responseCallback){
        authRepository.creatUser(requestUser,responseCallback);
    }
    public void deleteUser(int Id,ResponseCallback responseCallback){
        authRepository.delete(Id,responseCallback);
    }
    public void getCurrentUser(ResponseCallback<List<User>> responseCallback){
        authRepository.getListUser(responseCallback);
    }
    public void UpdateUser(int id,RequestUser requestUser,ResponseCallback responseCallback){
        authRepository.updateUser(id,requestUser,responseCallback);
    }
}
