package com.example.taskmanagement;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskmanagement.databinding.UserDetailBinding;

public class UserDetailViewHolder extends RecyclerView .ViewHolder{
    UserDetailBinding binding;
    public UserDetailViewHolder(UserDetailBinding binding) {
        super(binding.getRoot());
        this.binding=binding;
    }
}
