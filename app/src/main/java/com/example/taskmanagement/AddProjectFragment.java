package com.example.taskmanagement;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.taskmanagement.callback.ResponseCallback;
import com.example.taskmanagement.databinding.FragmentAddProjectBinding;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;


public class AddProjectFragment extends Fragment {
FragmentAddProjectBinding binding;
ApiTaskViewModel apiTaskViewModel;
AuthViewModel authViewModel;
String selectedDate,Due_Date;
List<Team>teamList=new ArrayList<>();
List<User> userList=new ArrayList<>();
Integer teamid=null;
Integer leadId=null;
    List<Integer>integerList=new ArrayList<>();
    List<Project>projects=new ArrayList<>();
    ProjectViewModel projectViewModel;
    String selectedStatus;
    int selectedTeam,selectedLead,projectId;
    List<Integer>selectedIds=new ArrayList<>();
    public AddProjectFragment() {
        // Required empty public constructor
    }






    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
binding=FragmentAddProjectBinding.inflate(inflater,container,false);
apiTaskViewModel=new ApiTaskViewModel(new ApiTaskRepository());
authViewModel=new AuthViewModel(new AuthRepository());
projectViewModel=new ProjectViewModel(new ProjectRepository());
        binding.startDate.setFocusable(false);
        binding.startDate.setClickable(true);

        binding.endDate.setFocusable(false);
        binding.endDate.setClickable(true);
        Bundle bundle=getArguments();
        if (bundle!=null){
            String selectedName=bundle.getString("name");
            binding.name.setText(selectedName);
            String selectedDiscp=bundle.getString("discription");
            binding.discrpt.setText(selectedDiscp);
            selectedStatus=bundle.getString("status");
selectedTeam=bundle.getInt("team");
selectedLead=bundle.getInt("projectLead");
            String firstDate=bundle.getString("startDate");
            binding.startDate.setText(firstDate);
            String lastDate=bundle.getString("endDate");
            binding.endDate.setText(lastDate);
            String kye=bundle.getString("key");
            binding.key.setText(kye);
selectedIds=bundle.getIntegerArrayList("assigness");
projectId=bundle.getInt("projectId");

        }
binding.startDate.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        openDatePicker(true);
    }
});
binding.endDate.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        openDatePicker(false);
    }
});
String[] statusLabels={

        "Planned",
        "Active",
        "On Hold",
        "Completed",
        "Archived"
};
String[] statusValues={
        "planned",
        "active",
        "on_hold",
        "completed",
        "archived"

};
        ArrayAdapter<String>arrayAdapter=new ArrayAdapter<>(requireContext(), R.layout.dropdown,statusLabels);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.statusSpinner.setAdapter(arrayAdapter);
        binding.statusSpinner.setText(statusLabels[0],false);
        binding.statusSpinner.setText(selectedStatus,false);

        apiTaskViewModel.getteam(new ResponseCallback<List<Team>>() {
            @Override
            public void onSuccess(List<Team> data, String message) {
                teamList.clear();
                teamList.addAll(data);
                ArrayAdapter<Team>teamArrayAdapter=new ArrayAdapter<>(requireContext(),R.layout.dropdown,data);
                binding.teamSpinner.setAdapter(teamArrayAdapter);
                for (Team team :teamList){
                    if (team.getId()==selectedTeam){
                        binding.teamSpinner.setText(team.getName(),false);
                    }
                }

            }

            @Override
            public void onError(String message) {

            }
        });
        authViewModel.getCurrentUser(new ResponseCallback<List<User>>() {
            @Override
            public void onSuccess(List<User> data, String message) {
                userList.clear();
                userList.addAll(data);
                ArrayAdapter<User>userArrayAdapter=new ArrayAdapter<>(requireContext(),R.layout.dropdown,data);
                binding.projectSpinner.setAdapter(userArrayAdapter);
                binding.customteam.setUsers(data);
                binding.customteam.setSelectedUserIds(selectedIds);
for (User user:userList){
    if (user.getId()==selectedLead){
        binding.projectSpinner.setText(user.getName(),false);
    }
}
            }

            @Override
            public void onError(String message) {

            }
        });

binding.saveBtn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        String name = binding.name.getEditableText().toString();
        String key = binding.key.getEditableText().toString().trim().toUpperCase();
        String discript = binding.discrpt.getEditableText().toString();
        String team = binding.teamSpinner.getEditableText().toString();
        String selectedStatus = binding.statusSpinner.getText().toString();
        integerList= binding.customteam.getSelectedUserIds();
        binding.loader.setVisibility(View.VISIBLE);
        Log.d("PROJECT_MEMBERS", "Selected Member IDs: " + integerList);
        int position = arrayAdapter.getPosition(selectedStatus);

        if (position == -1) {
            Toast.makeText(getContext(), "Please select status", Toast.LENGTH_SHORT).show();
            return;
        }

        String value = statusValues[position];

        Log.d("PROJECT_STATUS", "Selected Label: " + selectedStatus);
        Log.d("PROJECT_STATUS", "Selected Value: " + value);

        for (Team team1 : teamList) {
            if (team1.getName().equals(team)) {
                teamid = team1.getId();
                break;
            }

        }
        String lead = binding.projectSpinner.getEditableText().toString();
        for (User user : userList) {
            if (user.getName().equals(lead)) {
                leadId = user.getId();
                break;
            }

        }
        ProjectRequest project = new ProjectRequest();
        project.setName(name);
        project.setKey(key);
        project.setDescription(discript);
        project.setStart_date(selectedDate);
        project.setTarget_end_date(Due_Date);
        project.setLead_user_id(leadId);
        project.setTeam_id(teamid);
        project.setStatus(value);
        project.setMember_ids(integerList);

        binding.saveBtn.setVisibility(View.GONE);
if (projectId!=0){
    projectViewModel.updateProject(projectId, project, new ResponseCallback<Project>() {
        @Override
        public void onSuccess(Project data, String message) {
            binding.loader.setVisibility(View.GONE);
            Toast.makeText(getContext(), "Project Updated", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onError(String message) {

        }
    });
}else {
    projectViewModel.creatProject(project,new ResponseCallback<ProjectResponse>() {
        @Override
        public void onSuccess(ProjectResponse data, String message) {
            binding.loader.setVisibility(View.GONE);
            binding.saveBtn.setVisibility(View.VISIBLE);
            Toast.makeText(getContext(), "Project Created Successfully", Toast.LENGTH_SHORT).show();
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
    private void openDatePicker(boolean isStartDate) {
        Calendar calendar = Calendar.getInstance();

        DatePickerDialog dialog = new DatePickerDialog(
                requireContext(),
                (datePicker, year, month, day) -> {
                    String date = String.format(
                            Locale.US,
                            "%04d-%02d-%02d",
                            year, month + 1, day
                    );

                    if (isStartDate) {
                        selectedDate = date;
                        binding.startDate.setText(date);
                    } else {
                        Due_Date = date;
                        binding.endDate.setText(date);
                    }

                    Log.d("TASK_DATE", "Start date = " + selectedDate);
                    Log.d("TASK_DATE", "Due date = " + Due_Date);
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );

        dialog.show();
    }
    @Override
    public void onResume() {
        super.onResume();

        ((AdminActivity) requireActivity())
                .setToolbarText("Create New Project");
    }
}