package com.example.taskmanagement;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import com.example.taskmanagement.callback.ResponseCallback;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;


public class UserDashboardFragment extends Fragment {

    TaskViewModel taskViewModel;
    TextView taskcount,toDo,progress,review,compltext;
    FirebaseAuth auth;
    CardView card,toDoCard,progressCard,reviewCard,compltCard;
DashboardViewModel dashboardViewModel;
    public UserDashboardFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task_dashboard, container, false);
        taskcount = view.findViewById(R.id.taskcount);
        toDoCard = view.findViewById(R.id.toDoCard);
        progressCard = view.findViewById(R.id.progressCard);
        ImageView filterBtn=requireActivity().findViewById(R.id.filterBtn);
        reviewCard = view.findViewById(R.id.reviewCard);
        compltCard = view.findViewById(R.id.compltCard);
        toDo = view.findViewById(R.id.toDo);
        progress = view.findViewById(R.id.progress);
        review = view.findViewById(R.id.review);
        compltext = view.findViewById(R.id.compltext);
        card = view.findViewById(R.id.card);
        filterBtn.setVisibility(View.GONE);
        auth = FirebaseAuth.getInstance();
        String id = auth.getCurrentUser().getUid();

        taskViewModel = new TaskViewModel(new TaskRepository());
        dashboardViewModel=new DashboardViewModel(new DashboardRepository());
        toDoCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SingleUserTaskListFragment fragment=new SingleUserTaskListFragment();
                Bundle bundle=new Bundle();
                bundle.putString("status","To do");
                fragment.setArguments(bundle);
                getParentFragmentManager().beginTransaction().replace(R.id.fragmentcontainer,fragment).commit();

            }
        });
        progressCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SingleUserTaskListFragment fragment=new SingleUserTaskListFragment();
                Bundle bundle=new Bundle();
                bundle.putString("status","In Progess");
                fragment.setArguments(bundle);
                getParentFragmentManager().beginTransaction().replace(R.id.fragmentcontainer,fragment).commit();
            }
        });
        reviewCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SingleUserTaskListFragment fragment=new SingleUserTaskListFragment();
                Bundle bundle=new Bundle();
                bundle.putString("status","In Review");
                fragment.setArguments(bundle);
                getParentFragmentManager().beginTransaction().replace(R.id.fragmentcontainer,fragment).commit();

            }
        });
        compltCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SingleUserTaskListFragment fragment=new SingleUserTaskListFragment();
                Bundle bundle=new Bundle();
                bundle.putString("status","Completed");
                fragment.setArguments(bundle);
                getParentFragmentManager().beginTransaction().replace(R.id.fragmentcontainer,fragment).commit();

            }
        });

        taskViewModel.getTaskCount(id, new ResponseCallback() {
            @Override
            public void onSuccess(Object data, String message) {
                taskcount.setText(String.valueOf(data));
            }

            @Override
            public void onError(String message) {

            }
        });
dashboardViewModel.getSingleTodo(id, new ResponseCallback<Integer>() {
    @Override
    public void onSuccess(Integer data, String message) {
        toDo.setText(String.valueOf(data));
    }

    @Override
    public void onError(String message) {

    }
});
dashboardViewModel.getSingleInProgress(id, new ResponseCallback<Integer>() {
    @Override
    public void onSuccess(Integer data, String message) {
        progress.setText(String.valueOf(data));
    }

    @Override
    public void onError(String message) {

    }
});
dashboardViewModel.getSingleReview(id, new ResponseCallback<Integer>() {
    @Override
    public void onSuccess(Integer data, String message) {
        review.setText(String.valueOf(data));
    }

    @Override
    public void onError(String message) {

    }
});
dashboardViewModel.getSingelComplt(id, new ResponseCallback<Integer>() {
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