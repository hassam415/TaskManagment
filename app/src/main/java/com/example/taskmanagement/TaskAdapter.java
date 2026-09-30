package com.example.taskmanagement;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskmanagement.databinding.TaskItemBinding;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskViewHolder> {
    Context context;
    List<Task> taskList;
    OnTaskClick onTaskClick;

    public TaskAdapter(Context context, List<Task> taskList) {
        this.context = context;
        this.taskList = taskList;
    }

    public void setOnTaskClick(OnTaskClick onTaskClick) {
        this.onTaskClick = onTaskClick;
    }

    public interface OnTaskClick {
        void onClick(int position);
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        TaskItemBinding binding = TaskItemBinding.inflate(inflater, parent, false);

        return new TaskViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Task task = taskList.get(position);
        holder.binding.title.setText(task.getTitle());

        holder.binding.discription.setText(task.discription);
        holder.binding.status.setText(task.getStatus());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onTaskClick.onClick(position);
            }
        });
        String status=task.getStatus();
        if ("Task Assigned".equals(status)){
holder.binding.status.setChipBackgroundColorResource(R.color.TaskAssign);
        }
        if ("To do".equals(status)){
            holder.binding.status.setChipBackgroundColorResource(R.color.Todo);
        }
        if ("In Review".equals(status)){
            holder.binding.status.setChipBackgroundColorResource(R.color.InReview);
        }
        if ("In Progess".equals(status)){
            holder.binding.status.setChipBackgroundColorResource(R.color.InProgress);
        }
        if ("Completed".equals(status)){
            holder.binding.status.setChipBackgroundColorResource(R.color.Completed);
        }
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }
}
