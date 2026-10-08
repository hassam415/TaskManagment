package com.example.taskmanagement;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.taskmanagement.callback.ResponseCallback;
import com.example.taskmanagement.databinding.FragmentAddPeriorityBinding;

import java.util.ArrayList;
import java.util.List;


public class AddPeriorityFragment extends Fragment {
FragmentAddPeriorityBinding binding;
PeriorityViewModel periorityViewModel;
String id;
List<Periority>periorities=new ArrayList<>();
    public AddPeriorityFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding=FragmentAddPeriorityBinding.inflate(inflater,container,false);
        periorityViewModel=new PeriorityViewModel(new PeriorityRepository());
        Bundle bundle=getArguments();
        if (bundle!=null){
          id=bundle.getString("Id");
          String periority=bundle.getString("periority");
          binding.periority.setText(periority);
        }

        binding.addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String periority=binding.periority.getEditableText().toString();
                if (id!=null){
                    periorityViewModel.updatePeriority(id, periority, new ResponseCallback<Periority>() {
                        @Override
                        public void onSuccess(Periority data, String message) {
                            binding.loader.setVisibility(View.GONE);
                            periorities.clear();
                            periorities.add(data);
                            getParentFragmentManager().popBackStack();
                        }

                        @Override
                        public void onError(String message) {
                            binding.loader.setVisibility(View.GONE);
                        }
                    });
                }else {
                    periorityViewModel.addPeriority(periority, new ResponseCallback<Periority>() {
                        @Override
                        public void onSuccess(Periority data, String message) {
                            binding.loader.setVisibility(View.GONE);
                            getParentFragmentManager().popBackStack();
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