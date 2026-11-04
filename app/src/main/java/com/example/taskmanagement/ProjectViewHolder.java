package com.example.taskmanagement;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskmanagement.databinding.ProjectItemBinding;

public class ProjectViewHolder extends RecyclerView.ViewHolder {
    ProjectItemBinding binding;
    public ProjectViewHolder(ProjectItemBinding binding) {
        super(binding.getRoot());
        this.binding=binding;
    }
}
