package com.example.taskmanagement;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.taskmanagement.callback.ResponseCallback;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;


public class DashboardFragment extends Fragment  {
TextView totaluser,usercount,totaltask,taskcount,toDo,progress,compltext,review;
ActionBarDrawerToggle toggle;
CardView toDoCard,progressCard,reviewCard,compltCard;
String todo;
NavigationView navigation;
DashboardViewModel dashboardViewModel;
    public DashboardFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_dashboard,container,false);
    totaluser=view.findViewById(R.id.totaluser);
        toDoCard=view.findViewById(R.id.toDoCard);
        progressCard=view.findViewById(R.id.progressCard);
        reviewCard=view.findViewById(R.id.reviewCard);
        compltCard=view.findViewById(R.id.compltCard);
        navigation=view.findViewById(R.id.navigation);
        usercount=view.findViewById(R.id.usercount);
        totaltask=view.findViewById(R.id.totaltask);
        taskcount=view.findViewById(R.id.taskcount);
        review=view.findViewById(R.id.review);
        toDo=view.findViewById(R.id.toDo);
        progress=view.findViewById(R.id.progress);
        compltext=view.findViewById(R.id.compltext);

       dashboardViewModel=new DashboardViewModel(new DashboardRepository());
        toDoCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), TaskdetailActivity.class);
                intent.putExtra("status","To do");
                startActivity(intent);
            }
        });
        progressCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), TaskdetailActivity.class);
                intent.putExtra("status","In Progess");
                startActivity(intent);
            }
        });
        reviewCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), TaskdetailActivity.class);
                intent.putExtra("status","In Review");
                startActivity(intent);
            }
        });
        compltCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), TaskdetailActivity.class);
                intent.putExtra("status","Completed");
                startActivity(intent);
            }
        });

       dashboardViewModel.getTotalTask(new ResponseCallback<Integer>() {
           @Override
           public void onSuccess(Integer data, String message) {
               taskcount.setText(String.valueOf(data));
           }

           @Override
           public void onError(String message) {

           }
       });
       dashboardViewModel.getTotalUser(new ResponseCallback<Integer>() {
           @Override
           public void onSuccess(Integer data, String message) {
               usercount.setText(String.valueOf(data));
           }

           @Override
           public void onError(String message) {

           }
       });
       dashboardViewModel.getTotalAssign(new ResponseCallback<Integer>() {
           @Override
           public void onSuccess(Integer data, String message) {
               review.setText(String.valueOf(data));
           }

           @Override
           public void onError(String message) {

           }
       });
       dashboardViewModel.getToDo(new ResponseCallback<Integer>() {
           @Override
           public void onSuccess(Integer data, String message) {
               toDo.setText(String.valueOf(data));

           }

           @Override
           public void onError(String message) {

           }
       });
       dashboardViewModel.getProgress(new ResponseCallback<Integer>() {
           @Override
           public void onSuccess(Integer data, String message) {
               progress.setText(String.valueOf(data));
           }

           @Override
           public void onError(String message) {

           }
       });
       dashboardViewModel.getComplet(new ResponseCallback<Integer>() {
           @Override
           public void onSuccess(Integer data, String message) {
               compltext.setText(String.valueOf(data));
           }

           @Override
           public void onError(String message) {

           }
       });
        return view;
    }

}