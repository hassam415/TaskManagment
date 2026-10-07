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

public class CategoryRepository {
    FirebaseFirestore fs;

    public CategoryRepository() {
        fs=FirebaseFirestore.getInstance();

    }
    public void addCategory(String category, ResponseCallback<Category>responseCallback){
        Category  category1=new Category();
        category1.setName(category);
        DocumentReference reference=fs.collection("Categories").document();
        category1.setId(reference.getId());
        reference.set(category1).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                responseCallback.onSuccess(category1,"Success");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                responseCallback.onError(e.getLocalizedMessage());
            }
        });
    }
    public void getList(ResponseCallback<List<Category>>responseCallback){
        fs.collection("Categories").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<Category>categories=new ArrayList<>();
                for (DocumentSnapshot ds:queryDocumentSnapshots.getDocuments()){
                    Category category=ds.toObject(Category.class);
                    categories.add(category);
                }
                responseCallback.onSuccess(categories,"Displayes");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                responseCallback.onError(e.getLocalizedMessage());
            }
        });
    }
    public void updateCategory(String ID,String category,ResponseCallback<Category>responseCallback){
        Category category1=new Category();
        category1.setName(category);
        fs.collection("Categories").document(ID).update("name",category).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                responseCallback.onSuccess(category1,"Success");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                responseCallback.onError(e.getLocalizedMessage());
            }
        });
    }
}
