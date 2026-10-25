package com.example.taskmanagement;

public class AuthViewModel {
    private  AuthRepository authRepository;

    public AuthViewModel(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    public void login(String email, String password, AuthRepository.LoginCallBack loginCallBack){
authRepository.login(email,password,loginCallBack);
    }
    public void getUserList(AuthRepository.TaskCallBack taskCallBack){
        authRepository.getUser(taskCallBack);
    }
}
