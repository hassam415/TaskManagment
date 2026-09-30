package com.example.taskmanagement;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskmanagement.databinding.UserItemBinding;

public class UserViewHolder extends RecyclerView.ViewHolder {
    UserItemBinding binding;
    public UserViewHolder(UserItemBinding binding) {
        super(binding.getRoot());
        this.binding=binding;
    }
}
