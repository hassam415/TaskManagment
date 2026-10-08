package com.example.taskmanagement;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.taskmanagement.callback.ResponseCallback;
import com.example.taskmanagement.databinding.FragmentPeriorityBinding;

import java.util.ArrayList;
import java.util.List;


public class PeriorityFragment extends Fragment {
FragmentPeriorityBinding binding;
PeriorityViewModel periorityViewModel;
PeriorityAdapter periorityAdapter;
List<Periority>periorities=new ArrayList<>();
    public PeriorityFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding=FragmentPeriorityBinding.inflate(inflater,container,false);
        periorityViewModel=new PeriorityViewModel(new PeriorityRepository());
        binding.periorityRecyler.setHasFixedSize(true);
        binding.periorityRecyler.setLayoutManager(new LinearLayoutManager(getContext()));
        periorityAdapter=new PeriorityAdapter(getContext(),periorities);
        binding.periorityRecyler.setAdapter(periorityAdapter);
        binding.loader.setVisibility(View.VISIBLE);
        periorityViewModel.getList(new ResponseCallback<List<Periority>>() {
            @Override
            public void onSuccess(List<Periority> data, String message) {
                binding.loader.setVisibility(View.GONE);
periorities.clear();
periorities.addAll(data);
periorityAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(String message) {
                binding.loader.setVisibility(View.GONE);
            }
        });
        periorityAdapter.setOnPeriorityClick(new PeriorityAdapter.OnPeriorityClick() {
            @Override
            public void OnClick(Periority periority) {
                AddPeriorityFragment fragment=new AddPeriorityFragment();
                Bundle bundle=new Bundle();
                bundle.putString("Id",periority.getId());
                bundle.putString("periority",periority.getName());
                fragment.setArguments(bundle);
                getParentFragmentManager().beginTransaction().replace(R.id.fragmentContainer,fragment).addToBackStack(null).commit();
            }
        });
        binding.addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getParentFragmentManager().beginTransaction().replace(R.id.fragmentContainer,new AddPeriorityFragment()).addToBackStack(null).commit();
            }
        });

        return binding.getRoot();
    }
}