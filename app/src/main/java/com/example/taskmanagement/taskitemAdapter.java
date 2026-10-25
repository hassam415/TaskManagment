
package com.example.taskmanagement;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskmanagement.databinding.TaskuserBinding;

import java.util.ArrayList;
import java.util.List;

public class taskitemAdapter extends RecyclerView.Adapter<TaskItemViewHolder> {

    Context context;
    List<User> stringList;
    List<User> selecteduser;

    OnDelet onDelet;
    Boolean showdeletbtn = true;

    public taskitemAdapter(Context context, List<User> stringList) {
        this.context = context;
        this.stringList = stringList;
        this.selecteduser = new ArrayList<>();
    }

    // Constructor for CustomUserView selection list
    public taskitemAdapter(Context context, List<User> stringList,
                           List<User> selecteduser) {
        this.context = context;
        this.stringList = stringList;
        this.selecteduser = selecteduser;
    }

    public void setOnDelet(OnDelet onDelet) {
        this.onDelet = onDelet;
    }

    public interface OnDelet {
        void Ondelet(int position);
    }

    public void RemoveDeletebtn() {
        this.showdeletbtn = false;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TaskItemViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        TaskuserBinding binding =
                TaskuserBinding.inflate(inflater, parent, false);

        return new TaskItemViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(
            @NonNull TaskItemViewHolder holder, int position) {

        User user = stringList.get(position);

        holder.binding.assigneeName.setText(user.getName());
        holder.binding.assigneeJob.setText(user.getJob_title());

        // Purani listener remove karein, phir checkbox ki state set karein
        holder.binding.assigneeCheck.setOnCheckedChangeListener(null);

        holder.binding.assigneeCheck.setChecked(isSelected(user));

        holder.binding.assigneeCheck.setOnCheckedChangeListener(
                (buttonView, isChecked) -> {

                    if (isChecked) {
                        if (!isSelected(user)) {
                            selecteduser.add(user);
                        }
                    } else {
                        removeSelectedUser(user);
                    }

                    Log.d("ASSIGNEE_ADAPTER",
                            "Selected count: " + selecteduser.size());

                    for (User selected : selecteduser) {
                        Log.d("ASSIGNEE_ADAPTER",
                                "Selected user: " + selected.getName()
                                        + ", ID: " + selected.getId());
                    }
                });

        // Row par click karne se bhi checkbox toggle ho
        holder.itemView.setOnClickListener(v ->
                holder.binding.assigneeCheck.setChecked(
                        !holder.binding.assigneeCheck.isChecked()
                ));
    }

    private boolean isSelected(User user) {
        if (user == null || user.getId() == null) {
            return false;
        }

        for (User selected : selecteduser) {
            if (selected != null && selected.getId() != null
                    && String.valueOf(selected.getId())
                    .equals(String.valueOf(user.getId()))) {
                return true;
            }
        }

        return false;
    }

    private void removeSelectedUser(User user) {
        if (user == null || user.getId() == null) {
            return;
        }

        for (int i = selecteduser.size() - 1; i >= 0; i--) {
            User selected = selecteduser.get(i);

            if (selected != null && selected.getId() != null
                    && String.valueOf(selected.getId())
                    .equals(String.valueOf(user.getId()))) {
                selecteduser.remove(i);
            }
        }
    }

    public void setUsers(List<User> users) {
        stringList.clear();

        if (users != null) {
            stringList.addAll(users);
        }

        notifyDataSetChanged();

        Log.d("ASSIGNEE_ADAPTER",
                "Adapter item count: " + stringList.size());
    }

    @Override
    public int getItemCount() {
        return stringList.size();
    }
}
