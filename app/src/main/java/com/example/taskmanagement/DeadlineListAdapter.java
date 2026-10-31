package com.example.taskmanagement;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskmanagement.databinding.MilstoneItmeBinding;

import java.util.List;

public class DeadlineListAdapter extends RecyclerView.Adapter<DeadLineListViewHolder> {
    Context context;
    List<UpcomingDeadLine>upcomingDeadLines;

    public DeadlineListAdapter(Context context, List<UpcomingDeadLine> upcomingDeadLines) {
        this.context = context;
        this.upcomingDeadLines = upcomingDeadLines;
    }

    @NonNull
    @Override
    public DeadLineListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        MilstoneItmeBinding binding=MilstoneItmeBinding.inflate(inflater,parent,false);
        return new DeadLineListViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull DeadLineListViewHolder holder, int position) {
UpcomingDeadLine upcomingDeadLine=upcomingDeadLines.get(position);
holder.binding.taskId.setText(upcomingDeadLine.getReference_code()+" - "+upcomingDeadLine.getPriority());
holder.binding.team.setText(upcomingDeadLine.getTeam().getName());
holder.binding.title.setText(upcomingDeadLine.getTitle());
holder.binding.dueDate.setText(upcomingDeadLine.getDue_date());
    }

    @Override
    public int getItemCount() {
        return upcomingDeadLines.size();
    }
}
