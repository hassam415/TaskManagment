package com.example.taskmanagement;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import com.example.taskmanagement.callback.ResponseCallback;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;


public class UserDashboardFragment extends Fragment {
    TaskViewModel taskViewModel;
    TextView taskcount;
    FirebaseAuth auth;
    RecyclerView dashbordRecyler;
    DashboardAdapter dashboardAdapter;
DashboardViewModel dashboardViewModel;
List<Status>statusList=new ArrayList<>();
StatusViewModel statusViewModel;

    String id;
    public UserDashboardFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task_dashboard, container, false);
        taskcount = view.findViewById(R.id.taskcount);
        ImageView filterBtn=requireActivity().findViewById(R.id.filterBtn);
dashbordRecyler=view.findViewById(R.id.dashbordRecyler);
dashbordRecyler.setHasFixedSize(true);
dashbordRecyler.setLayoutManager(new GridLayoutManager(getContext(),2));
dashboardAdapter=new DashboardAdapter(getContext(),statusList);
dashbordRecyler.setAdapter(dashboardAdapter);
        auth = FirebaseAuth.getInstance();
        id = auth.getCurrentUser().getUid();
taskViewModel=new TaskViewModel(new TaskRepository());
statusViewModel=new StatusViewModel(new StatusRepository());
dashboardViewModel=new DashboardViewModel(new DashboardRepository());
statusViewModel.getList(new ResponseCallback<List<Status>>() {
    @Override
    public void onSuccess(List<Status> data, String message) {
        statusList.clear();
        statusList.addAll(data);
        dashboardAdapter.notifyDataSetChanged();
        for (Status status:statusList){
            dashboardViewModel.getSingleTaskStatus(status.getId(),id, new ResponseCallback<Integer>() {
                @Override
                public void onSuccess(Integer data, String message) {

                    dashboardAdapter.setTaskCount(status.getId(),data);
                }

                @Override
                public void onError(String message) {

                }
            });
        }
    }

    @Override
    public void onError(String message) {

    }
});
        filterBtn.setVisibility(View.GONE);


        taskViewModel = new TaskViewModel(new TaskRepository());
        dashboardViewModel=new DashboardViewModel(new DashboardRepository());


        taskViewModel.getTaskCount(id, new ResponseCallback() {
            @Override
            public void onSuccess(Object data, String message) {
                taskcount.setText(String.valueOf(data));
            }

            @Override
            public void onError(String message) {

            }
        });

        return view;
    }
}