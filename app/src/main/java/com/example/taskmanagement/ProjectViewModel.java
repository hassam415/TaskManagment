package com.example.taskmanagement;

import com.example.taskmanagement.callback.ResponseCallback;

import java.util.List;

public class ProjectViewModel {

    public ProjectRepository projectRepository;

    public ProjectViewModel(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }
    public void getProjectList(ResponseCallback<List<Project>>responseCallback ){
        projectRepository.getProjectList(responseCallback);
    }
    public void creatProject(ProjectRequest project, ResponseCallback<ProjectResponse>responseCallback){
        projectRepository.createProject(project,responseCallback);
    }
    public void deleteProject(int id,ResponseCallback<Project> responseCallback){
        projectRepository.deleteProject(id,responseCallback);
    }
    public void updateProject(int id,ProjectRequest projectRequest,ResponseCallback<Project>responseCallback){
        projectRepository.updateProject(id, projectRequest, responseCallback);
    }
}
