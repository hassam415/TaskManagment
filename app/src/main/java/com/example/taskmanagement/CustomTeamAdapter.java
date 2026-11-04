package com.example.taskmanagement;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskmanagement.databinding.TeamItemBinding;

import java.util.List;

public class CustomTeamAdapter
        extends RecyclerView.Adapter<CustomTeamViewHolder> {

    Context context;
    List<Team> teamList;
    List<Team> selectedTeam;

    public CustomTeamAdapter(Context context,
                             List<Team> teamList,
                             List<Team> selectedTeam) {

        this.context = context;
        this.teamList = teamList;
        this.selectedTeam = selectedTeam;
    }

    @NonNull
    @Override
    public CustomTeamViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);

        TeamItemBinding binding =
                TeamItemBinding.inflate(inflater, parent, false);

        return new CustomTeamViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(
            @NonNull CustomTeamViewHolder holder, int position) {

        Team team = teamList.get(position);

        holder.binding.teamname.setText(team.getName());

        holder.binding.checkbox.setOnCheckedChangeListener(null);

        holder.binding.checkbox.setChecked(isSelected(team));

        holder.binding.checkbox.setOnCheckedChangeListener(
                (buttonView, isChecked) -> {

                    if (isChecked) {

                        if (!isSelected(team)) {
                            selectedTeam.add(team);
                        }

                    } else {
                        removeSelectedTeam(team);
                    }

                    Log.d("SELECTED_TEAMS",
                            "Selected IDs: " + getSelectedIds());
                });

        holder.itemView.setOnClickListener(v ->
                holder.binding.checkbox.setChecked(
                        !holder.binding.checkbox.isChecked()
                ));
    }

    private boolean isSelected(Team team) {

        for (Team selected : selectedTeam) {

            if (selected != null &&
                    selected.getId() == team.getId()) {
                return true;
            }
        }

        return false;
    }

    private void removeSelectedTeam(Team team) {

        for (int i = selectedTeam.size() - 1; i >= 0; i--) {

            Team selected = selectedTeam.get(i);

            if (selected != null &&
                    selected.getId() == team.getId()) {

                selectedTeam.remove(i);
            }
        }
    }

    private List<Integer> getSelectedIds() {

        List<Integer> ids = new java.util.ArrayList<>();

        for (Team team : selectedTeam) {
            ids.add(team.getId());
        }

        return ids;
    }

    public void setTeams(List<Team> teams) {

        teamList.clear();

        if (teams != null) {
            teamList.addAll(teams);
        }

        notifyDataSetChanged();

        Log.d("TEAM_ADAPTER",
                "Adapter item count: " + teamList.size());
    }

    @Override
    public int getItemCount() {
        return teamList.size();
    }
}