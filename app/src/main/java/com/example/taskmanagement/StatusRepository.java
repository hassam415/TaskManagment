package com.example.taskmanagement;

import androidx.annotation.NonNull;

import com.example.taskmanagement.callback.ResponseCallback;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class StatusRepository {
    FirebaseFirestore fs;

    public StatusRepository() {
        fs=FirebaseFirestore.getInstance();
    }
    public void addStatus(String status, ResponseCallback<Status> responseCallback){
        Status status1=new Status();
        status1.setName(status);
        DocumentReference  reference=fs.collection("Status").document();
        status1.setId(reference.getId());
        reference.set(status1).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                responseCallback.onSuccess(status1,"Data Add SuccessFully");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                responseCallback.onError(e.getLocalizedMessage());
            }
        });
    }
    public void getList(ResponseCallback<List<Status>>callback){
        fs.collection("Status").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<Status>statusList=new ArrayList<>();
                for (DocumentSnapshot ds:queryDocumentSnapshots.getDocuments()){
                    Status status=ds.toObject(Status.class);
                    statusList.add(status);

                }
                callback.onSuccess(statusList,"SuccesFuly");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                callback.onError(e.getLocalizedMessage());
            }
        });
    }
    public void updateStatus(String id,String status,ResponseCallback<Status>responseCallback){
        Status status1=new Status();
        status1.setName(status);

        fs.collection("Status").document(id).update("name",status).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                responseCallback.onSuccess(status1,"Success");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
               responseCallback.onError(e.getLocalizedMessage());
            }
        });
    }
}
