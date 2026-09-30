package com.example.taskmanagement;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.taskmanagement.callback.ResponseCallback;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;


public class UserTaskDisplayFragment extends Fragment {
CustomImagView customimg;
TaskViewModel taskViewModel;
TextView discription,title;
LinearLayout  loader;
CommentsAdapter commentsAdapter;
RecyclerView taskrecyler;
ImageButton imgbtn;
CommentsViewModel commentsViewModel;
List<Comment>commentList=new ArrayList<>();
    String id;
    public UserTaskDisplayFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view=inflater.inflate(R.layout.fragment_user_task_display,container,false);
        title=view.findViewById(R.id.title);
        discription=view.findViewById(R.id.discription);
        commentsViewModel=new CommentsViewModel(new CommentsRepository());
        taskrecyler=view.findViewById(R.id.taskrecyler);
        imgbtn=view.findViewById(R.id.imgbtn);
        customimg=view.findViewById(R.id.customimg);
        loader=view.findViewById(R.id.loader);
        taskrecyler.setLayoutManager(new LinearLayoutManager(getContext()));
        commentsAdapter=new CommentsAdapter(getContext(),commentList);
        taskrecyler.setAdapter(commentsAdapter);
        customimg.setview();
        taskViewModel=new TaskViewModel(new TaskRepository());
        Bundle bundle=getArguments();
        id =bundle.getString("Id");
        loader.setVisibility(View.VISIBLE);
     taskViewModel.getSingleTask(id, new ResponseCallback<Task>() {
         @Override
         public void onSuccess(Task data, String message) {
             loader.setVisibility(View.GONE);
         title.setText("Title :"+data.getTitle());
         discription.setText("Discription :"+data.getDiscription());

         customimg.uriList.clear();
         customimg.uriList.addAll(data.getSelectedImges());
         customimg.imageAdpter.notifyDataSetChanged();
         }

         @Override
         public void onError(String message) {
             loader.setVisibility(View.GONE);
         }
     });
     customimg.setOnImageClick(new CustomImagView.OnImageClick() {
         @Override
         public void OnClick(String imagUrl) {
             Intent intent=new Intent(getContext(), PhotoZoomActivity.class);
             intent.putExtra("ImageUrl",imagUrl);
             startActivity(intent);
         }
     });
     commentsViewModel.getList(id, new ResponseCallback<List<Comment>>() {
         @Override
         public void onSuccess(List<Comment> data, String message) {
             commentList.clear();
             commentList.addAll(data);
             commentsAdapter.notifyDataSetChanged();
         }

         @Override
         public void onError(String message) {

         }
     });
     commentsAdapter.setOnBtnClick(new CommentsAdapter.OnBtnClick() {
         @Override
         public void Onclick(String reply, String Id) {
             commentsViewModel.savereply(id, Id, reply, new ResponseCallback<Comment>() {
                 @Override
                 public void onSuccess(Comment data, String message) {

                 }

                 @Override
                 public void onError(String message) {

                 }
             });
         }
     });
     imgbtn.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             LayoutInflater inflater1=LayoutInflater.from(getContext());
             View view1=inflater1.inflate(R.layout.dialogue_input,null);
             TextInputLayout commentedit=view1.findViewById(R.id.commentedit);
             new AlertDialog.Builder(getContext()).setView(view1).setPositiveButton("Send",(dialog, which) -> {
                 String comment=commentedit.getEditText().getText().toString();
                 commentsViewModel.saveComment(id, comment, new ResponseCallback<Comment>() {
                     @Override
                     public void onSuccess(Comment data, String message) {

                     }

                     @Override
                     public void onError(String message) {

                     }
                 });
             }).setNegativeButton("Cancel",(dialog, which) -> {
                 dialog.dismiss();
             }).show();
         }
     });
        return view;
    }
}