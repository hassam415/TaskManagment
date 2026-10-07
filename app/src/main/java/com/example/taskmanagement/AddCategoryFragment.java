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
String id;
String category;

    public AddCategoryFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding=FragmentAddCategoryBinding.inflate(inflater,container,false);
        categoryViewModel=new CategoryViewModel(new CategoryRepository());
        Bundle bundle=getArguments();
        if (bundle!=null){
            id=bundle.getString("Id");
            category=bundle.getString("Category");
            binding.category.setText(category);
        }
        binding.addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.loader.setVisibility(View.VISIBLE);
                String category=binding.category.getEditableText().toString();
                if (id!=null){
                    categoryViewModel.updateCategory(id, category, new ResponseCallback<Category>() {
                        @Override
                        public void onSuccess(Category data, String message) {
                            binding.loader.setVisibility(View.GONE);
                            binding.category.setText("");
                            getParentFragmentManager().popBackStack();
                        }

                        @Override
                        public void onError(String message) {

                        }
                    });
                }else {
                    categoryViewModel.addCategory(category, new ResponseCallback<Category>() {
                        @Override
                        public void onSuccess(Category data, String message) {
                            binding.loader.setVisibility(View.GONE);
                            binding.category.setText("");
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