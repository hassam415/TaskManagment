package com.example.taskmanagement;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.taskmanagement.callback.ResponseCallback;
import com.example.taskmanagement.response.PaginateResponse;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommentsRepository {
    FirebaseFirestore fs;
FirebaseAuth fauth;
ApiService apiService;
    public CommentsRepository() {
        fs = FirebaseFirestore.getInstance();
        fauth=FirebaseAuth.getInstance();
        apiService=ApiClient.getRetrofit().create(ApiService.class);
    }

   // public void saveComment(String taskId, String comment, ResponseCallback<Comment> responseCallback) {
     //   String uid=fauth.getCurrentUser().getUid();
       // fs.collection("User").document(uid).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
         //   @Override
           // public void onSuccess(DocumentSnapshot documentSnapshot) {
             //   String name=documentSnapshot.getString("name");
              //  DocumentReference reference=  fs.collection("Task").document(taskId).collection("Comment").document();
              //  String comentid=reference.getId();
             //   Comment comment1 = new Comment();
              //  comment1.setComment(comment);
              //  comment1.setId(comentid);
              //  comment1.setUsername(name);
              //  comment1.setReplies(new ArrayList<>());
              //  comment1.setUserReplies(new ArrayList<>());
              //  reference.set(comment1).addOnSuccessListener(new OnSuccessListener<Void>() {
                 //   @Override
                 //   public void onSuccess(Void unused) {
                   //     saveCommentInActivity(taskId,uid,name +" Commented :"+comment);
                   //     responseCallback.onSuccess(comment1,"Comment added");
                  //  }
              //  }).addOnFailureListener(new OnFailureListener() {
                //    @Override
                  //  public void onFailure(@NonNull Exception e) {
                    //    responseCallback.onError(e.getLocalizedMessage());
                  //  }
            //    });
          //  }
       // });

   // }
    public void replycoment(String taskid,
                            String commentID,
                            String reply,
                            ResponseCallback<Comment> responseCallback) {

        String uid = fauth.getCurrentUser().getUid();

        fs.collection("User")
                .document(uid)
                .get()
                .addOnSuccessListener(documentSnapshot -> {

                    String name = documentSnapshot.getString("name");

                    DocumentReference dr = fs.collection("Task")
                            .document(taskid)
                            .collection("Comment")
                            .document(commentID);

                    dr.get().addOnSuccessListener(documentSnapshot1 -> {

                        Comment comment =
                                documentSnapshot1.toObject(Comment.class);

                        if (comment == null) {
                            responseCallback.onError("Comment data not found");
                            return;
                        }

                        List<String> replies = new ArrayList<>();
                        List<String> userReplies = new ArrayList<>();

                        if (comment.getReplies() != null) {
                            replies.addAll(comment.getReplies());
                        }

                        if (comment.getUserReplies() != null) {
                            userReplies.addAll(comment.getUserReplies());
                        }


                        replies.add(reply);

                        userReplies.add(name);


                        dr.update("replies", replies)
                                .addOnSuccessListener(unused -> {


                                    dr.update("userReplies", userReplies)
                                            .addOnSuccessListener(unused2 -> {

                                                responseCallback.onSuccess(
                                                        comment,
                                                        "Reply added"
                                                );

                                            })
                                            .addOnFailureListener(e ->
                                                    responseCallback.onError(
                                                            e.getLocalizedMessage()
                                                    )
                                            );

                                })
                                .addOnFailureListener(e ->
                                        responseCallback.onError(
                                                e.getLocalizedMessage()
                                        )
                                );

                    }).addOnFailureListener(e ->
                            responseCallback.onError(
                                    e.getLocalizedMessage()
                            )
                    );

                }).addOnFailureListener(e ->
                        responseCallback.onError(
                                e.getLocalizedMessage()
                        )
                );
    }


//public void saveCommentInActivity(String taskId,String uid,String detail){
  //      TaskActivity  taskActivity=new TaskActivity();
    //    taskActivity.setTaskId(taskId);
      //  taskActivity.setUserId(uid);
        //taskActivity.setDetail(detail);
       // taskActivity.setTime_stamp(Timestamp.now());
       // fs.collection("Task Activity").add(taskActivity);
//}


    public void getlist(String id ,ResponseCallback<List<Comment>> callback){
        fs.collection("Task")
                .document(id)
                .collection("Comment").orderBy("id", Query.Direction.ASCENDING)
                .addSnapshotListener((querySnapshot, error) -> {

                    if (error != null) {
                        callback.onError(error.getLocalizedMessage());
                        return;
                    }

                    List<Comment> commentList = new ArrayList<>();

                    for (DocumentSnapshot snapshot : querySnapshot.getDocuments()) {

                        Comment comment = snapshot.toObject(Comment.class);

                        if (comment != null) {
                        //    comment.setId(snapshot.getId());
                            commentList.add(comment);
                        }
                    }

                    callback.onSuccess(commentList, "Updated");
                });
    }
    public void getApiComment(String id,ResponseCallback<List<Comment>> callback){
        apiService.getApiComment(id).enqueue(new Callback<PaginateResponse<Comment>>() {
            @Override
            public void onResponse(Call<PaginateResponse<Comment>> call, Response<PaginateResponse<Comment>> response) {
                if (response.body()!=null){
                    callback.onSuccess(response.body().getData(),"Success");
                }

            }

            @Override
            public void onFailure(Call<PaginateResponse<Comment>> call, Throwable throwable) {
callback.onError(throwable.getMessage());
            }});
    }
    public void creatComment(String id,RequestComment requestComment ,ResponseCallback<Comment>responseCallback){
        apiService.creatComment(requestComment,id).enqueue(new Callback<Comment>() {
            @Override
            public void onResponse(Call<Comment> call, Response<Comment> response) {
responseCallback.onSuccess(response.body(),"Success");
            }

            @Override
            public void onFailure(Call<Comment> call, Throwable throwable) {
responseCallback.onError(throwable.getMessage());
            }
        });
    }
    public void DeleteComment(String taskId,int commentId,ResponseCallback<Comment>responseCallback){
        apiService.DeleteComment(taskId,commentId).enqueue(new Callback<Comment>() {
            @Override
            public void onResponse(Call<Comment> call, Response<Comment> response) {
                responseCallback.onSuccess(response.body(),"Success");
            }

            @Override
            public void onFailure(Call<Comment> call, Throwable throwable) {
responseCallback.onError(throwable.getMessage());
            }
        });
    }
}
