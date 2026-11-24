package com.example.taskmanagement;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class ImageAdpter extends RecyclerView.Adapter<ImageViewHolder> {
    Context context;
    List<Attachement> uriList;
    OnimageClick onimageClick;
OnDeletBtn onDeletBtn;

    public void setOnDeletBtn(OnDeletBtn onDeletBtn) {
        this.onDeletBtn = onDeletBtn;
    }

    public void setOnimageClick(OnimageClick onimageClick) {
        this.onimageClick = onimageClick;
    }

    Boolean showDeletBtn = true;

    public ImageAdpter(Context context, List<Attachement> uriList) {
        this.context = context;
        this.uriList = uriList;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.image_item, parent, false);
        return new ImageViewHolder(view);
    }

    public void RemoveDeletBtn() {
        this.showDeletBtn = false;
        notifyDataSetChanged();
    }

    public interface OnimageClick {
        void OnClick(String imageUrl);
    }
    public interface OnDeletBtn{
        void OnClick(int Id,int position);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {

        Attachement attachment = uriList.get(position);

        String imageUrl = attachment.getDownload_url();

        PicasoUtility.loadImage(
                holder.imagbtn.getContext(),
                imageUrl,
                holder.imgView
        );

        holder.itemView.setOnClickListener(v -> {
            if (onimageClick != null) {
                onimageClick.OnClick(imageUrl);
            }
        });

        holder.imagbtn.setVisibility(
                showDeletBtn ? View.VISIBLE : View.GONE
        );

        holder.imagbtn.setOnClickListener(v -> {

            int pos = holder.getAdapterPosition();

            if (pos != RecyclerView.NO_POSITION && onDeletBtn != null) {
                onDeletBtn.OnClick(attachment.getId(),pos);
            }
        });
    }

    @Override
    public int getItemCount() {
        return uriList.size();
    }
}
