package com.example.taskmanagement;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskmanagement.databinding.WorkloadItemBinding;

public class WorkLoadViewHolder extends RecyclerView.ViewHolder {
    WorkloadItemBinding binding;
    public WorkLoadViewHolder(WorkloadItemBinding binding) {
        super(binding.getRoot());
        this.binding=binding;
    }
}
