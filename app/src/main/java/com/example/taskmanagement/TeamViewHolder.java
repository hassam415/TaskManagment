package com.example.taskmanagement;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskmanagement.databinding.TeamItemsBinding;

public class TeamViewHolder extends RecyclerView.ViewHolder {
    TeamItemsBinding binding;
    public TeamViewHolder(TeamItemsBinding binding)  {
        super(binding.getRoot());
        this.binding=binding;
    }
}
