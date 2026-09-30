package com.example.taskmanagement;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskmanagement.databinding.HistoryItemBinding;

public class TaskHistoryViewholder extends RecyclerView.ViewHolder {
    public HistoryItemBinding binding;
    public TaskHistoryViewholder(HistoryItemBinding binding) {
        super(binding.getRoot());
        this.binding=binding;
    }
}
