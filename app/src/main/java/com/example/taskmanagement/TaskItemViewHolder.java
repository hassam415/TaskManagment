package com.example.taskmanagement;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskmanagement.databinding.TaskuserBinding;

public class TaskItemViewHolder extends RecyclerView.ViewHolder {
    TaskuserBinding binding;
    public TaskItemViewHolder(TaskuserBinding binding) {
        super(binding.getRoot());
        this.binding=binding;
    }
}
