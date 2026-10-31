package com.example.taskmanagement;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskmanagement.databinding.MilstoneItmeBinding;

public class DeadLineListViewHolder extends RecyclerView.ViewHolder {
    MilstoneItmeBinding binding;
    public DeadLineListViewHolder(MilstoneItmeBinding binding) {
        super(binding.getRoot());
        this.binding=binding;
    }
}
