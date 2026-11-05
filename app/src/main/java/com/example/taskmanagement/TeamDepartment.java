package com.example.taskmanagement;

public class TeamDepartment {
    int id,organization_id,teams_count;
    String name,created_at;

    public TeamDepartment(int id, int teams_count, int organization_id, String name, String created_at) {
        this.id = id;
        this.teams_count = teams_count;
        this.organization_id = organization_id;
        this.name = name;
        this.created_at = created_at;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TeamDepartment(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getOrganization_id() {
        return organization_id;
    }

    public void setOrganization_id(int organization_id) {
        this.organization_id = organization_id;
    }

    public int getTeams_count() {
        return teams_count;
    }

    public void setTeams_count(int teams_count) {
        this.teams_count = teams_count;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public TeamDepartment() {
    }
    @Override
    public String toString() {
        return name;
    }
}
