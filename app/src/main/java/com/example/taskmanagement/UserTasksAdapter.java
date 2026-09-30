package com.example.taskmanagement;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskmanagement.databinding.TaskItemBinding;
import com.example.taskmanagement.databinding.UsertaskItemBinding;

import java.util.List;

public class UserTasksAdapter extends RecyclerView.Adapter<UserTasksViewHolder> {
    Context context;
    List<Task>taskList;

    public UserTasksAdapter(Context context, List<Task> taskList) {
        this.context = context;
        this.taskList = taskList;
    }

    @NonNull
    @Override
    public UserTasksViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        UsertaskItemBinding binding=UsertaskItemBinding.inflate(inflater,parent,false);

        return new UserTasksViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull UserTasksViewHolder holder, int position) {
Task  task=taskList.get(position);
holder.binding.title.setText("Title :"+task.getTitle());
holder.binding.discription.setText("Discription :"+task.getDiscription());
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }
}
