package com.example.taskmanagement;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskmanagement.databinding.UsertaskItemBinding;

public class UserTasksViewHolder extends RecyclerView.ViewHolder {
    UsertaskItemBinding binding;
    public UserTasksViewHolder(UsertaskItemBinding binding) {
        super(binding.getRoot());
        this.binding=binding;
    }
}
