package com.example.taskmanagement;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.taskmanagement.callback.ResponseCallback;
import com.example.taskmanagement.databinding.FragmentAddStatusBinding;

import java.util.ArrayList;
import java.util.List;


public class AddStatusFragment extends Fragment {
    FragmentAddStatusBinding binding;
    StatusViewModel statusViewModel;
    List<Status> statusList = new ArrayList<>();
    String id;
    public AddStatusFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAddStatusBinding.inflate(inflater, container, false);
        MyBaseActivity activity = (MyBaseActivity) requireActivity();
        statusViewModel = new StatusViewModel(new StatusRepository());
        activity.setToolbarText("Add Status");
        Bundle bundle = getArguments();
        if (bundle!=null){
            id = bundle.getString("Id");
            String name = bundle.getString("Status");
            binding.status.setText(name);
        }


        binding.addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.loader.setVisibility(View.VISIBLE);
                String status = binding.status.getEditableText().toString();
                if (id != null) {
                    statusViewModel.updateStatus(id, status, new ResponseCallback<Status>() {
                        @Override
                        public void onSuccess(Status data, String message) {
                            binding.loader.setVisibility(View.GONE);

                            binding.status.setText(" ");
                        }

                        @Override
                        public void onError(String message) {

                        }
                    });
                } else {
                    statusViewModel.addStatus(status, new ResponseCallback<Status>() {
                        @Override
                        public void onSuccess(Status data, String message) {
                            binding.loader.setVisibility(View.GONE);
                            statusList.clear();
                            statusList.add(data);
                            binding.status.setText(" ");
                        }

                        @Override
                        public void onError(String message) {

                        }
                    });
                }
            }
        });
        return binding.getRoot();
    }
}