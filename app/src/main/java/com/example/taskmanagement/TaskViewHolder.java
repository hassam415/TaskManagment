package com.example.taskmanagement;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskmanagement.databinding.TaskItemBinding;

public class TaskViewHolder extends RecyclerView.ViewHolder {
   TaskItemBinding binding;
    public TaskViewHolder(TaskItemBinding binding) {
        super(binding.getRoot());
        this.binding=binding;
    }
}
