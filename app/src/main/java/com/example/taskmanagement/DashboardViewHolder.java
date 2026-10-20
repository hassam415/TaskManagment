package com.example.taskmanagement;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskmanagement.databinding.CardItemBinding;

public class DashboardViewHolder extends RecyclerView.ViewHolder {
    CardItemBinding binding;
    public DashboardViewHolder(CardItemBinding binding) {
        super(binding.getRoot());
this.binding=binding;
    }
}
