package com.example.taskmanagement;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskmanagement.databinding.StatusItemBinding;

public class StatusViewHolder extends RecyclerView.ViewHolder {
    public StatusItemBinding  binding;
    public StatusViewHolder(StatusItemBinding binding) {
        super(binding.getRoot());
        this.binding=binding;
    }
}
