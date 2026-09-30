package com.example.taskmanagement;

import androidx.annotation.NonNull;

import com.example.taskmanagement.callback.ResponseCallback;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class DashboardRepository {
    FirebaseFirestore fb;
    public DashboardRepository() {
  fb=FirebaseFirestore.getInstance();  }
    public void getTask(ResponseCallback<Integer> callback){
        fb.collection("Task").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
               int totaltask= queryDocumentSnapshots.size();
               callback.onSuccess(totaltask,"Task Count");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                callback.onError("Task Not Found");
            }
        });
    }
    public void getUser(ResponseCallback<Integer> callback){
        fb.collection("User").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                int totaluser= queryDocumentSnapshots.size();
                callback.onSuccess(totaluser,"User Count");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                callback.onError("User not Found");
            }
        });
    }
    public void getAssignTask(ResponseCallback<Integer> callback){
        fb.collection("Task").whereEqualTo("status","In Review").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                int totalassign= queryDocumentSnapshots.size();
                callback.onSuccess(totalassign,"No of Assign Task");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                callback.onError(e.getLocalizedMessage());
            }
        });
    }
    public void getToDo(ResponseCallback<Integer>callback){
        fb.collection("Task").whereEqualTo("status","To do").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                int toDosize= queryDocumentSnapshots.size();
                callback.onSuccess(toDosize,"No of To do");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                callback.onError(e.getLocalizedMessage());
            }
        });
    }
    public void getInProgress(ResponseCallback<Integer>callback){
        fb.collection("Task").whereEqualTo("status","In Progess").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                int inProgress= queryDocumentSnapshots.size();
                callback.onSuccess(inProgress,"No of Progresss");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                callback.onError(e.getLocalizedMessage());
            }
        });
    }
    public void getCompletTask(ResponseCallback<Integer>callback){
        fb.collection("Task").whereEqualTo("status","Completed").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                int complet=queryDocumentSnapshots.size();
                callback.onSuccess(complet,"Complete");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                callback.onError(e.getLocalizedMessage());
            }
        });
    }
    public void getSingleCompletTask(String id,ResponseCallback<Integer>callback){
        fb.collection("Task").whereArrayContains("selecteduser",id).whereEqualTo("status","Completed").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                int complet=queryDocumentSnapshots.size();
                callback.onSuccess(complet,"Complete");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                callback.onError(e.getLocalizedMessage());
            }
        });
    }
    public void getSingleInProgress(String id,ResponseCallback<Integer>callback){
        fb.collection("Task").whereArrayContains("selecteduser",id).whereEqualTo("status","In Progess").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                int inProgress= queryDocumentSnapshots.size();
                callback.onSuccess(inProgress,"No of Progresss");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                callback.onError(e.getLocalizedMessage());
            }
        });
    }
    public void getSingleToDo(String id,ResponseCallback<Integer>callback){
        fb.collection("Task").whereArrayContains("selecteduser",id).whereEqualTo("status","To do").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                int toDosize= queryDocumentSnapshots.size();
                callback.onSuccess(toDosize,"No of To do");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                callback.onError(e.getLocalizedMessage());
            }
        });
    }
    public void getSingleReviewTask(String id,ResponseCallback<Integer> callback){
        fb.collection("Task").whereArrayContains("selecteduser",id).whereEqualTo("status","In Review").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                int totalassign= queryDocumentSnapshots.size();
                callback.onSuccess(totalassign,"No of Assign Task");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                callback.onError(e.getLocalizedMessage());
            }
        });
    }

}
