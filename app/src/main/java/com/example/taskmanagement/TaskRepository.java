package com.example.taskmanagement;

import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.taskmanagement.callback.ResponseCallback;
import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;

public class TaskRepository {
    public FirebaseFirestore fb;
    public FirebaseStorage fs;

    public TaskRepository() {
        fb = FirebaseFirestore.getInstance();
        fs = FirebaseStorage.getInstance();

    }

    public void saveDate(String title, String discription, List<String> selecteduser, List<String> images, ResponseCallback<Task> responseCallback) {

        List<String> imagurl = new ArrayList<>();

        if (images == null || images.isEmpty()) {

            saveTask(title, discription, selecteduser, imagurl, responseCallback);

            return;
        }

        uploadImages(title, discription, selecteduser, images, imagurl, 0, responseCallback);
    }


    private void uploadImages(String title, String discription, List<String> selecteduser, List<String> images, List<String> imagurl, int position, ResponseCallback<Task> responseCallback) {

        if (position >= images.size()) {

            saveTask(title, discription, selecteduser, imagurl, responseCallback);

            return;
        }

        String image = images.get(position);

        Uri uri = Uri.parse(image);

        StorageReference reference = fs.getReference()
                .child("Task Images")
                .child(System.currentTimeMillis() + "jpg");

        reference.putFile(uri)
                .addOnSuccessListener(taskSnapshot -> {

                    reference.getDownloadUrl()
                            .addOnSuccessListener(downloadUri -> {

                                imagurl.add(downloadUri.toString());

                                uploadImages(title, discription, selecteduser, images, imagurl, position + 1, responseCallback);

                            })
                            .addOnFailureListener(e -> {

                                responseCallback.onError(
                                        e.getLocalizedMessage()
                                );

                            });

                })
                .addOnFailureListener(e -> {

                    responseCallback.onError(
                            e.getLocalizedMessage()
                    );

                });
    }


    private void saveTask(String title,
                          String discription,
                          List<String> selecteduser,
                          List<String> imagurl,
                          ResponseCallback<Task> responseCallback) {

        Task task = new Task();

        task.setTitle(title);
        task.setDiscription(discription);
        task.setSelecteduser(selecteduser);
        task.setStatus("Task Assigned");
        task.setSelectedImges(imagurl);

        DocumentReference documentReference =
                fb.collection("Task").document();

        String taskid = documentReference.getId();

        task.setUid(taskid);

        documentReference.set(task)
                .addOnSuccessListener(unused -> {
                    saveUserTask(taskid, selecteduser, responseCallback, task);


                })
                .addOnFailureListener(e -> {

                    responseCallback.onError(
                            e.getLocalizedMessage()
                    );

                });
    }

    public void saveUserTask(String taskid, List<String> selecteduser, ResponseCallback<Task> responseCallback, Task task) {
        for (String userId : selecteduser) {
            TaskActivity taskActivity = new TaskActivity();
            taskActivity.setTaskId(taskid);
            taskActivity.setUserId(userId);
            taskActivity.setDetail("Task is created");
            taskActivity.setStatus("Task Assigned");
            taskActivity.setTime_stamp(Timestamp.now());
            DocumentReference reference = fb.collection("Task Activity").document();
            taskActivity.setId(reference.getId());
            reference.set(taskActivity).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    responseCallback.onSuccess(task, "Succes");
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    responseCallback.onError(e.getLocalizedMessage());
                }
            });
        }

    }

    public void getTask(ResponseCallback<List<Task>> responseCallback) {
        fb.collection("Task").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<Task> taskList = new ArrayList<>();
                for (DocumentSnapshot snapshot : queryDocumentSnapshots.getDocuments()) {
                    Task task = snapshot.toObject(Task.class);
                    taskList.add(task);
                }
                responseCallback.onSuccess(taskList, "Retrive");
            }
        });

    }

    public void getUserTaskCount(String id, ResponseCallback responseCallback) {
        fb.collection("Task").whereArrayContains("selecteduser", id).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                int count = queryDocumentSnapshots.size();
                responseCallback.onSuccess(count, " Task SuccessFully");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                responseCallback.onError(e.getLocalizedMessage());
            }
        });
    }

    public void getSingleTask(String id, ResponseCallback<Task> responseCallback) {
        fb.collection("Task").document(id).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {

                    Task task = documentSnapshot.toObject(Task.class);

                    responseCallback.onSuccess(task, "SuccessFully");
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                responseCallback.onError(e.getLocalizedMessage());
            }
        });
    }

    public void removeuser(String id, ResponseCallback callback) {
        fb.collection("Task").document(id).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                callback.onSuccess("Deleted", "Data Deleted");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                callback.onError(e.getLocalizedMessage());
            }
        });
    }

    public void getSingleUserTask(String id, ResponseCallback<List<Task>> responseCallback) {
        fb.collection("Task").whereArrayContains("selecteduser", id).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<Task> taskList = new ArrayList<>();
                for (DocumentSnapshot snapshot : queryDocumentSnapshots.getDocuments()) {
                    Task task = snapshot.toObject(Task.class);
                    taskList.add(task);
                }
                responseCallback.onSuccess(taskList, "Retrived");

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                responseCallback.onError(e.getLocalizedMessage());
            }
        });
    }

    public void getTaskActivity(String id, ResponseCallback<List<TaskActivity>> responseCallback) {
        fb.collection("Task Activity").whereEqualTo("taskId", id).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<TaskActivity> taskActivities = new ArrayList<>();
                for (DocumentSnapshot ds : queryDocumentSnapshots.getDocuments()) {
                    TaskActivity taskActivity = ds.toObject(TaskActivity.class);
                    taskActivity.setId(ds.getId());
                    Log.e("ActivityID", "FireStore ID " + ds.getId());
                    taskActivities.add(taskActivity);
                    Log.d("ACTIVITY_ID",
                            "Total = " + taskActivities.size());
                }
                responseCallback.onSuccess(taskActivities, "Retrived");

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                responseCallback.onError(e.getLocalizedMessage());
            }
        });

    }

    public void updateStatus(String taskId, String status, ResponseCallback responseCallback) {
        fb.collection("Task").document(taskId).update("status", status).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                fb.collection("Task Activity").whereEqualTo("taskId", taskId).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        DocumentSnapshot snapshot = queryDocumentSnapshots.getDocuments().get(0);
                        TaskActivity oldactivity = snapshot.toObject(TaskActivity.class);
                        TaskActivity activity = new TaskActivity();
                        activity.setTaskId(taskId);
                        ;
                        activity.setStatus(status);
                        activity.setUserId(oldactivity.getUserId());
                        activity.setTime_stamp(Timestamp.now());

                        DocumentReference reference = fb.collection("Task Activity").document();
                        activity.setId(reference.getId());
                        reference.set(activity).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                responseCallback.onSuccess("Status Updated", "Successfully");
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
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                responseCallback.onError(e.getLocalizedMessage());
            }
        });


    }

    public void getTaskByStatus(String status, ResponseCallback<List<Task>> responseCallback) {
        fb.collection("Task").whereEqualTo("status", status).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<Task> taskList = new ArrayList<>();
                for (DocumentSnapshot snapshot : queryDocumentSnapshots.getDocuments()) {
                    Task task = snapshot.toObject(Task.class);

                    taskList.add(task);

                }
                responseCallback.onSuccess(taskList, "Successfull");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                responseCallback.onError(e.getLocalizedMessage());
            }
        });
    }
    public void getSingleTaskByStatus(String id,String status,ResponseCallback<Task>responseCallback){
        fb.collection("Task").whereArrayContains("selecteduser",id).whereEqualTo("status",status).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<Task> taskList = new ArrayList<>();
                Task task = null;
                for (DocumentSnapshot snapshot : queryDocumentSnapshots.getDocuments()) {
                    task = snapshot.toObject(Task.class);
                    taskList.add(task);
                }
                responseCallback.onSuccess(task, "Success");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                responseCallback.onError(e.getLocalizedMessage());
            }
        });
    }

}
