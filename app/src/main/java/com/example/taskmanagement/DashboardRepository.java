package com.example.taskmanagement;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;

import com.example.taskmanagement.callback.ResponseCallback;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardRepository {
    private Context context;
    FirebaseFirestore fb;
    public ApiService apiService;
    public DashboardRepository(Context context) {

        this.context = context.getApplicationContext();

        fb = FirebaseFirestore.getInstance();

        apiService = ApiClient.getRetrofit()
                .create(ApiService.class);
    }
    public interface DashBoardResponse{
        void OnSuccess(ResponseDashBoard responseDashBoard);
        void OnError(String message);
    }
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
  public void getTaskStatus(String statusId,ResponseCallback<Integer> callback){
        fb.collection("Task").whereEqualTo("statusId",statusId).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                int count=queryDocumentSnapshots.size();
                callback.onSuccess(count,"Success");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                callback.onError(e.getLocalizedMessage());
            }
        });
  }
  public void getSingleUserTask(String statusId,String id,ResponseCallback<Integer>callback){
fb.collection("Task").whereArrayContains("selecteduser",id).whereEqualTo("statusId",statusId).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
    @Override
    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
        int count=queryDocumentSnapshots.size();
        callback.onSuccess(count,"Succes");
    }
}).addOnFailureListener(new OnFailureListener() {
    @Override
    public void onFailure(@NonNull Exception e) {
        callback.onError(e.getLocalizedMessage());
    }
});
  }

    public void getTaskApi(ResponseCallback<ResponseDashBoard> callback) {

        String token = ApiSesson.getAccess_Token();

        apiService.getDashboard()
                .enqueue(new Callback<ResponseDashBoard>() {

                    @Override
                    public void onResponse(
                            Call<ResponseDashBoard> call,
                            Response<ResponseDashBoard> response) {

                        if (response.isSuccessful()
                                && response.body() != null) {

                            callback.onSuccess(response.body(), "Success");

                        } else if (response.code()==401) {
                            ApiSesson.clearSession(context);
                            Intent intent=new Intent(context,LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            context.startActivity(intent);

                        }
                    }

                    @Override
                    public void onFailure(
                            Call<ResponseDashBoard> call,
                            Throwable throwable) {

                        callback.onError(throwable.getMessage());
                    }
                });
    }
}
