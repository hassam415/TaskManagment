package com.example.taskmanagement;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskmanagement.databinding.TaskDetailBinding;

public class TaskDetailViewHolder extends RecyclerView.ViewHolder {
    TaskDetailBinding binding;
    public TaskDetailViewHolder(TaskDetailBinding binding) {
        super(binding.getRoot());
        this.binding=binding;
    }
}
