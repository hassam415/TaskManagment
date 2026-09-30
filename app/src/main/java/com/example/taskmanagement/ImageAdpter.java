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
    List<String >uriList;
    OnimageClick  onimageClick;

    public void setOnimageClick(OnimageClick onimageClick) {
        this.onimageClick = onimageClick;
    }

    Boolean showDeletBtn=true;
    public ImageAdpter(Context context, List<String> uriList) {
        this.context = context;
        this.uriList = uriList;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.image_item,parent,false);
        return new ImageViewHolder(view);
    }
    public void RemoveDeletBtn(){
        this.showDeletBtn=false;
        notifyDataSetChanged();
    }
public interface OnimageClick{
        void OnClick(String imageUrl);
}
    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
String uri=uriList.get(position);
Uri uri1=Uri.parse(uri);
        Picasso.get().load(uri).into(holder.imgView);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onimageClick.OnClick(uri);
            }
        });
if (showDeletBtn){
    holder.imagbtn.setVisibility(View.VISIBLE);
}else {
    holder.imagbtn.setVisibility(View.GONE);
}
holder.imagbtn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
uriList.remove(position);
notifyDataSetChanged();
    }
});
    }

    @Override
    public int getItemCount() {
        return uriList.size();
    }
}
