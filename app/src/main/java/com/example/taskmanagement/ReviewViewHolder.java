package com.example.taskmanagement;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskmanagement.databinding.ReviewItemBinding;

public class ReviewViewHolder extends RecyclerView.ViewHolder {
    ReviewItemBinding binding;
    public ReviewViewHolder(ReviewItemBinding binding) {
        super(binding.getRoot());
        this.binding=binding;
    }
}
