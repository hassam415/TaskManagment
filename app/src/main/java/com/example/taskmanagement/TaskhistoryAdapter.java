package com.example.taskmanagement;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskmanagement.databinding.HistoryItemBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class TaskhistoryAdapter extends RecyclerView.Adapter<TaskHistoryViewholder> {
    Context context;
    List<TaskActivity>taskActivities;
    FirebaseFirestore fs;

    public TaskhistoryAdapter(Context context, List<TaskActivity> taskActivities) {
        this.context = context;
        this.taskActivities = taskActivities;
    }

    @NonNull
    @Override
    public TaskHistoryViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        HistoryItemBinding binding=HistoryItemBinding.inflate(inflater,parent,false);

        return new TaskHistoryViewholder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskHistoryViewholder holder, int position) {

TaskActivity activity=taskActivities.get(position);
holder.binding.detail.setText(activity.getDescription());
        if (activity.getProperties() != null) {

            holder.binding.status.setVisibility(View.VISIBLE);

            holder.binding.status.setText(
                    "Status: " +
                            activity.getProperties().old_status +
                            " to " +
                            activity.getProperties().new_status
            );

        } else {
            holder.binding.status.setVisibility(View.GONE);
        }


        holder.binding.time.setText(activity.getFormatted_created_at());
        holder.binding.username.setText(activity.getUser().getName());
      //  FirebaseFirestore.getInstance().collection("User").document(activity.getUserId()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
        //    @Override
          //  public void onSuccess(DocumentSnapshot documentSnapshot) {
            //    String username=documentSnapshot.getString("name");
              //  holder.binding.username.setText("username :"+username);
           // }
      //  }).addOnFailureListener(new OnFailureListener() {
        //    @Override
          //  public void onFailure(@NonNull Exception e) {
            //    Toast.makeText(context, ""+e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
          //  }
      //  });
    }

    @Override
    public int getItemCount() {
        return taskActivities.size();
    }
}
