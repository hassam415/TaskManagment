package com.example.taskmanagement;

import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.taskmanagement.callback.ResponseCallback;
import com.example.taskmanagement.databinding.ActivityTaskHistoryBinding;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class TaskHistoryActivity extends MyBaseActivity {
ActivityTaskHistoryBinding binding;
TaskhistoryAdapter taskhistoryAdapter;
List<TaskActivity>taskActivities=new ArrayList<>();
TaskViewModel taskViewModel;
List<Task>taskList=new ArrayList<>();
    TaskDetailAdapter taskDetailAdapter;

    @Override
    public String gettoolbartitle() {
        return "Task Activity";
    }

    @Override
    public View getView() {
        binding=ActivityTaskHistoryBinding.inflate(getLayoutInflater());
        binding.taskhistoryrecyler.setHasFixedSize(true);
        binding.taskhistoryrecyler.setLayoutManager(new LinearLayoutManager(this));
        taskhistoryAdapter=new TaskhistoryAdapter(this,taskActivities);
        binding.taskhistoryrecyler.setAdapter(taskhistoryAdapter);
        binding.taskrecyler.setLayoutManager(new LinearLayoutManager(this));
        binding.taskrecyler.setHasFixedSize(true);
        taskDetailAdapter =new TaskDetailAdapter(this,taskList);
        binding.taskrecyler.setAdapter(taskDetailAdapter);
        taskViewModel =new TaskViewModel(new TaskRepository());
        String id=getIntent().getStringExtra("Activityid");
        if (id!=null){
            taskViewModel.getTaskActivity(id, new ResponseCallback<List<TaskActivity>>() {
                @Override
                public void onSuccess(List<TaskActivity> data, String message) {

                    taskActivities.clear();

                    Collections.sort(data, (a, b) ->b.getTime_stamp().compareTo(a.getTime_stamp())
                    );
                    taskActivities.addAll(data);
                    taskhistoryAdapter.notifyDataSetChanged();
                }

                @Override
                public void onError(String message) {

                }
            });
            taskViewModel.getSingleTask(id, new ResponseCallback<Task>() {
                @Override
                public void onSuccess(Task data, String message) {
                    taskList.clear();
                    taskList.add(data);
                    taskDetailAdapter.notifyDataSetChanged();
                }

                @Override
                public void onError(String message) {

                }
            });
        }
        return binding.getRoot();
    }
}