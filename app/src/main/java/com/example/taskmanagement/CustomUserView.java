package com.example.taskmanagement;

import android.app.AlertDialog;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskmanagement.callback.ResponseCallback;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class CustomUserView extends LinearLayout {
    ImageButton plus_btn;
FirebaseFirestore fs;
    List<User> userList=new ArrayList<>();
    List<User>selecteduser=new ArrayList<>();
    RecyclerView  userrecyler;
    List<String >list=new ArrayList<>();
    taskitemAdapter taskitemAdapter;
    TaskViewModel taskViewModel;
LinearLayout loader;

    public CustomUserView(Context context) {
        super(context);
        init(context);
    }

    public CustomUserView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CustomUserView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public CustomUserView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }
    public void showUsersByIds(List<String> userIds) {

        selecteduser.clear();

        for (String userId : userIds) {

            fs.collection("User")
                    .document(userId)
                    .get()
                    .addOnSuccessListener(documentSnapshot -> {

                        if (documentSnapshot.exists()) {

                            User user = documentSnapshot.toObject(User.class);

                            if (user != null) {
                                selecteduser.add(user);
                                taskitemAdapter.notifyDataSetChanged();
                            }
                        }
                    })
                    .addOnFailureListener(e -> {
                        Log.e("USER_ERROR", e.getMessage());
                    });
        }
    }
    public void setview(){
        plus_btn.setVisibility(View.GONE);
        taskitemAdapter.RemoveDeletebtn();
    }
    public void showLoader(){
        loader.setVisibility(View.VISIBLE);
    }
    public void hideLoader(){
        loader.setVisibility(View.GONE);
    }
    public void init(Context context){
        setOrientation(VERTICAL);
        LayoutInflater.from(context).inflate(R.layout.user_view,this,true);
        fs=FirebaseFirestore.getInstance();
        plus_btn=findViewById(R.id.plus_btn);
        loader=findViewById(R.id.loader);
        taskViewModel=new TaskViewModel(new TaskRepository());
        userrecyler=findViewById(R.id.userrecyler);
        userrecyler.setHasFixedSize(true);
        userrecyler.setLayoutManager(new LinearLayoutManager(context));
        taskitemAdapter =new taskitemAdapter(context,selecteduser);
        userrecyler.setAdapter(taskitemAdapter);
taskitemAdapter.setOnDelet(new taskitemAdapter.OnDelet() {
    @Override
    public void Ondelet(int position) {
        list.remove(position);
        selecteduser.remove(position);
        taskitemAdapter.notifyDataSetChanged();
    }
});
plus_btn.setOnClickListener(new OnClickListener() {
    @Override
    public void onClick(View v) {

        String[]name=new String[userList.size()];
        boolean []check=new boolean[userList.size()];

        for (int i=0;i<userList.size();i++){
            User user=userList.get(i);
            name[i]=userList.get(i).getName();
            if (selecteduser.contains(user)) {
                check[i] = true;
            }else {
                check[i]=false;
            }
        }

        new AlertDialog.Builder(getContext()).setTitle("Assign User").setMultiChoiceItems(name,check,(dialog, which, isChecked) -> {
            User user=userList.get(which);
            String uid=user.getUuid();

            Log.d("USER_TEST", "Name = " + user.getName());

            Log.d("USER_TEST", "UUID = " + uid);
if (isChecked){
if (!selecteduser.contains(user)){
    selecteduser.add(user);
    list.add(user.getUuid());
}
}else {
    selecteduser.remove(user);
}
            taskitemAdapter.notifyDataSetChanged();

        }).setNegativeButton("Cancel",null).setPositiveButton("ok",null).show();
    }
});
    }
}
