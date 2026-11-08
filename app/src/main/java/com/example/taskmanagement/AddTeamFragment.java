package com.example.taskmanagement;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.taskmanagement.callback.ResponseCallback;
import com.example.taskmanagement.databinding.FragmentAddTeamBinding;

import java.util.ArrayList;
import java.util.List;

public class AddTeamFragment extends Fragment {

FragmentAddTeamBinding binding;
AuthViewModel authViewModel;
TeamViewModel teamViewModel;
int departmentID,leadId;
List<TeamDepartment>teamDepartments=new ArrayList<>();
List<User>userList=new ArrayList<>();
List<Integer>integerList=new ArrayList<>();
int id,selectedLeadId,selectedDepartID;
List<Integer>selectedIDs=new ArrayList<>();
    public AddTeamFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding=FragmentAddTeamBinding.inflate(inflater,container,false);
        authViewModel=new AuthViewModel(new AuthRepository());
        teamViewModel=new TeamViewModel(new TeamRepository());
        Bundle bundle=getArguments();
        if (bundle!=null){
            String teamName=bundle.getString("name");
            String teamDiscrip=bundle.getString("discription");
            selectedDepartID=bundle.getInt("departmentID");
            selectedLeadId=bundle.getInt("leadId");
            id=bundle.getInt("teamId");
            selectedIDs=bundle.getIntegerArrayList("membersId");
            binding.teamname.setText(teamName);
            binding.discription.setText(teamDiscrip);

        }
        authViewModel.getCurrentUser(new ResponseCallback<List<User>>() {
            @Override
            public void onSuccess(List<User> data, String message) {
                userList.clear();
                userList.addAll(data);
                ArrayAdapter<User>userArrayAdapter=new ArrayAdapter<>(requireContext(),R.layout.dropdown,data);
                binding.teamleadSpinner.setAdapter(userArrayAdapter);
                binding.customUsers.setUsers(data);
                binding.customUsers.setSelectedUserIds(selectedIDs);
                for (User user:userList){
                    if (user.getId()==selectedLeadId){
                        binding.teamleadSpinner.setText(user.getName(),false);
                    }
                }
            }

            @Override
            public void onError(String message) {

            }
        });
        teamViewModel.getDepartment(new ResponseCallback<List<TeamDepartment>>() {
            @Override
            public void onSuccess(List<TeamDepartment> data, String message) {
                teamDepartments.clear();
                teamDepartments.addAll(data);
                ArrayAdapter<TeamDepartment>arrayAdapter=new ArrayAdapter<>(requireContext(),R.layout.dropdown,data);
                binding.departmentSpinner.setAdapter(arrayAdapter);
                for (TeamDepartment department:teamDepartments){
                    if (department.getId()==selectedDepartID){
                        binding.departmentSpinner.setText(department.getName(),false);
                        break;
                    }
                }

            }

            @Override
            public void onError(String message) {

            }
        });
        binding.savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
String name=binding.teamname.getEditableText().toString();
String discription=binding.discription.getEditableText().toString();
String departmentD=binding.departmentSpinner.getEditableText().toString();
integerList=binding.customUsers.getSelectedUserIds();
                Log.d("TEAM_MEMBERS", "Selected Members: " + integerList);
for (TeamDepartment department:teamDepartments){
    if (department.getName().equals(departmentD)){
        departmentID=department.getId();
        break;
    }
}
String leadid=binding.teamleadSpinner.getEditableText().toString();
for (User user:userList){
    if (user.getName().equals(leadid)){
        leadId= user.getId();
        break;
    }
}
TeamRequest team=new TeamRequest();
team.setDepartment_id(departmentID);
team.setName(name);
team.setLead_user_id(leadId);
team.setDescription(discription);
team.setMember_ids(integerList);
binding.loader.setVisibility(View.VISIBLE);
binding.savebtn.setVisibility(View.GONE);
if (id!=0){
    teamViewModel.updateTeam(id, team, new ResponseCallback<Team>() {

        @Override
        public void onSuccess(Team data, String message) {
            binding.loader.setVisibility(View.GONE);
            Toast.makeText(getContext(), "Team Updated", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onError(String message) {

        }
    });
}
else {
    teamViewModel.creatTeam(team, new ResponseCallback<Team>() {
        @Override
        public void onSuccess(Team data, String message) {
            binding.loader.setVisibility(View.GONE);
            Toast.makeText(getContext(), "Team is Created", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onError(String message) {
            Toast.makeText(getContext(), ""+message, Toast.LENGTH_SHORT).show();
        }
    });
}


            }
        });

        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        ((AdminActivity) requireActivity()).setToolbarText("Creat New Team Squad");
    }
}