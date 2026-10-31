package com.example.taskmanagement;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskmanagement.databinding.WorkloadItemBinding;

import java.util.List;

public class WorkLoadAdapter extends RecyclerView.Adapter<WorkLoadViewHolder> {
    Context context;
    List<TeamWorkLoad> teamWorkLoadList;

    public WorkLoadAdapter(Context context, List<TeamWorkLoad> teamWorkLoadList) {
        this.context = context;
        this.teamWorkLoadList = teamWorkLoadList;
    }

    @NonNull
    @Override
    public WorkLoadViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        WorkloadItemBinding  binding=WorkloadItemBinding.inflate(inflater,parent,false);
        return new WorkLoadViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull WorkLoadViewHolder holder, int position) {
TeamWorkLoad teamWorkLoad=teamWorkLoadList.get(position);
holder.binding.activeTask.setText(String.valueOf(teamWorkLoad.getActive_tasks_count()));
holder.binding.title.setText(teamWorkLoad.getName());
holder.binding.estimatHour.setText(String.valueOf(teamWorkLoad.getEstimated_hours()));
holder.binding.capacityHour.setText(String.valueOf(teamWorkLoad.getCapacity_status()));
holder.binding.compltTask.setText(String.valueOf(teamWorkLoad.getCompleted_tasks_count()));
holder.binding.dueTask.setText(String.valueOf(teamWorkLoad.getOverdue_tasks_count()));
holder.binding.capacityHour.setText(teamWorkLoad.getCapacity_status());
    }

    @Override
    public int getItemCount() {
        return teamWorkLoadList.size();
    }
}
