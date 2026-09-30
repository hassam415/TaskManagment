package com.example.taskmanagement;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskmanagement.databinding.UserDetailBinding;

import java.util.List;

public class UserDetailAdapter extends RecyclerView.Adapter<UserDetailViewHolder> {
    Context context;
    List<User>userList;

    public UserDetailAdapter(Context context, List<User> userList) {
        this.context = context;
        this.userList = userList;
    }

    @NonNull
    @Override
    public UserDetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        UserDetailBinding binding=UserDetailBinding.inflate(inflater,parent,false);
        return new UserDetailViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull UserDetailViewHolder holder, int position) {
User user=userList.get(position);
holder.binding.name.setText(user.getName());
holder.binding.address.setText("Address :"+user.getAddress());
holder.binding.email.setText("Email :"+user.getEmail());
holder.binding.password.setText("Password :"+user.getPassword());
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }
}
