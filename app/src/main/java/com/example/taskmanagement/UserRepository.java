package com.example.taskmanagement;

import static android.content.ContentValues.TAG;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.taskmanagement.callback.ResponseCallback;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class UserRepository {
    public FirebaseAuth auth;
    public FirebaseFirestore db;

    public UserRepository() {
        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
    }

    public void createUser(String name, String email, String password, String address, ResponseCallback<User> responseCallback) {
        auth.createUserWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                String uid = auth.getCurrentUser().getUid();
                User user = new User();
                user.setName(name);
                user.setEmail(email);
                user.setUuid(uid);
                user.setAddress(address);
                user.setPassword(password);
                db.collection("User").document(uid).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {

                        responseCallback.onSuccess(user, "User registered successfully");
                        Log.d("User", "onSuccess: ");
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("USER", "Database error: " + e.getMessage());
                        responseCallback.onError(e.getLocalizedMessage());

                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("User", "onFailure: " + e.getMessage());
            }
        });
    }
    public void getUser(ResponseCallback<List<User>>callback){
        db.collection("User").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<User>userList=new ArrayList<>();
                for (DocumentSnapshot snapshot : queryDocumentSnapshots.getDocuments()){
                    User user=snapshot.toObject(User.class);
                    if (user!=null){
                        userList.add(user);
                    }

                }
                callback.onSuccess(userList,"Data Retrive Successfully");

            }
         }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
           callback.onError(e.getLocalizedMessage());
            }
        });
    }
    public void getSingleUser(String id,ResponseCallback<User> callback){
        db.collection("User").document(id).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()){
                    User user=documentSnapshot.toObject(User.class);
                    callback.onSuccess(user,"Retrived");
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                callback.onError(e.getLocalizedMessage());
            }
        });
    }
    public void loginuser(String email,String password,ResponseCallback<User> responseCallback){
        auth.signInWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                String uid=authResult.getUser().getUid();
              FirebaseFirestore.getInstance().collection("User").document(uid).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                  @Override
                  public void onSuccess(DocumentSnapshot documentSnapshot) {
                      User user=documentSnapshot.toObject(User.class);
                     String role=documentSnapshot.getString("role");
                     if ("Admin".equals(role)){
                        responseCallback.onSuccess(user,"Admin Panel");
                     }
                     else {
                      responseCallback.onSuccess(user,"User Login");
                     }
                  }
              }).addOnFailureListener(new OnFailureListener() {
                  @Override
                  public void onFailure(@NonNull Exception e) {
responseCallback.onError(e.getLocalizedMessage());
                  }
              });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                responseCallback.onError(e.getLocalizedMessage());
            }
        });
    }
}
