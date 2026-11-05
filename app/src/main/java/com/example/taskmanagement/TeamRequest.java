package com.example.taskmanagement;

import java.util.List;

public class TeamRequest {
    int id,department_id,lead_user_id;
    String name,description;
    List<Integer> member_ids;

    public TeamRequest(int id, int department_id, int lead_user_id, String name, String description, List<Integer> member_ids) {
        this.id = id;
        this.department_id = department_id;
        this.lead_user_id = lead_user_id;
        this.name = name;
        this.description = description;
        this.member_ids = member_ids;
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

    public TeamRequest() {
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    @Override
    public String toString() {
        return name;
    }
}
