package com.example.taskmanagement;

import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.taskmanagement.callback.ResponseCallback;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

import de.hdodenhof.circleimageview.CircleImageView;


public class UserFragment extends Fragment {
    TextInputLayout user_name, password, email, address;
    Button savebtn;
    FirebaseAuth auth;
    USerViewModel uSerViewModel;
    CircleImageView imgView;
    LinearLayout loadingcoment;
private ActivityResultLauncher<String> cameraLaucnher;
    public UserFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, container, false);
        auth = FirebaseAuth.getInstance();

        user_name = view.findViewById(R.id.user_name);
        loadingcoment = view.findViewById(R.id.loadingcoment);
        password = view.findViewById(R.id.password);
        uSerViewModel = new USerViewModel(new UserRepository());
        email = view.findViewById(R.id.email);
        imgView = view.findViewById(R.id.imgView);

        address = view.findViewById(R.id.address);
        savebtn = view.findViewById(R.id.savebtn);
        imgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cameraLaucnher.launch("image/*");
            }
        });
        cameraLaucnher=registerForActivityResult(new ActivityResultContracts.GetMultipleContents(),uris ->{
            for (Uri uri:uris){
                imgView.setImageURI(uri);
            }
        } );
        savebtn.setOnClickListener(v -> {
            String name = user_name.getEditText().getText().toString();
            String password1 = password.getEditText().getText().toString();
            String email1 = email.getEditText().getText().toString();
            String address1 = address.getEditText().getText().toString();
            loadingcoment.setVisibility(View.VISIBLE);
            uSerViewModel.createUser(name, email1, password1, address1, new ResponseCallback<User>() {
                @Override
                public void onSuccess(User data, String message) {
                    loadingcoment.setVisibility(View.GONE);

                }

                @Override
                public void onError(String message) {

                }
            });
        });

        return view;

    }





}