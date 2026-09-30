package com.example.taskmanagement;

import android.widget.Toast;

import androidx.lifecycle.ViewModel;

import com.example.taskmanagement.callback.ResponseCallback;

import java.util.List;

public class USerViewModel extends ViewModel {
    UserRepository userRepository;

    public USerViewModel(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void createUser(String name, String email, String password, String address,ResponseCallback<User> responseCallback) {
        userRepository.createUser(name, email, password, address,responseCallback);

    }
    public void getUser(ResponseCallback<List<User>> responseCallback){
        userRepository.getUser(responseCallback);
    }
    public void getSingelUser(String id,ResponseCallback<User> callback){
        userRepository.getSingleUser(id, callback);
    }
    public void loginmethod(String email,String password,ResponseCallback<User> callback){
        userRepository.loginuser(email,password,callback);
    }
}
