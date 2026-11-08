package com.example.taskmanagement;

import java.util.List;

public class Team {

    int id, department_id, lead_user_id;
    String name, description;

    List<Integer> member_ids;

    private TeamDepartment department;
    private TeamLead lead;
    List<User> members;

    public Team() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDepartment_id() {
        return department_id;
    }

    public void setDepartment_id(int department_id) {
        this.department_id = department_id;
    }

    public int getLead_user_id() {
        return lead_user_id;
    }

    public void setLead_user_id(int lead_user_id) {
        this.lead_user_id = lead_user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Integer> getMember_ids() {
        return member_ids;
    }

    public void setMember_ids(List<Integer> member_ids) {
        this.member_ids = member_ids;
    }

    public TeamDepartment getDepartment() {
        return department;
    }

    public void setDepartment(TeamDepartment department) {
        this.department = department;
    }

    public TeamLead getLead() {
        return lead;
    }

    public void setLead(TeamLead lead) {
        this.lead = lead;
    }

    public List<User> getMembers() {
        return members;
    }

    public void setMembers(List<User> members) {
        this.members = members;
    }

    public Team(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}