package com.example.taskmanagement;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.taskmanagement.callback.ResponseCallback;
import com.example.taskmanagement.databinding.FragmentProjectBinding;

import java.util.ArrayList;
import java.util.List;


public class ProjectFragment extends Fragment {
FragmentProjectBinding binding;
ProjectAdapter projectAdapter;
ProjectViewModel projectViewModel;
List<Project>projects=new ArrayList<>();
    public ProjectFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding=FragmentProjectBinding.inflate(inflater,container,false);
        binding.projectRecyler.setHasFixedSize(true);
        binding.projectRecyler.setLayoutManager(new LinearLayoutManager(getContext()));
        projectAdapter=new ProjectAdapter(getContext(),projects);
        binding.projectRecyler.setAdapter(projectAdapter);
        projectViewModel=new ProjectViewModel(new ProjectRepository());
        projectViewModel.getProjectList(new ResponseCallback<List<Project>>() {
            @Override
            public void onSuccess(List<Project> data, String message) {
                projects.clear();
                projects.addAll(data);
                projectAdapter.notifyDataSetChanged();

            }

            @Override
            public void onError(String message) {

            }
        });
        projectAdapter.setOnDeletBtnClick(new ProjectAdapter.OnDeletBtnClick() {
            @Override
            public void OnClick(int Id, int position) {
                projectViewModel.deleteProject(Id, new ResponseCallback<Project>() {
                    @Override
                    public void onSuccess(Project data, String message) {
                        projects.remove(position);
                        projectAdapter.notifyItemRemoved(position);
                    }

                    @Override
                    public void onError(String message) {

                    }
                });
            }
        });

        binding.projectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
getParentFragmentManager().beginTransaction().replace(R.id.fragmentContainer,new AddProjectFragment()).addToBackStack(null).commit();
            }
        });
        return binding.getRoot();
    }
}