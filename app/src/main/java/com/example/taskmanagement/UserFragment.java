package com.example.taskmanagement;

import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.taskmanagement.callback.ResponseCallback;
import com.example.taskmanagement.databinding.FragmentUserBinding;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class UserFragment extends Fragment {
    FragmentUserBinding binding;

    FirebaseAuth auth;
    USerViewModel uSerViewModel;
    CircleImageView imgView;
    ApiTaskViewModel apiTaskViewModel;
    ArrayAdapter<Role>roleArrayAdapter;
    List<String >statusList=new ArrayList<>();
    AuthViewModel authViewModel;
    List<Integer>selectedTeamId=new ArrayList<>();
    List<Role>roleList=new ArrayList<>();

    private int editUserId = -1;
    private int editRoleId = -1;

    private String editStatus = "";

    private ArrayList<Integer> editTeamIds = new ArrayList<>();
private ActivityResultLauncher<String> cameraLaucnher;
    public UserFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding= FragmentUserBinding.inflate(inflater,container,false);
        apiTaskViewModel=new ApiTaskViewModel(new ApiTaskRepository());
        authViewModel=new AuthViewModel(new AuthRepository());
       // auth = FirebaseAuth.getInstance();

       // uSerViewModel = new USerViewModel(new UserRepository());

     //   imgView = view.findViewById(R.id.imgView);


      //  imgView.setOnClickListener(new View.OnClickListener() {
        //    @Override
          //  public void onClick(View v) {
            //    cameraLaucnher.launch("image/*");
         //   }
     //   });
       // cameraLaucnher=registerForActivityResult(new ActivityResultContracts.GetMultipleContents(),uris ->{
         //   for (Uri uri:uris){
           //     imgView.setImageURI(uri);
         //   }
      //  } );
        Bundle bundle=getArguments();
        if (bundle!=null){
            editUserId=bundle.getInt("Id");
            String name=bundle.getString("name");
            String email=bundle.getString("email");
            String pass=bundle.getString("password");
            editStatus=bundle.getString("status");
            String jobb=bundle.getString("job");
            editRoleId = bundle.getInt("role", -1);
            Log.d("ROLE_DEBUG", "Received Role ID: " + editRoleId);
            binding.userName.getEditText().setText(name);
            binding.email.getEditText().setText(email);
            binding.password.getEditText().setText(pass);
            binding.jobTitle.getEditText().setText(jobb);

            editTeamIds = bundle.getIntegerArrayList("team_ids");

            if (editTeamIds == null) {
                editTeamIds = new ArrayList<>();
            }

        }
        apiTaskViewModel.getRole(new ResponseCallback<List<Role>>() {
            @Override
            public void onSuccess(List<Role> data, String message)
            {
                if (data == null || data.isEmpty()) {
                Toast.makeText(requireContext(),
                        "Role list is empty",
                        Toast.LENGTH_SHORT).show();
                return;

            }
                roleList.clear();
                roleList.addAll(data);
                roleArrayAdapter=new ArrayAdapter<>(requireContext(), R.layout.dropdown,data);
                roleArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                binding.roleSpinner.setAdapter(roleArrayAdapter);
                for (Role role:roleList){
                    if (role.getId()==editRoleId){
                        binding.roleSpinner.setText(role.getName(),false);
                        break;
                    }
                }
            }

            @Override
            public void onError(String message) {

            }
        });
        apiTaskViewModel.getteam(new ResponseCallback<List<Team>>() {
            @Override
            public void onSuccess(List<Team> data, String message) {
                binding.customteam.setteam(data);
                binding.customteam.setSelectedTeamIds(editTeamIds);
                Log.e("SELECT TEAM", "onSuccess: "+editTeamIds );
            }

            @Override
            public void onError(String message) {

            }
        });

        binding.savebtn.setOnClickListener(v -> {
            String name = binding.userName.getEditText().getText().toString();
            String password1 = binding.password.getEditText().getText().toString();
            String email1 = binding.email.getEditText().getText().toString();
            String jobtitle = binding.jobTitle.getEditText().getText().toString();

            String selectedRole = binding.roleSpinner.getText().toString().trim();
            String selectedStatus = binding.statusSpinner.getText().toString().trim();

            if (name.isEmpty() || password1.isEmpty() || email1.isEmpty()
                    || selectedRole.isEmpty() || selectedStatus.isEmpty()) {

                Toast.makeText(requireContext(),
                        "Please fill required fields", Toast.LENGTH_SHORT).show();
                return;
            }

            int roleId = -1;

            for (Role role : roleList) {
                if (role.getName().equalsIgnoreCase(selectedRole)) {
                    roleId = role.getId();
                    break;
                }
            }

            if (roleId == -1) {
                Toast.makeText(requireContext(),
                        "Please select a valid role", Toast.LENGTH_SHORT).show();
                return;
            }

            RequestUser requestUser=new RequestUser();
            requestUser.setName(name);
            requestUser.setPassword(password1);
            requestUser.setEmail(email1);
            requestUser.setJob_title(jobtitle);
            requestUser.setRole_id(roleId);
            requestUser.setStatus(selectedStatus);
            List<Integer>selectedTeamIds=binding.customteam.getSelectedTeamIds();
            requestUser.setTeam_ids(selectedTeamIds);
            binding.loadingcoment.setVisibility(View.VISIBLE);
            binding.savebtn.setVisibility(View.GONE);
            if (editUserId!=0){
                authViewModel.UpdateUser(editUserId, requestUser, new ResponseCallback() {
                    @Override
                    public void onSuccess(Object data, String message) {
                        binding.loadingcoment.setVisibility(View.GONE);
                        Toast.makeText(getContext(), "User Updated", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(String message) {

                    }
                });
            }else {
                authViewModel.creatUser(requestUser, new ResponseCallback() {
                    @Override
                    public void onSuccess(Object data, String message) {
                        Toast.makeText(getContext(), "User Created Succcessfully", Toast.LENGTH_SHORT).show();
                        binding.loadingcoment.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(String message) {

                    }
                });
            }

            //   uSerViewModel.createUser(name, email1, password1, address1, new ResponseCallback<User>() {
            //     @Override
            //   public void onSuccess(User data, String message) {
            //  binding.loadingcoment.setVisibility(View.GONE);

            //   }

            // @Override
            //   public void onError(String message) {

            // }
            //      });
            //   });
        });
        loadStatus();
            return binding.getRoot();
        }

    private void loadStatus() {

        statusList.add("active");
        statusList.add("inactive");
        statusList.add("suspended");
        ArrayAdapter<String >arrayAdapter=new ArrayAdapter<>(requireContext(), R.layout.dropdown,statusList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.statusSpinner.setAdapter(arrayAdapter);
        if (editStatus!=null){
            binding.statusSpinner.setText(editStatus,false);
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        ((AdminActivity) requireActivity()).setToolbarText("Create Organization User");
    }
}
