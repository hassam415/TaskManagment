package com.example.taskmanagement;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskmanagement.databinding.ChecklistItemBinding;

public class CheckListViewHolder extends RecyclerView.ViewHolder {
    public ChecklistItemBinding binding;
    public CheckListViewHolder(ChecklistItemBinding binding) {
        super(binding.getRoot());
        this.binding=binding;
    }
}
