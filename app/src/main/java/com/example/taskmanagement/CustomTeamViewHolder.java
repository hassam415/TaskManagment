package com.example.taskmanagement;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskmanagement.databinding.TeamItemBinding;

public class CustomTeamViewHolder extends RecyclerView.ViewHolder {
    TeamItemBinding binding;
    public CustomTeamViewHolder(TeamItemBinding binding) {
        super(binding.getRoot());
        this.binding=binding;
    }
}
