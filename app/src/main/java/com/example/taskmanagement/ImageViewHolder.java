package com.example.taskmanagement;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ImageViewHolder extends RecyclerView.ViewHolder {
    ImageView imgView,imagbtn;
    public ImageViewHolder(@NonNull View itemView) {
        super(itemView);
        imgView=itemView.findViewById(R.id.imgView);
        imagbtn=itemView.findViewById(R.id.imagbtn);
    }
}
