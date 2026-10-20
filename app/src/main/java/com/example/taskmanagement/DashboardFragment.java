package com.example.taskmanagement;

import static android.widget.GridLayout.VERTICAL;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.taskmanagement.callback.ResponseCallback;
import com.example.taskmanagement.databinding.FragmentDashboardBinding;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;


public class DashboardFragment extends Fragment  {
    FragmentDashboardBinding  fragmentDashboardBinding;
ActionBarDrawerToggle toggle;
NavigationView navigation;
DashboardAdapter dashboardAdapter;
List<Status>statusList=new ArrayList<>();
DashboardViewModel dashboardViewModel;
StatusViewModel statusViewModel;
    public DashboardFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentDashboardBinding=FragmentDashboardBinding.inflate(inflater,container,false);

        ImageView filterBtn=requireActivity().findViewById(R.id.filterBtn);
fragmentDashboardBinding.dashboardrecyler.setHasFixedSize(true);
fragmentDashboardBinding.dashboardrecyler.setLayoutManager(new GridLayoutManager(getContext(),2));
dashboardAdapter=new DashboardAdapter(getContext(),statusList);
fragmentDashboardBinding.dashboardrecyler.setAdapter(dashboardAdapter);
statusViewModel=new StatusViewModel(new StatusRepository());
filterBtn.setVisibility(View.GONE);
       dashboardViewModel=new DashboardViewModel(new DashboardRepository());
       dashboardViewModel.getTotalTask(new ResponseCallback<Integer>() {
           @Override
           public void onSuccess(Integer data, String message) {
               fragmentDashboardBinding.taskcount.setText(String.valueOf(data));
           }

           @Override
           public void onError(String message) {

           }
       });
       dashboardViewModel.getTotalUser(new ResponseCallback<Integer>() {
           @Override
           public void onSuccess(Integer data, String message) {
               fragmentDashboardBinding.usercount.setText(String.valueOf(data));
           }

           @Override
           public void onError(String message) {

           }
       });


        statusViewModel.getList(new ResponseCallback<List<Status>>() {
            @Override
            public void onSuccess(List<Status> data, String message) {

                statusList.clear();
                statusList.addAll(data);
                dashboardAdapter.notifyDataSetChanged();

                for (Status status : data) {

                    dashboardViewModel.getStatus(
                            status.getId(),
                            new ResponseCallback<Integer>() {
                                @Override
                                public void onSuccess(Integer count, String message) {

                                    dashboardAdapter.setTaskCount(status.getId(), count);
                                }

                                @Override
                                public void onError(String message) {

                                }
                            }
                    );
                }
            }

            @Override
            public void onError(String message) {
            }
        });


        return fragmentDashboardBinding.getRoot();
    }

}