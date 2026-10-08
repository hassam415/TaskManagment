package com.example.taskmanagement;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskmanagement.databinding.TaskItemBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

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
FirebaseFirestore.getInstance().collection("Status").document(task.getStatusId()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
    @Override
    public void onSuccess(DocumentSnapshot documentSnapshot) {
        String status=documentSnapshot.getString("name");
        holder.binding.status.setText(status);
    }
}).addOnFailureListener(new OnFailureListener() {
    @Override
    public void onFailure(@NonNull Exception e) {

    }
});


                       if ("Task Assigned".equals(holder.binding.status)) {
                           holder.binding.status
                                    .setChipBackgroundColorResource(R.color.TaskAssign);

                        } else if ("To do".equals(holder.binding.status)) {
                            holder.binding.status
                                    .setChipBackgroundColorResource(R.color.Todo);

                        } else if ("In Review".equals(holder.binding.status)) {
                            holder.binding.status
                                    .setChipBackgroundColorResource(R.color.InReview);

                        } else if ("In Progess".equals(holder.binding.status)) {
                            holder.binding.status
                                    .setChipBackgroundColorResource(R.color.InProgress);

                        } else if ("Completed".equals(holder.binding.status)) {
                            holder.binding.status
                                    .setChipBackgroundColorResource(R.color.Completed);
                        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onTaskClick.onClick(position);
            }
        });

    }


    @Override
    public int getItemCount() {
        return taskList.size();
    }
}
