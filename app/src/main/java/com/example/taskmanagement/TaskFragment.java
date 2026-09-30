package com.example.taskmanagement;

import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.taskmanagement.callback.ResponseCallback;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

public class TaskFragment extends Fragment {

TextInputLayout title,discription;
Button savebtn;

TaskViewModel taskViewModel;
LinearLayout Loader;
USerViewModel uSerViewModel;
List<User>userList=new ArrayList<>();


private ActivityResultLauncher<String>gallerLauncher;
private ActivityResultLauncher<Uri> cameraLauncher;
private Uri cameraUri;
CustomImagView customImagView;
CustomUserView customUser;
    public TaskFragment() {

    }





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_task,container,false);
        title=view.findViewById(R.id.title);
        Loader=view.findViewById(R.id.Loader);
        discription=view.findViewById(R.id.discription);
customImagView=view.findViewById(R.id.customImageView);
        customUser=view.findViewById(R.id.customUser);
        savebtn=view.findViewById(R.id.savebtn);
        taskViewModel=new TaskViewModel(new TaskRepository());
        uSerViewModel=new USerViewModel(new UserRepository());
       // AdminActivity mainActivity=(AdminActivity) requireActivity();
      //  mainActivity.toolbar1.setTitle("Creat Task");
        cameraLauncher=registerForActivityResult(new ActivityResultContracts.TakePicture(),result ->{
            if (result&&cameraUri!=null){
                customImagView.uriList.add(cameraUri.toString());
                customImagView.imageAdpter.notifyDataSetChanged();
            }
        } );
        customImagView.setOnCameraClick(() -> {

            ContentValues values = new ContentValues();

            values.put(
                    MediaStore.Images.Media.DISPLAY_NAME,
                    "task_" + System.currentTimeMillis() + ".jpg"
            );

            values.put(
                    MediaStore.Images.Media.MIME_TYPE,
                    "image/jpeg"
            );

            cameraUri = requireContext()
                    .getContentResolver()
                    .insert(
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                            values
                    );

            if (cameraUri != null) {
                cameraLauncher.launch(cameraUri);
            }
        });
        gallerLauncher = registerForActivityResult(
                new ActivityResultContracts.GetMultipleContents(),
                uris -> {

                    if (uris != null) {

                        customImagView.uriList.clear();
                        for (Uri uri:uris){
                            customImagView.uriList.add(uri.toString());
                        }


                        customImagView.imageAdpter.notifyDataSetChanged();
                    }
                }
        );
        customImagView.setOnGallerClick(new CustomImagView.OnGallerClick() {
            @Override
            public void onClick() {
                gallerLauncher.launch("image/*");
            }
        });
        uSerViewModel.getUser(new ResponseCallback<List<User>>() {
            @Override
            public void onSuccess(List<User> data, String message) {
                userList.clear();
                customUser.userList.addAll(data);

            }

            @Override
            public void onError(String message) {

            }
        });

        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title1=title.getEditText().getText().toString();
                String discpt=discription.getEditText().getText().toString();

                if (title1.isEmpty()){
                    Toast.makeText(getContext(), " Please Create title ", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (discpt.isEmpty()){
                    Toast.makeText(getContext(), " Please Create discription ", Toast.LENGTH_SHORT).show();
                    return;
                }
                Loader.setVisibility(View.VISIBLE);

                taskViewModel.saveData(title1, discpt, customUser.list,customImagView.uriList,new ResponseCallback<Task>() {
                    @Override
                    public void onSuccess(Task data, String message) {
                        Loader.setVisibility(View.GONE);
                        title.getEditText().setText("");
                        discription.getEditText().setText("");
                        customUser.selecteduser.clear();
                        customImagView.uriList.clear();
                        Toast.makeText(getContext(), "Task is Created", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onError(String message) {
                        Loader.setVisibility(View.GONE);
                    }
                });
            }
        });
        return view;

    }
}