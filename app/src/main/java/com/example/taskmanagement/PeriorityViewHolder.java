package com.example.taskmanagement;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskmanagement.databinding.PeriorityItemBinding;

public class PeriorityViewHolder extends RecyclerView.ViewHolder {
    public PeriorityItemBinding binding;
    public PeriorityViewHolder(PeriorityItemBinding binding) {
        super(binding.getRoot());
        this.binding=binding;
    }
}
