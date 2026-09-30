package com.example.taskmanagement;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskmanagement.databinding.CommentsItemBinding;

public class CommentViewHolder extends RecyclerView.ViewHolder {
    CommentsItemBinding binding;
    public CommentViewHolder(CommentsItemBinding binding) {
        super(binding.getRoot());
        this.binding=binding;
    }
}
