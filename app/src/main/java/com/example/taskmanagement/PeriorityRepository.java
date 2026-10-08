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

public class PeriorityRepository {
    FirebaseFirestore fs;

    public PeriorityRepository() {
        fs=FirebaseFirestore.getInstance();
    }
    public void savePeriority(String priority, ResponseCallback<Periority>responseCallback){
        Periority periority=new Periority();
        periority.setName(priority);
        DocumentReference reference=fs.collection("Periorities").document();
        periority.setId(reference.getId());
        reference.set(periority).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                responseCallback.onSuccess(periority,"Success");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                responseCallback.onError(e.getLocalizedMessage());
            }
        });
    }
    public void getList(ResponseCallback<List<Periority>>responseCallback){
        fs.collection("Periorities").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<Periority>periorities=new ArrayList<>();
                for (DocumentSnapshot ds:queryDocumentSnapshots.getDocuments()){
                    Periority periority=ds.toObject(Periority.class);
                    periorities.add(periority);
                }
                responseCallback.onSuccess(periorities,"Retrived");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                responseCallback.onError(e.getLocalizedMessage());
            }
        });
    }
    public void updatePeriority(String id,String periority,ResponseCallback<Periority>responseCallback){
        Periority periority1=new Periority();
        periority1.setName(periority);
        fs.collection("Periorities").document(id).update("name",periority).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                responseCallback.onError(e.getLocalizedMessage());
            }
        }).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                responseCallback.onSuccess(periority1,"Updated");
            }
        });
    }
}
