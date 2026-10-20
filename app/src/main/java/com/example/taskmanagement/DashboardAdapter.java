package com.example.taskmanagement;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskmanagement.databinding.CardItemBinding;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DashboardAdapter extends RecyclerView.Adapter<DashboardViewHolder> {
    Context context;
    List<Status>statusList;
    private final Map<String, Integer> taskCounts = new HashMap<>();

    public DashboardAdapter(Context context, List<Status> statusList) {
        this.context = context;
        this.statusList = statusList;
    }
public void setTaskCount(String statusID,int count){
        taskCounts.put(statusID,count);
        notifyDataSetChanged();
}
    @NonNull
    @Override
    public DashboardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        CardItemBinding binding=CardItemBinding.inflate(inflater,parent,false);

        return new DashboardViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull DashboardViewHolder holder, int position) {
Status status=statusList.get(position);
holder.binding.statusName.setText(status.getName());

        int count = taskCounts.getOrDefault(status.getId(), 0);

        holder.binding.statusTaskCount.setText(String.valueOf(count));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TaskListFragment taskListFragment=new TaskListFragment();
                Bundle bundle=new Bundle();
                bundle.putString("statusId",status.getId());
                taskListFragment.setArguments(bundle);


                androidx.fragment.app.FragmentActivity activity =
                        (androidx.fragment.app.FragmentActivity) context;
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,taskListFragment).addToBackStack(null).commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return statusList.size();
    }
}
