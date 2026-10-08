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
    CustomUserView customUser;
    CommentsAdapter commentsAdapter;
    List<Comment> commentList = new ArrayList<>();
    RecyclerView commentRecycler;
    String id;
    TextView title,discript;
    ImageButton imgbtn;
    ImageView materialbtn;
    CommentsViewModel commentsViewModel;
    Spinner spinner,categoryspinner,periorityspinner;
    ArrayAdapter<String>statusAdapter;
    List<Status>statusList=new ArrayList<>();
    StatusViewModel statusViewModel;
    List<String>statusName=new ArrayList<>();
    List<Category>categories=new ArrayList<>();
    List<String>categoryName=new ArrayList<>();

    ArrayAdapter<String>categoryAdapter;
    CategoryViewModel categoryViewModel;
    List<Periority>periorities=new ArrayList<>();
    List<String>periorityName=new ArrayList<>();
    ArrayAdapter<String>periorityAdapter;
    PeriorityViewModel periorityViewModel;
    String statusId;
    String categoryID;
    String periorityId;
    public TaskDisplayFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_task_display, container, false);
statusViewModel=new StatusViewModel(new StatusRepository());
        categoryViewModel=new CategoryViewModel(new CategoryRepository());
        customImage = view.findViewById(R.id.customImage);
        categoryspinner = view.findViewById(R.id.categoryspinner);
        periorityspinner = view.findViewById(R.id.periorityspinner);
        imgbtn = view.findViewById(R.id.imgbtn);
        spinner = view.findViewById(R.id.spinner);
        materialbtn = view.findViewById(R.id.materialbtn);
        title = view.findViewById(R.id.title);
        discript = view.findViewById(R.id.discript);
        periorityViewModel=new PeriorityViewModel(new PeriorityRepository());
ImageView filterBtn=requireActivity().findViewById(R.id.filterBtn);
filterBtn.setVisibility(View.GONE);
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
statusName.add("Status");
statusAdapter=new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item,statusName);
        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(statusAdapter);
        statusViewModel.getList(new ResponseCallback<List<Status>>() {
            @Override
            public void onSuccess(List<Status> data, String message) {
                statusList.clear();
                statusList.addAll(data);
                statusName.clear();
                statusName.add("Status");
                for (Status status:data){
                    statusName.add(status.getName());

                }
                statusAdapter.notifyDataSetChanged();
               setSelectedStatus();
            }

            @Override
            public void onError(String message) {

            }
        });
categoryName.add("Category");
categoryAdapter=new ArrayAdapter<>(getContext(),android.R.layout.simple_spinner_item,categoryName);
categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
categoryspinner.setAdapter(categoryAdapter);
categoryViewModel.getList(new ResponseCallback<List<Category>>() {
    @Override
    public void onSuccess(List<Category> data, String message) {
        categories.clear();
        categories.addAll(data);
        for (Category category:data){
            categoryName.add(category.getName());

        }
        categoryAdapter.notifyDataSetChanged();
        setSelectedCategory();
    }

    @Override
    public void onError(String message) {

    }
});
periorityName.add("Periority");
periorityAdapter=new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item,periorityName);
periorityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
periorityspinner.setAdapter(periorityAdapter);
periorityViewModel.getList(new ResponseCallback<List<Periority>>() {
    @Override
    public void onSuccess(List<Periority> data, String message) {
        periorities.clear();
        periorities.addAll(data);
        for (Periority periority:data){
            periorityName.add(periority.getName());
        }
        periorityAdapter.notifyDataSetChanged();
       setSelectedPeriority();
    }

    @Override
    public void onError(String message) {

    }
});
        Bundle bundle = getArguments();

        if (bundle != null) {

            id = bundle.getString("Id");


            taskViewModel.getSingleTask(id, new ResponseCallback<Task>() {

                @Override
                public void onSuccess(Task data, String message) {
                     title.setText(data.getTitle());
                     discript.setText(data.getDiscription());
                 statusId=data.getStatusId();
                  categoryID=data.getCategoryId();
                          periorityId= data.getPeriorityId();
                          setSelectedStatus();
                          setSelectedCategory();
                          setSelectedPeriority();

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
periorityspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long itemId) {
        String selecteedPeriority=parent.getItemAtPosition(position).toString().trim();
        taskViewModel.updatePeriority(id, selecteedPeriority, new ResponseCallback<Periority>() {
            @Override
            public void onSuccess(Periority data, String message) {

            }

            @Override
            public void onError(String message) {

            }
        });
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
});
                    categoryspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long ItemId) {
                            String selectedCategory=parent.getItemAtPosition(position).toString().trim();
                            taskViewModel.updateCategory(id, selectedCategory, new ResponseCallback<Category>() {
                                @Override
                                public void onSuccess(Category data, String message) {

                                }

                                @Override
                                public void onError(String message) {

                                }
                            });
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                        spinner.setOnItemSelectedListener(
                                new AdapterView.OnItemSelectedListener() {

                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long itemId) {
                                       String selectedSpinner = parent.getItemAtPosition(position).toString().trim();

                                        if (selectedSpinner.equals("Status")) {
                                            return;
                                        }


                                      taskViewModel.updateStatus(id, selectedSpinner, new ResponseCallback() {
                                          @Override
                                          public void onSuccess(Object data, String message) {



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

                commentsViewModel.savereply(id, Id, reply, new ResponseCallback<Comment>() {

                            @Override
                            public void onSuccess(Comment data, String message) {

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

                View view1 = getLayoutInflater().inflate(R.layout.dialogue_input, null);

                TextInputLayout commentedit = view1.findViewById(R.id.commentedit);

                new AlertDialog.Builder(getContext()).setView(view1)

                        .setPositiveButton(
                                "Send",
                                (dialog, which) -> {

                                    String comment = commentedit.getEditText().getText().toString();

                                    commentsViewModel.saveComment(id, comment, new ResponseCallback<Comment>() {

                                                @Override
                                                public void onSuccess(Comment data, String message) {

                                                }

                                                @Override
                                                public void onError(String message) {

                                                }
                                            }
                                    );
                                }
                        )

                        .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())

                        .show();
            }
        });

        return view;
    }
    public void setSelectedStatus(){
        if (statusId!=null){
            for (int i=0;i<statusList.size();i++){
                Status status =statusList.get(i);
                if (statusId.equals(status.getId())){
                    spinner.setSelection(i+1);
                    break;
                }
            }
        }
    }
    public void setSelectedCategory(){
        if (categoryID!=null){
            for (int i=0;i<categories.size();i++){
                Category category=categories.get(i);
                if (categoryID.equals(category.getId())){
                    categoryspinner.setSelection(i+1);
                    break;
                }
            }
        }
    }
    public void setSelectedPeriority(){
        if (periorityId!=null){
            for (int i=0;i<periorities.size();i++){
                Periority periority=periorities.get(i);
                if (periorityId.equals(periority.getId())){
                    periorityspinner.setSelection(i+1);
                    break;
                }
            }
        }
    }
}