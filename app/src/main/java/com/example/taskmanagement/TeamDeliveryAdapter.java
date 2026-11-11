package com.example.taskmanagement;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskmanagement.databinding.TeamDeliveryItemBinding;

import java.util.List;

public class TeamDeliveryAdapter extends RecyclerView.Adapter<TeamDeliverVIewHolder> {
    Context context;
    List<TeamBreakdown>teamBreakdowns;

    public TeamDeliveryAdapter(Context context, List<TeamBreakdown> teamBreakdowns) {
        this.context = context;
        this.teamBreakdowns = teamBreakdowns;
    }

    @NonNull
    @Override
    public TeamDeliverVIewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        TeamDeliveryItemBinding binding=TeamDeliveryItemBinding.inflate(inflater,parent,false);
        return new TeamDeliverVIewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull TeamDeliverVIewHolder holder, int position) {
TeamBreakdown teamBreakdown=teamBreakdowns.get(position);
holder.binding.teamName.setText(teamBreakdown.getName());
holder.binding.completedTask.setText(String.valueOf((int)teamBreakdown.getCompleted()));
        holder.binding.activeTask.setText(String.valueOf((int)teamBreakdown.getActive()));
        holder.binding.dueTask.setText(String.valueOf((int)teamBreakdown.getOverdue()));
        holder.binding.slaTime.setText(String.valueOf((int)teamBreakdown.getOn_time_percentage()));
        holder.binding.hours.setText(String.valueOf((int)teamBreakdown.getActual_hours()));
holder.binding.createdTask.setText(String.valueOf((int) teamBreakdown.getTotal_created()));
    }

    @Override
    public int getItemCount() {
        return teamBreakdowns.size();
    }
}
