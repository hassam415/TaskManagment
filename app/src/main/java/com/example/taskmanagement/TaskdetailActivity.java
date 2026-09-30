package com.example.taskmanagement;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.taskmanagement.callback.ResponseCallback;
import com.example.taskmanagement.databinding.ActivityTaskdetailBinding;

import java.util.ArrayList;
import java.util.List;

public class TaskdetailActivity extends MyBaseActivity {

    ActivityTaskdetailBinding binding;


    @Override
    public String gettoolbartitle() {
        return "Task List";
    }

    @Override
    public View getView() {
        binding = ActivityTaskdetailBinding.inflate(getLayoutInflater());


        return binding.getRoot();
    }

    @Override
    protected void onViewCreated() {
        super.onViewCreated();
        String getStatus = getIntent().getStringExtra("status");
        Log.d("STATUS_CHECK", "Activity received = [" + getStatus + "]");
        Bundle bundle = new Bundle();
        bundle.putString("status", getStatus);
        TaskListFragment fragment = new TaskListFragment();
        fragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(binding.fragmentContainer.getId(), fragment).commit();
    }
}