package com.example.taskmanagement;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.taskmanagement.callback.ResponseCallback;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;


public class TaskDisplayFragment extends Fragment {

    TaskViewModel taskViewModel;
    CustomImagView customImage;
    String[] status = {" Status", "To do","In Progess","In Review","Completed"};
    CustomUserView customUser;
    CommentsAdapter commentsAdapter;
    List<Comment> commentList = new ArrayList<>();
    RecyclerView commentRecycler;
    String id;
    TextView title,discript;
    ImageButton imgbtn;
    ImageView materialbtn;
    CommentsViewModel commentsViewModel;
    Spinner spinner;
    public TaskDisplayFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_task_display, container, false);

        customImage = view.findViewById(R.id.customImage);
        imgbtn = view.findViewById(R.id.imgbtn);
        spinner = view.findViewById(R.id.spinner);
        materialbtn = view.findViewById(R.id.materialbtn);
        title = view.findViewById(R.id.title);
        discript = view.findViewById(R.id.discript);

        commentRecycler = view.findViewById(R.id.commentRecycler);
        customUser = view.findViewById(R.id.customUser);
        customImage.showLoader();
customUser.showLoader();

        commentsViewModel = new CommentsViewModel(new CommentsRepository());
        taskViewModel = new TaskViewModel(new TaskRepository());

        commentRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        commentRecycler.setNestedScrollingEnabled(false);

        commentsAdapter = new CommentsAdapter(getContext(), commentList);
        commentRecycler.setAdapter(commentsAdapter);



        customImage.setview();
        customUser.setview();
materialbtn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

Intent intent=new Intent(getContext(), TaskHistoryActivity.class);
intent.putExtra("Activityid",id);
startActivity(intent);
    }
});

        ArrayAdapter<String> arrayAdapter =
                new ArrayAdapter<>(
                        getContext(),
                        android.R.layout.simple_spinner_item,
                        status
                );

        arrayAdapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item
        );

        spinner.setAdapter(arrayAdapter);

        Bundle bundle = getArguments();

        if (bundle != null) {

            id = bundle.getString("Id");



            taskViewModel.getSingleTask(id, new ResponseCallback<Task>() {

                @Override
                public void onSuccess(Task data, String message) {
                     title.setText(data.getTitle());
                     discript.setText(data.getDiscription());

                    if (data.getSelectedImges() != null) {
                        customImage.hideLoader();
                        customImage.uriList.clear();
                        customImage.uriList.addAll(data.getSelectedImges());
                        customImage.imageAdpter.notifyDataSetChanged();
                    }

                    if (data.getSelecteduser() != null) {
                        customUser.hideLoader();
                        customUser.showUsersByIds(data.getSelecteduser());
                    }
                   String selectedposition=data.getStatus();
                    int position=arrayAdapter.getPosition(selectedposition);

                    spinner.setSelection(position);

                }

                @Override
                public void onError(String message) {

                }
            });



            commentsViewModel.getList(id, new ResponseCallback<List<Comment>>() {

                @Override
                public void onSuccess(List<Comment> data, String message) {

                    commentList.clear();
                    commentList.addAll(data);
                    commentsAdapter.notifyDataSetChanged();
                }

                @Override
                public void onError(String message) {

                }
            });



            taskViewModel.getTaskActivity(id, new ResponseCallback<List<TaskActivity>>() {

                @Override
                public void onSuccess(List<TaskActivity> data, String message) {


                        spinner.setOnItemSelectedListener(
                                new AdapterView.OnItemSelectedListener() {

                                    @Override
                                    public void onItemSelected(
                                            AdapterView<?> parent,
                                            View view,
                                            int position,
                                            long itemId) {
                                       String selectedSpinner =
                                                parent.getItemAtPosition(position)
                                                        .toString()
                                                        .trim();

                                        if (selectedSpinner.equals("Status")) {
                                            return;
                                        }


                                      taskViewModel.updateStatus(id, selectedSpinner, new ResponseCallback() {
                                          @Override
                                          public void onSuccess(Object data, String message) {
                                              spinner.setEnabled(true);


                                          }

                                          @Override
                                          public void onError(String message) {

                                          }
                                      });
                                    }

                                    @Override
                                    public void onNothingSelected(
                                            AdapterView<?> parent) {

                                    }
                                }
                        );


                }

                @Override
                public void onError(String message) {

                    spinner.setEnabled(false);
                }
            });
        }


        customImage.setOnImageClick(new CustomImagView.OnImageClick() {

            @Override
            public void OnClick(String imagUrl) {

                Intent intent =
                        new Intent(getContext(), PhotoZoomActivity.class);

                intent.putExtra("ImageUrl", imagUrl);

                startActivity(intent);
            }
        });


        commentsAdapter.setOnBtnClick(new CommentsAdapter.OnBtnClick() {

            @Override
            public void Onclick(String reply, String Id) {

                commentsViewModel.savereply(
                        id,
                        Id,
                        reply,
                        new ResponseCallback<Comment>() {

                            @Override
                            public void onSuccess(
                                    Comment data,
                                    String message) {

                            }

                            @Override
                            public void onError(String message) {

                            }
                        }
                );
            }
        });


        imgbtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                View view1 =
                        getLayoutInflater().inflate(
                                R.layout.dialogue_input,
                                null
                        );

                TextInputLayout commentedit =
                        view1.findViewById(R.id.commentedit);

                new AlertDialog.Builder(getContext())
                        .setView(view1)

                        .setPositiveButton(
                                "Send",
                                (dialog, which) -> {

                                    String comment =
                                            commentedit
                                                    .getEditText()
                                                    .getText()
                                                    .toString();

                                    commentsViewModel.saveComment(
                                            id,
                                            comment,
                                            new ResponseCallback<Comment>() {

                                                @Override
                                                public void onSuccess(
                                                        Comment data,
                                                        String message) {

                                                }

                                                @Override
                                                public void onError(
                                                        String message) {

                                                }
                                            }
                                    );
                                }
                        )

                        .setNegativeButton(
                                "Cancel",
                                (dialog, which) -> dialog.dismiss()
                        )

                        .show();
            }
        });


        return view;
    }
}