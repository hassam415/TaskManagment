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

import com.example.taskmanagement.databinding.TeamItemsBinding;

import java.util.ArrayList;
import java.util.List;

public class TeamAdapter extends RecyclerView.Adapter<TeamViewHolder> {
    Context context;
    List<Team>teams;
    OnDeletBtnClick onDeletBtnClick;

    public void setOnDeletBtnClick(OnDeletBtnClick onDeletBtnClick) {
        this.onDeletBtnClick = onDeletBtnClick;
    }

    public TeamAdapter(Context context, List<Team> teams) {
        this.context = context;
        this.teams = teams;
    }
    public interface OnDeletBtnClick{
        void onClick(int id,int position);
    }

    @NonNull
    @Override
    public TeamViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        TeamItemsBinding binding=TeamItemsBinding.inflate(inflater,parent,false);
        return new TeamViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull TeamViewHolder holder, int position) {
Team team=teams.get(position);
holder.binding.teamName.setText(team.getName());
holder.binding.deparment.setText(team.getDepartment().getName());
holder.binding.details.setText(team.getDescription());
holder.binding.teamLead.setText(team.getLead().getName());
holder.binding.optionbtn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        PopupMenu menu=new PopupMenu(context,holder.binding.optionbtn);
        menu.getMenuInflater().inflate(R.menu.task_update,menu.getMenu());
        if (context instanceof FragmentActivity) {

            FragmentActivity activity = (FragmentActivity) context;

            FragmentManager fragmentManager =
                    activity.getSupportFragmentManager();
            menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    if (item.getItemId()==R.id.delete){
                        int position=holder.getPosition();
                        onDeletBtnClick.onClick(team.getId(),position);
                    }
                    if (item.getItemId()==R.id.update){
                        AddTeamFragment addTeamFragment=new AddTeamFragment();
                        Bundle  bundle=new Bundle();
                        bundle.putString("name", team.getName());
                        bundle.putString("discription", team.getDescription());
                        bundle.putInt("teamId",team.getId());

                        bundle.putInt("leadId", team.getLead().getId());

                        bundle.putInt("departmentID",team.getDepartment().getId());
                        ArrayList<Integer>integers=new ArrayList<>();
                        for (User user:team.getMembers()){
                            integers.add(user.getId() );

                        }
                        bundle.putIntegerArrayList("membersId",integers);
                        addTeamFragment.setArguments(bundle);
                        fragmentManager.beginTransaction().replace(R.id.fragmentContainer,addTeamFragment).addToBackStack(null).commit();
                    }
                    return true;
                }
            });
        }
        menu.show();

    }
});
    }

    @Override
    public int getItemCount() {
        return teams.size();
    }
}
