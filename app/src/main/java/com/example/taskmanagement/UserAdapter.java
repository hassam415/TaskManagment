package com.example.taskmanagement;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskmanagement.databinding.UserItemBinding;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserViewHolder> {

    Context context;
    List<User> userList;
OnUserClick onUserClick;

    public void setOnUserClick(OnUserClick onUserClick) {
        this.onUserClick = onUserClick;
    }

    public UserAdapter(Context context, List<User> userList) {
        this.context = context;
        this.userList = userList;
    }
    public interface OnUserClick{
        void OnClick(int position);
    }
    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        UserItemBinding binding=UserItemBinding.inflate(inflater,parent,false);
        return new UserViewHolder(binding) ;
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user=userList.get(position);
        holder.binding.name.setText(user.getName());
        holder.binding.address.setText(user.getAddress());
        holder.binding.email.setText(user.getEmail());
holder.itemView.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        onUserClick.OnClick(position);
    }
});
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }
}
