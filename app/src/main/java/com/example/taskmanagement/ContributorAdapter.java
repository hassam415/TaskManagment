package com.example.taskmanagement;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskmanagement.databinding.ItemContributorBinding;

import java.util.List;

public class ContributorAdapter extends RecyclerView.Adapter<ContributorViewHolder> {
    Context context;
    List<MemberBreakdown>memberBreakdowns;

    public ContributorAdapter(Context context, List<MemberBreakdown> memberBreakdowns) {
        this.context = context;
        this.memberBreakdowns = memberBreakdowns;
    }

    @NonNull
    @Override
    public ContributorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        ItemContributorBinding binding=ItemContributorBinding.inflate(inflater,parent,false);
        return new ContributorViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ContributorViewHolder holder, int position) {
MemberBreakdown memberBreakdown=memberBreakdowns.get(position);
holder.binding.member.setText(memberBreakdown.getName());
holder.binding.jobtittle.setText(memberBreakdown.getJob_title());
holder.binding.completedTask.setText(String.valueOf((int) memberBreakdown.getCompleted_count()));
        holder.binding.activeTask.setText(String.valueOf((int) memberBreakdown.getActive_count()));
        holder.binding.assigneTask.setText(String.valueOf((int) memberBreakdown.getAssigned_count()));
        holder.binding.dueTask.setText(String.valueOf((int) memberBreakdown.getOverdue_count()));
        holder.binding.slaTime.setText(String.valueOf((int) memberBreakdown.getOn_time_rate()));
        holder.binding.hours.setText(String.valueOf((int) memberBreakdown.getLogged_hours()));
    }

    @Override
    public int getItemCount() {
        return memberBreakdowns.size();
    }
}
