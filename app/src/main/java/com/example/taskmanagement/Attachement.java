package com.example.taskmanagement;

public class Attachement {
    int id,task_id;
    String file_name,file_type,download_url,created_at;
    private User uploaded_by;

    public Attachement() {
    }

    public Attachement(int id, int task_id, String file_name, String file_type, String download_url, String created_at, User uploaded_by) {
        this.id = id;
        this.task_id = task_id;
        this.file_name = file_name;
        this.file_type = file_type;
        this.download_url = download_url;
        this.created_at = created_at;
        this.uploaded_by = uploaded_by;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTask_id() {
        return task_id;
    }

    public void setTask_id(int task_id) {
        this.task_id = task_id;
    }

    public String getFile_name() {
        return file_name;
    }

    public void setFile_name(String file_name) {
        this.file_name = file_name;
    }

    public String getFile_type() {
        return file_type;
    }

    public void setFile_type(String file_type) {
        this.file_type = file_type;
    }

    public String getDownload_url() {
        return download_url;
    }

    public void setDownload_url(String download_url) {
        this.download_url = download_url;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public User getUploaded_by() {
        return uploaded_by;
    }

    public void setUploaded_by(User uploaded_by) {
        this.uploaded_by = uploaded_by;
    }
}
