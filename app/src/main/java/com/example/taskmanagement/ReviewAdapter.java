package com.example.taskmanagement;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskmanagement.databinding.ReviewItemBinding;

import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewViewHolder> {
    Context context;
    List<Review> reviews;

    public ReviewAdapter(Context context, List<Review> reviews) {
        this.context = context;
        this.reviews = reviews;
    }

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        ReviewItemBinding binding=ReviewItemBinding.inflate(inflater,parent,false);

        return new ReviewViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {
Review review=reviews.get(position);
holder.binding.titlId.setText(review.getReference_code());
holder.binding.title.setText(review.getTitle());
    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }
}
