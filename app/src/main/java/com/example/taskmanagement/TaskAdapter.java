package com.example.taskmanagement;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskmanagement.databinding.TaskItemBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TaskAdapter extends RecyclerView.Adapter<TaskViewHolder> {
    Context context;
    List<Task> taskList;

    OnTaskClick onTaskClick;
OnDeletBtnClick onDeletBtnClick;
    public TaskAdapter(Context context, List<Task> taskList) {
        this.context = context;
        this.taskList = taskList;

    }

    public void setOnDeletBtnClick(OnDeletBtnClick onDeletBtnClick) {
        this.onDeletBtnClick = onDeletBtnClick;
    }

    public void setOnTaskClick(OnTaskClick onTaskClick) {
        this.onTaskClick = onTaskClick;
    }

    public interface OnTaskClick {
        void onClick(int position);
    }
    public interface OnDeletBtnClick{
        void onClick(String taskID,int position);
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
        holder.binding.status.setText(task.getStatus().getLabel());
        holder.binding.reference.setText(task.getReference_code());
        holder.binding.category.setText(task.getCategory());
        holder.binding.date.setText(task.getDue_date());
        holder.binding.periority.setText(task.getPriority().getLabel());

        String teamName = "No team assigned";

        if (task.getTeam() != null) {

          //  if (task.getTeam().getName() != null) {
            //    teamName = task.getTeam().getName();

        //    } else if (task.getTeam().getLabel() != null) {
          //      teamName = task.getTeam().getLabel();

            //} else if (task.getTeam().getValue() != null) {
              //  teamName = task.getTeam().getValue();
          //  }
        }

        holder.binding.team.setText("Team: " + task.getTeam().name);
        holder.binding.updatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu menu = new PopupMenu(context, holder.binding.updatebtn);
                menu.getMenuInflater().inflate(R.menu.task_update, menu.getMenu());
                if (context instanceof FragmentActivity) {

                    FragmentActivity activity = (FragmentActivity) context;

                    FragmentManager fragmentManager =
                            activity.getSupportFragmentManager();

                    menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            if (item.getItemId() == R.id.update) {
                                TaskFragment taskFragment=new TaskFragment();
                                Bundle bundle=new Bundle();
                                bundle.putString("title",task.getTitle());
                                bundle.putString("discription",task.getDiscription());
                                bundle.putString("category",task.getCategory());
                                bundle.putString("priority",task.getPriority().getValue());
                                bundle.putString("taskId",task.getId());
                                bundle.putString("hourss",task.getEstimated_hours());
bundle.putString("team",task.getTeam().name);
taskFragment.setArguments(bundle);
                                fragmentManager.beginTransaction().replace(R.id.fragmentContainer, taskFragment).addToBackStack(null).commit();
                            }
                            if (item.getItemId()==R.id.delete){
                                int currentpostion=holder.getPosition();
onDeletBtnClick.onClick(task.getId(), currentpostion);
                            }
                            return true;
                        }

                    });
                }
                menu.show();
            }
        });

      //  Timestamp time=task.getTimes_tamp();
        //Date date=time.toDate();
     //   SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
      //  holder.binding.date.setText(sdf.format(date));
//FirebaseFirestore.getInstance().collection("Status").document(task.getStatusId()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
  //  @Override
    //public void onSuccess(DocumentSnapshot documentSnapshot) {
      //  String status=documentSnapshot.getString("name");
        //holder.binding.status.setText(status);
    //    if ("Task Assigned".equals(status)) {
      //      holder.binding.status
        //            .setChipBackgroundColorResource(R.color.TaskAssign);

//        } else if ("To do".equals(status)) {
  //          holder.binding.status
    //                .setChipBackgroundColorResource(R.color.Todo);

      //  } else if ("In Review".equals(status)) {
        //    holder.binding.status
          //          .setChipBackgroundColorResource(R.color.InReview);

//        } else if ("In Progress".equals(status)) {
  //          holder.binding.status
    //                .setChipBackgroundColorResource(R.color.InProgress);

      //  } else if ("Completed".equals(status)) {
        //    holder.binding.status
          //          .setChipBackgroundColorResource(R.color.Completed);
     //   }

  //  }
//}).addOnFailureListener(new OnFailureListener() {
  //  @Override
    //public void onFailure(@NonNull Exception e) {

   // }
//});



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
