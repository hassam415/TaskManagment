package com.example.taskmanagement;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.taskmanagement.callback.ResponseCallback;
import com.example.taskmanagement.databinding.FragmentCategoryBinding;

import java.util.ArrayList;
import java.util.List;


public class CategoryFragment extends Fragment {
    FragmentCategoryBinding binding;
    CategoryAdapter categoryAdapter;
    List<Category>categories=new ArrayList<>();
    CategoryViewModel categoryViewModel;
    public CategoryFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
binding=FragmentCategoryBinding.inflate(inflater,container,false);
categoryViewModel=new CategoryViewModel(new CategoryRepository());
binding.categoryRecyler.setHasFixedSize(true);
binding.categoryRecyler.setLayoutManager(new LinearLayoutManager(getContext()));
categoryAdapter=new CategoryAdapter(getContext(),categories);
binding.categoryRecyler.setAdapter(categoryAdapter);
binding.btn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        getParentFragmentManager().beginTransaction().replace(R.id.fragmentContainer,new AddCategoryFragment()).addToBackStack(null).commit();
    }
});
binding.loader.setVisibility(View.VISIBLE);
categoryViewModel.getList(new ResponseCallback<List<Category>>() {
    @Override
    public void onSuccess(List<Category> data, String message) {
        binding.loader.setVisibility(View.GONE);
        categories.clear();
        categories.addAll(data);
        categoryAdapter.notifyDataSetChanged();
    }

    @Override
    public void onError(String message) {
binding.loader.setVisibility(View.GONE);
    }
});
categoryAdapter.setOnCategoryClick(new CategoryAdapter.OnCategoryClick() {
    @Override
    public void OnClick(Category category) {
        AddCategoryFragment fragment=new AddCategoryFragment();
        Bundle bundle=new Bundle();
        bundle.putString("Id",category.getId());
        bundle.putString("Category",category.getName());
        fragment.setArguments(bundle);
        getParentFragmentManager().beginTransaction().replace(R.id.fragmentContainer,fragment).addToBackStack(null).commit();
    }
});
        return binding.getRoot();
    }
}