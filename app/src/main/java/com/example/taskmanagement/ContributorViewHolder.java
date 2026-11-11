package com.example.taskmanagement;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskmanagement.databinding.ItemContributorBinding;

public class ContributorViewHolder extends RecyclerView.ViewHolder {
    public ItemContributorBinding binding;
    public ContributorViewHolder(ItemContributorBinding binding) {
        super(binding.getRoot());
        this.binding=binding;
    }
}
