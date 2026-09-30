package com.example.taskmanagement.callback;

public interface ResponseCallback<T> {

    void onSuccess(T data, String message);

    void onError(String message);
}
