package com.example.taskmanagement;

import com.example.taskmanagement.callback.ResponseCallback;

import java.util.List;

public class TeamViewModel {
    TeamRepository teamRepository;

    public TeamViewModel(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }
    public void getTeamList(ResponseCallback<List<Team>>responseCallback ){
        teamRepository.TeamList(responseCallback);
    }
    public void creatTeam(TeamRequest teamRequest,ResponseCallback<Team>responseCallback){
        teamRepository.creatTeam(teamRequest,responseCallback);
    }
    public void getDepartment(ResponseCallback<List<TeamDepartment>>responseCallback){
        teamRepository.getDepartment(responseCallback);
    }
    public void deletTeam(int id,ResponseCallback<Team>responseCallback){
        teamRepository.deletTeam(id, responseCallback);
    }
    public void updateTeam(int id,TeamRequest teamRequest,ResponseCallback<Team>responseCallback){
        teamRepository.updateTeam(id,teamRequest,responseCallback);
    }
}
