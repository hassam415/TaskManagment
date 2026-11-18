package com.example.taskmanagement;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.taskmanagement.callback.ResponseCallback;
import com.example.taskmanagement.databinding.FragmentUpdateProfileBinding;
import com.example.taskmanagement.response.AuthResponse;


public class UpdateProfileFragment extends Fragment {
FragmentUpdateProfileBinding binding;
String name,job;
AuthViewModel authViewModel;

    public UpdateProfileFragment() {
        // Required empty public constructor
    }





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding=FragmentUpdateProfileBinding.inflate(inflater,container,false);
        authViewModel=new AuthViewModel(new AuthRepository());
        Bundle bundle=getArguments();
        if (bundle!=null){
            name=bundle.getString("name");
            job=bundle.getString("jobTitle");
        }
        binding.updatename.setText(name);
        binding.updatejobtitle.setText(job);
        binding.updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameupdate=binding.updatename.getEditableText().toString();
                String jobUpdate=binding.updatejobtitle.getEditableText().toString();
                UpdateProfileRequest updateProfileRequest=new UpdateProfileRequest();
                updateProfileRequest.setName(nameupdate);
                updateProfileRequest.setJob_title(jobUpdate);
                showLoader();
                authViewModel.updateCurrentMe(updateProfileRequest, new ResponseCallback<AuthResponse>() {
                    @Override
                    public void onSuccess(AuthResponse data, String message) {
                        HideLoader();
                        Toast.makeText(getContext(), "Updated", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onError(String message) {
HideLoader();
                    }
                });
            }
        });

        return binding.getRoot();

    }
    @Override
    public void onResume() {
        super.onResume();
        ((AdminActivity) requireActivity()).setToolbarText(getResources().getString(R.string.Account_Setting));
    }
    private void showLoader(){
        binding.updateBtn.setText("");
        binding.updateLoader.setVisibility(View.VISIBLE);
        binding.updateBtn.setEnabled(false);
    }
    private void HideLoader(){
        binding.updateBtn.setText("Update");
        binding.updateLoader.setVisibility(View.GONE);
        binding.updateBtn.setEnabled(true);
    }
}