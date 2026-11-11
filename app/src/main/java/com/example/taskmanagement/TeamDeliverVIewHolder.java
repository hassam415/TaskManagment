package com.example.taskmanagement;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskmanagement.databinding.TeamDeliveryItemBinding;

public class TeamDeliverVIewHolder extends RecyclerView.ViewHolder {
    public TeamDeliveryItemBinding binding;
    public TeamDeliverVIewHolder(TeamDeliveryItemBinding binding) {
        super(binding.getRoot());
        this.binding=binding;
    }
}
