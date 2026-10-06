package com.example.taskmanagement;

import com.example.taskmanagement.callback.ResponseCallback;

import java.util.List;

public class CategoryViewModel {
    CategoryRepository categoryRepository;

    public CategoryViewModel(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }
    public void addCategory(String category,ResponseCallback<Category>responseCallback){
        categoryRepository.addCategory(category,responseCallback);
    }
    public  void getList(ResponseCallback<List<Category>>callback){
        categoryRepository.getList(callback);
    }
}
