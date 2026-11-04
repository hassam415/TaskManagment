package com.example.taskmanagement;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class CustomteamView extends LinearLayout {

    RecyclerView teamreycler;

    CustomTeamAdapter customTeamAdapter;

    public List<Team> teamList = new ArrayList<>();
    public List<Team> selectedTeam = new ArrayList<>();

    public CustomteamView(Context context) {
        super(context);
        init(context);
    }

    public CustomteamView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CustomteamView(Context context, @Nullable AttributeSet attrs,
                          int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public CustomteamView(Context context, AttributeSet attrs,
                          int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    public void init(Context context) {

        setOrientation(VERTICAL);

        LayoutInflater.from(context).inflate(
                R.layout.customteam, this, true);

        teamreycler = findViewById(R.id.teamreycler);

        teamreycler.setLayoutManager(
                new GridLayoutManager(context, 2));

        teamreycler.setHasFixedSize(true);
        teamreycler.setNestedScrollingEnabled(false);

        customTeamAdapter = new CustomTeamAdapter(
                context,
                new ArrayList<>(),
                selectedTeam
        );

        teamreycler.setAdapter(customTeamAdapter);
    }

    public void setteam(List<Team> teams) {

        teamList.clear();

        if (teams != null) {
            teamList.addAll(teams);
        }

        customTeamAdapter.setTeams(new ArrayList<>(teamList));
    }

    public List<Team> getSelectedTeams() {
        return new ArrayList<>(selectedTeam);
    }

    public List<Integer> getSelectedTeamIds() {

        List<Integer> ids = new ArrayList<>();

        for (Team team : selectedTeam) {
            if (team != null && team.getId() > 0) {
                ids.add(team.getId());
            }
        }

        Log.d("SELECTED_TEAMS", "Selected IDs: " + ids);

        return ids;
    }

    public void clearSelection() {
        selectedTeam.clear();
        customTeamAdapter.notifyDataSetChanged();
    }

    public void setSelectedTeamIds(List<Integer> selectedIds) {

        selectedTeam.clear();

        if (selectedIds != null) {
            for (Team team : teamList) {

                if (team == null) continue;

                if (selectedIds.contains(team.getId())) {
                    selectedTeam.add(team);
                }
            }
        }

        customTeamAdapter.notifyDataSetChanged();
    }
}