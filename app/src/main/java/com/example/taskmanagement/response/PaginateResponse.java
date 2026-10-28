package com.example.taskmanagement.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PaginateResponse <T>{

    List<T> data;

    @SerializedName("per_page")

    int perPage;
    int page;

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public int getPerPage() {
        return perPage;
    }

    public void setPerPage(int perPage) {
        this.perPage = perPage;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }
}
