package com.example.taskmanagement;

import com.example.taskmanagement.callback.ResponseCallback;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CategoryApiRepository {



        public void getCategoryList(ResponseCallback<List<String>> callback) {
            List<String> categoryList = new ArrayList<>(Arrays.asList(
                    "Category",
                    "Development",
                    "Backend",
                    "Frontend",
                    "Testing",
                    "Design"
            ));

            callback.onSuccess(categoryList, "Category loaded successfully");
        }
    }
