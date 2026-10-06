package com.example.taskmanagement;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.taskmanagement.callback.ResponseCallback;
import com.example.taskmanagement.databinding.FragmentStatusBinding;

import java.util.ArrayList;
import java.util.List;


public class StatusFragment extends Fragment {
FragmentStatusBinding binding;
StatusAdapter statusAdapter;
StatusViewModel statusViewModel;
List<Status>statusList=new ArrayList<>();
    public StatusFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding=FragmentStatusBinding.inflate(inflater,container,false);
        binding.plusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
getParentFragmentManager().beginTransaction().replace(R.id.fragmentContainer,new AddStatusFragment()).addToBackStack(null).commit();
            }
        });
        binding.statusrecyler.setHasFixedSize(true);
        binding.statusrecyler.setLayoutManager(new LinearLayoutManager(getContext()));
        statusAdapter=new StatusAdapter(getContext(),statusList);
        binding.statusrecyler.setAdapter(statusAdapter);
        statusViewModel=new StatusViewModel(new StatusRepository());
        statusViewModel.getList(new ResponseCallback<List<Status>>() {
            @Override
            public void onSuccess(List<Status> data, String message) {
                statusList.clear();
                statusList.addAll(data);
                statusAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(String message) {

            }
        });
        statusAdapter.setOnThreeDotCLick(new StatusAdapter.OnThreeDotCLick() {
            @Override
            public void onClick(Status status) {
                AddStatusFragment fragment=new AddStatusFragment();
                 Bundle bundle=new Bundle();
                 bundle.putString("Id",status.getId());
                 bundle.putString("Status",status.getName());

                 fragment.setArguments(bundle);
                 getParentFragmentManager().beginTransaction().replace(R.id.fragmentContainer,fragment).addToBackStack(null).commit();
            }
        });

        return binding.getRoot();
    }
}