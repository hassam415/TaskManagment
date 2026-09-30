package com.example.taskmanagement;

import android.app.AlertDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class CustomImagView extends LinearLayout {
    ImageButton plus_btn;
    RecyclerView imagrecyler;
    ImageAdpter imageAdpter;
    OnImageClick onImageClick;
LinearLayout Loader;
    public void setOnImageClick(OnImageClick onImageClick) {
        this.onImageClick = onImageClick;
    }

    List<String>uriList=new ArrayList<>();
    public interface OnGallerClick{
        void  onClick();

    }
    public interface OnCameraClick{
        void onClick();
    }
    public interface OnImageClick{
        void OnClick(String imagUrl);
    }
    public OnGallerClick onGallerClick;
public OnCameraClick onCameraClick;

    public void setOnCameraClick(OnCameraClick onCameraClick) {
        this.onCameraClick = onCameraClick;
    }

    public void setOnGallerClick(OnGallerClick onGallerClick) {
        this.onGallerClick = onGallerClick;
    }

    public CustomImagView(Context context) {
        super(context);
        init(context);
    }

    public CustomImagView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CustomImagView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public CustomImagView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }
    public void setview(){
        plus_btn.setVisibility(View.GONE);
        imageAdpter.RemoveDeletBtn();
    }
    public void showLoader(){
        Loader.setVisibility(View.VISIBLE);
    }
    public void hideLoader(){
        Loader.setVisibility(View.GONE);
    }
    public void init(Context context){
        setOrientation(VERTICAL);
        LayoutInflater.from(context).inflate(R.layout.attachable_view,this,true);
        plus_btn=findViewById(R.id.plus_btn);
        Loader=findViewById(R.id.Loader);
        imagrecyler=findViewById(R.id.imagrecyler);
        imagrecyler.setHasFixedSize(true);
        imageAdpter=new ImageAdpter(context,uriList);
        imagrecyler.setLayoutManager(new GridLayoutManager(CustomImagView.this.getContext(),3));


        imagrecyler.setAdapter(imageAdpter);
        imageAdpter.setOnimageClick(new ImageAdpter.OnimageClick() {
            @Override
            public void OnClick(String imageUrl) {
                onImageClick.OnClick(imageUrl);
            }
        });

        plus_btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
String[]option={"Camera "," Gallary"};
new AlertDialog.Builder(context).setTitle("Select Image").setItems(option,(dialog, which) -> {
    if (which==0){
if (onCameraClick!=null){
    onCameraClick.onClick();
}
    }else {
        if (onGallerClick!=null){
            onGallerClick.onClick();
        }
    }
}).show();
            }
        });
    }
}
