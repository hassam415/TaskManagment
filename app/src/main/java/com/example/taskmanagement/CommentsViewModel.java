package com.example.taskmanagement;

import com.example.taskmanagement.callback.ResponseCallback;

import org.checkerframework.checker.units.qual.C;

import java.util.List;

public class CommentsViewModel {
    public CommentsRepository commentsRepository;

    public CommentsViewModel(CommentsRepository commentsRepository) {
        this.commentsRepository = commentsRepository;
    }
    public void saveComment(String id, String Comment, ResponseCallback<Comment>responseCallback){
        commentsRepository.saveComment(id, Comment, responseCallback);
    }
    public void savereply(String id, String comentId, String reply, ResponseCallback<Comment>responseCallback){
        commentsRepository.replycoment(id,comentId,reply,responseCallback);
    }
    public void getList(String id, ResponseCallback<List<Comment>>responseCallback){
        commentsRepository.getlist(id,responseCallback);
    }
}
