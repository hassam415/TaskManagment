package com.example.taskmanagement;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskmanagement.databinding.TaskDetailBinding;

import java.util.List;

public class TaskDetailAdapter extends RecyclerView.Adapter<TaskDetailViewHolder> {
    Context context;
    List<Task>taskList;

    public TaskDetailAdapter(Context context, List<Task> taskList) {
        this.context = context;
        this.taskList = taskList;
    }

    @NonNull
    @Override
    public TaskDetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        TaskDetailBinding binding=TaskDetailBinding.inflate(inflater,parent,false);
        return new TaskDetailViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskDetailViewHolder holder, int position) {
Task task=taskList.get(position);
holder.binding.title.setText(task.getTitle());
holder.binding.discription.setText(task.getDiscription());
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }
}
