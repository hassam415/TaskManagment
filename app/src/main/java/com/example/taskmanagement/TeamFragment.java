package com.example.taskmanagement;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.taskmanagement.callback.ResponseCallback;
import com.example.taskmanagement.databinding.FragmentTeamBinding;

import java.util.ArrayList;
import java.util.List;


public class TeamFragment extends Fragment {

FragmentTeamBinding binding;
TeamViewModel teamViewModel;
TeamAdapter teamAdapter;
List<Team>teamList=new ArrayList<>();

    public TeamFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding=FragmentTeamBinding.inflate(inflater,container,false);
        teamViewModel=new TeamViewModel(new TeamRepository());
        binding.teamRecyler.setHasFixedSize(true);
        binding.teamRecyler.setLayoutManager(new LinearLayoutManager(getContext()));
        teamAdapter=new TeamAdapter(getContext(),teamList);
        binding.teamRecyler.setAdapter(teamAdapter);
        teamViewModel.getTeamList(new ResponseCallback<List<Team>>() {
            @Override
            public void onSuccess(List<Team> data, String message) {
                teamList.clear();
                teamList.addAll(data);
                teamAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(String message) {

            }
        });
        teamAdapter.setOnDeletBtnClick(new TeamAdapter.OnDeletBtnClick() {
            @Override
            public void onClick(int id, int position) {
                teamViewModel.deletTeam(id, new ResponseCallback<Team>() {
                    @Override
                    public void onSuccess(Team data, String message) {

                        teamList.remove(position);
                        teamAdapter.notifyItemRemoved(position);
                    }

                    @Override
                    public void onError(String message) {

                    }
                });
            }
        });
        binding.teamAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getParentFragmentManager().beginTransaction().replace(R.id.fragmentContainer,new AddTeamFragment()).addToBackStack(null).commit();
            }
        });
        return binding.getRoot();
    }
}