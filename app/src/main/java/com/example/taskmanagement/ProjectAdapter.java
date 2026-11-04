package com.example.taskmanagement;

import android.content.Context;
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

import com.example.taskmanagement.databinding.ProjectItemBinding;

import java.util.ArrayList;
import java.util.List;

public class ProjectAdapter extends RecyclerView.Adapter<ProjectViewHolder> {
    Context context;
    List<Project>projects;
OnDeletBtnClick  onDeletBtnClick;

    public void setOnDeletBtnClick(OnDeletBtnClick onDeletBtnClick) {
        this.onDeletBtnClick = onDeletBtnClick;
    }

    public ProjectAdapter(Context context, List<Project> projects) {
        this.context = context;
        this.projects = projects;
    }
public interface OnDeletBtnClick{
        void OnClick(int Id,int position);
}
    @NonNull
    @Override
    public ProjectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        ProjectItemBinding binding=ProjectItemBinding.inflate(inflater,parent,false);
        return new ProjectViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ProjectViewHolder holder, int position) {
Project project=projects.get(position);
holder.binding.projectName.setText(project.getName());
holder.binding.projectDescription.setText(project.getDescription());
holder.binding.projectStatus.setText(project.getStatus().getLabel());
holder.binding.projectInitials.setText(project.getKey());
holder.binding.completedTasks.setText(project.getCompleted_tasks_count()+"/"+project.getTasks_count()+"task completed");
holder.binding.progressPercent.setText(project.getProgress_percentage()+"%");
        if (project.getTeam() != null) {
            holder.binding.projectTeam.setText(project.getTeam().getName());
        } else {
            holder.binding.projectTeam.setText("No Team");
        }
holder.binding.projectLead.setText(project.getLead().getName());
        double progress=project.getProgress_percentage();
        holder.binding.projectProgress.setMax(100);
        holder.binding.projectProgress.setProgress((int)progress);
        holder.binding.optionbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu menu=new PopupMenu(context,holder.binding.optionbtn);
                menu.getMenuInflater().inflate(R.menu.task_update,menu.getMenu());
                if (context instanceof FragmentActivity) {

                    FragmentActivity activity = (FragmentActivity) context;

                    FragmentManager fragmentManager =
                            activity.getSupportFragmentManager();
                    menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            if (item.getItemId()==R.id.update){
                                AddProjectFragment addProjectFragment=new AddProjectFragment();
                                Bundle bundle=new Bundle();
bundle.putString("name", project.getName());
bundle.putInt("projectId", project.getId());
bundle.putString("key", project.getKey());
bundle.putString("status",project.getStatus().getLabel());

bundle.putInt("team",project.getTeam().getId());
bundle.putInt("projectLead",project.getLead().getId());
bundle.putString("startDate",project.getStart_date());
bundle.putString("endDate",project.getTarget_end_date());
bundle.putString("discription",project.getDescription());
                                ArrayList<Integer>integers=new ArrayList<>();
for (User user:project.getMembers()){
    integers.add(user.getId());
}
                                bundle.putIntegerArrayList("assigness",integers);
                                addProjectFragment.setArguments(bundle);
                                fragmentManager.beginTransaction().replace(R.id.fragmentContainer,addProjectFragment).addToBackStack(null).commit();
                            }
                            if (item.getItemId()==R.id.delete){
                                int postion=holder.getPosition();
                                onDeletBtnClick.OnClick(project.getId(),postion);
                            }
                            return true;
                        }
                    });
                }


                menu.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return projects.size();
    }
}
