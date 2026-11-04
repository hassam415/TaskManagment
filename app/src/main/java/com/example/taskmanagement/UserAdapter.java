package com.example.taskmanagement;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskmanagement.databinding.UserItemBinding;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserViewHolder> {

    Context context;
    List<User> userList;
OnUserClick onUserClick;
OnDelectClick onDelectClick;
    public void setOnUserClick(OnUserClick onUserClick) {
        this.onUserClick = onUserClick;
    }

    public void setOnDelectClick(OnDelectClick onDelectClick) {
        this.onDelectClick = onDelectClick;
    }

    public UserAdapter(Context context, List<User> userList) {
        this.context = context;
        this.userList = userList;
    }
    public interface OnUserClick{
        void OnClick(int position);
    }
    public interface  OnDelectClick{
        void OnClick(int  Userid,int position);
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
        holder.binding.address.setText(user.getRole());
        holder.binding.email.setText(user.getJob_title());
        holder.binding.updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu menu=new PopupMenu(context,holder.binding.updateBtn);
                menu.getMenuInflater().inflate(R.menu.task_update,menu.getMenu());
                if (context instanceof FragmentActivity){
                    FragmentActivity activity = (FragmentActivity) context;

                    FragmentManager fragmentManager =
                            activity.getSupportFragmentManager();
                    menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {


                            if (item.getItemId()==R.id.update){
                                UserFragment userFragment=new UserFragment();
                                Bundle bundle=new Bundle();
                                bundle.putInt("Id",user.getId());
                                bundle.putString("name",user.getName());
                                int roleId = -1;

                                if (user.getRoles() != null && !user.getRoles().isEmpty()) {
                                    roleId = user.getRoles().get(0).getId();
                                }

                                bundle.putInt("role", roleId);

                                bundle.putString("email",user.getEmail());
                                bundle.putString("job",user.getJob_title());
                                bundle.putString("password",user.getPassword());
                                bundle.putString("status",user.getStatus());
                                ArrayList<Integer> teamIds = new ArrayList<>();

                                if (user.getTeams() != null) {
                                    for (Team team : user.getTeams()) {
                                        teamIds.add(team.getId());
                                    }
                                }

                                bundle.putIntegerArrayList("team_ids", teamIds);

                                Log.d("API", "onMenuItemClick: " + teamIds);

                                userFragment.setArguments(bundle);
                                fragmentManager.beginTransaction().replace(R.id.fragmentContainer,userFragment).addToBackStack(null).commit();
                            }
                            if (item.getItemId()==R.id.delete){
                                int currentposition=holder.getPosition();
                                onDelectClick.OnClick(user.getId(),currentposition);
                            }
                            return true;
                        }
                    });
                }


                menu.show();
            }
        });
//holder.itemView.setOnClickListener(new View.OnClickListener() {
  //  @Override
  //  public void onClick(View v) {
    //    onUserClick.OnClick(position);
   // }
//});
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }
}
