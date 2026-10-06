package com.example.taskmanagement;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.taskmanagement.callback.ResponseCallback;
import com.example.taskmanagement.databinding.FragmentAddCategoryBinding;

import java.util.ArrayList;
import java.util.List;


public class AddCategoryFragment extends Fragment {
FragmentAddCategoryBinding binding;
CategoryViewModel categoryViewModel;

    public AddCategoryFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding=FragmentAddCategoryBinding.inflate(inflater,container,false);
        categoryViewModel=new CategoryViewModel(new CategoryRepository());
        binding.addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.loader.setVisibility(View.VISIBLE);
                String category=binding.category.getEditableText().toString();
                categoryViewModel.addCategory(category, new ResponseCallback<Category>() {
                    @Override
                    public void onSuccess(Category data, String message) {
                        binding.loader.setVisibility(View.GONE);
                        binding.category.setText("");
                    }

                    @Override
                    public void onError(String message) {

                    }
                });
            }
        });
        return binding.getRoot();
    }
}