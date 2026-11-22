package com.example.taskmanagement;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
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
import com.example.taskmanagement.databinding.FragmentTaskDisplayBinding;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class TaskDisplayFragment extends Fragment {
FragmentTaskDisplayBinding binding;
    TaskViewModel taskViewModel;

    CommentsAdapter commentsAdapter;
    List<Comment> commentList = new ArrayList<>();
    String id;
    CommentsViewModel commentsViewModel;
    ArrayAdapter<String> statusAdapter;
    List<Status> statusList = new ArrayList<>();
    StatusViewModel statusViewModel;
    List<String> statusName = new ArrayList<>();
    List<Category> categories = new ArrayList<>();
    List<String> categoryName = new ArrayList<>();

    ArrayAdapter<String> categoryAdapter;
    CategoryViewModel categoryViewModel;
    List<Periority> periorities = new ArrayList<>();
    List<String> periorityName = new ArrayList<>();
    ArrayAdapter<String> periorityAdapter;
    PeriorityViewModel periorityViewModel;
    String statusId;
    String categoryID;
    String periorityId;
    ApiTaskViewModel apiTaskViewModel;
    StatusApiRepository statusApiRepository;
    PriorityRepository priorityRepository;
    CategoryApiRepository categoryApiRepository;
    List<CheckList>checkLists=new ArrayList<>();
CheckListAdapter checkListAdapter;

    public TaskDisplayFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding=FragmentTaskDisplayBinding.inflate(inflater,container,false);
        statusViewModel = new StatusViewModel(new StatusRepository());
        categoryViewModel = new CategoryViewModel(new CategoryRepository());
        binding.checkListReycler.setHasFixedSize(true);
        binding.checkListReycler.setLayoutManager(new LinearLayoutManager(getContext()));
        checkListAdapter=new CheckListAdapter(getContext(),checkLists);
        binding.checkListReycler.setAdapter(checkListAdapter);

        apiTaskViewModel = new ApiTaskViewModel(new ApiTaskRepository());
        statusApiRepository = new StatusApiRepository();
        priorityRepository = new PriorityRepository();
        categoryApiRepository = new CategoryApiRepository();

        periorityViewModel = new PeriorityViewModel(new PeriorityRepository());
        ImageView filterBtn = requireActivity().findViewById(R.id.filterBtn);
        filterBtn.setVisibility(View.GONE);

      //  binding.customImage.showLoader();
        binding.customUser.showLoader();

        commentsViewModel = new CommentsViewModel(new CommentsRepository());
        taskViewModel = new TaskViewModel(new TaskRepository());

        binding.commentRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.commentRecycler.setNestedScrollingEnabled(false);

        commentsAdapter = new CommentsAdapter(getContext(), commentList);
        binding.commentRecycler.setAdapter(commentsAdapter);
        Bundle bundle = getArguments();
        if (bundle != null) {
            id = bundle.getString("Id");
        }
      //  binding.customImage.setview();
        commentsViewModel.getApiComments(id, new ResponseCallback<List<Comment>>() {
            @Override
            public void onSuccess(List<Comment> data, String message) {
                commentList.addAll(data);
                commentsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(String message) {

            }
        });

apiTaskViewModel.getCheckList(id, new ResponseCallback<List<CheckList>>() {
    @Override
    public void onSuccess(List<CheckList> data, String message) {
        checkLists.clear();
        checkLists.addAll(data);
        binding.checklist.setText("Deliverable Checklists("+data.size()+")");
        checkListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onError(String message) {

    }
});
checkListAdapter.setOnCheckBoxCLick(new CheckListAdapter.OnCheckBoxCLick() {
    @Override
    public void onClick(int Id, int position) {
        apiTaskViewModel.toggleCheclist(id, Id, new ResponseCallback<CheckList>() {
            @Override
            public void onSuccess(CheckList data, String message) {
                CheckList updatedItem = data;

                checkLists.set(position, updatedItem);

                checkListAdapter.notifyItemChanged(position);


            }

            @Override
            public void onError(String message) {

            }
        });
    }
});

binding.additem.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        String item= binding.checklistitem.getText().toString();
       RequestCheckList req=new RequestCheckList();
       req.setTitle(item);
       apiTaskViewModel.creatCheckList(id, req, new ResponseCallback<CheckList>() {
           @Override
           public void onSuccess(CheckList data, String message) {
               binding.checklistitem.setText("");
               checkLists.add(data);
               checkListAdapter.notifyDataSetChanged();
           }

           @Override
           public void onError(String message) {

           }
       });
    }
});

        binding.materialbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getContext(), TaskHistoryActivity.class);
                intent.putExtra("Activityid", id);
                startActivity(intent);
            }
        });
//statusName.add("Status");
//statusAdapter=new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item,statusName);
        //      statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //    spinner.setAdapter(statusAdapter);
        //  statusViewModel.getList(new ResponseCallback<List<Status>>() {
        //    @Override
        //  public void onSuccess(List<Status> data, String message) {
        //    statusList.clear();
        //  statusList.addAll(data);
        //statusName.clear();
        //    statusName.add("Status");
        //  for (Status status:data){
        //    statusName.add(status.getName());

        //      }
        //    statusAdapter.notifyDataSetChanged();
        // setSelectedStatus();
        //   }

        //    @Override
        //  public void onError(String message) {

//            }
        //      });
     //   statusApiRepository.getStatusList(new ResponseCallback<List<String>>() {
       //     @Override
         //   public void onSuccess(List<String> data, String message) {
           //     statusAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, data);
             //   statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
              //  spinner.setAdapter(statusAdapter);
          //  }

          //  @Override
          //  public void onError(String message) {

           // }
      //  });
        categoryName.add("Category");
        categoryAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, categoryName);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.categoryspinner.setAdapter(categoryAdapter);
        categoryViewModel.getList(new ResponseCallback<List<Category>>() {
            @Override
            public void onSuccess(List<Category> data, String message) {
                categories.clear();
                categories.addAll(data);
                for (Category category : data) {
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
        periorityAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, periorityName);
        periorityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.periorityspinner.setAdapter(periorityAdapter);
        periorityViewModel.getList(new ResponseCallback<List<Periority>>() {
            @Override
            public void onSuccess(List<Periority> data, String message) {
                periorities.clear();
                periorities.addAll(data);
                for (Periority periority : data) {
                    periorityName.add(periority.getName());
                }
                periorityAdapter.notifyDataSetChanged();
                setSelectedPeriority();
            }

            @Override
            public void onError(String message) {

            }
        });





            apiTaskViewModel.getSingleTask(id, new ResponseCallback<Task>() {
                @Override
                public void onSuccess(Task data, String message) {
                    Log.e("API ", "onSuccess: "+data.getTitle() );
                    Log.e("API ", "onSuccess: "+data.getDescription() );
                    binding.title.setText(data.getTitle() + "");
                    binding.discript.setText(data.getDescription() + "");


                }

                @Override
                public void onError(String message) {

                }
            });

            //   taskViewModel.getSingleTask(id, new ResponseCallback<Task>() {

            //     @Override
            //   public void onSuccess(Task data, String message) {
            //      title.setText(data.getTitle());
            //    discript.setText(data.getDiscription());

            //    Timestamp time=data.getTimes_tamp();
            //  Date date1=time.toDate();
            //    SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            //    date.setText("Date : "+sdf.format(date1));
            //  statusId=data.getStatusId();
            //   categoryID=data.getCategoryId();
            //         periorityId= data.getPeriorityId();
            //       setSelectedStatus();
            //     setSelectedCategory();
            //   setSelectedPeriority();

            //      if (data.getSelectedImges() != null) {
            //        customImage.hideLoader();
            //      customImage.uriList.clear();
            //    customImage.uriList.addAll(data.getSelectedImges());
            //  customImage.imageAdpter.notifyDataSetChanged();
            //   }

            // if (data.getSelecteduser() != null) {
            //   customUser.hideLoader();
            // customUser.showUsersByIds(data.getSelecteduser());
            //  }

            //   }

            // @Override
            //     public void onError(String message) {

            //   }
            //  });


//            commentsViewModel.getList(id, new ResponseCallback<List<Comment>>() {

            //              @Override
            //            public void onSuccess(List<Comment> data, String message) {

            //              commentList.clear();
            //            commentList.addAll(data);
            //          commentsAdapter.notifyDataSetChanged();
            //    }

            //  @Override
            //public void onError(String message) {

            //  }
            //  });

            taskViewModel.getTaskActivity(id, new ResponseCallback<List<TaskActivity>>() {

                @Override
                public void onSuccess(List<TaskActivity> data, String message) {
                    binding.periorityspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long itemId) {
                            String selecteedPeriority = parent.getItemAtPosition(position).toString().trim();
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
                    binding.categoryspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long ItemId) {
                            String selectedCategory = parent.getItemAtPosition(position).toString().trim();
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
                    binding.spinner.setOnItemSelectedListener(
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

                    binding.spinner.setEnabled(false);
                }
            });


       // binding.customImage.setOnImageClick(new CustomImagView.OnImageClick() {

         //   @Override
           // public void OnClick(String imagUrl) {

             //   Intent intent =
               //         new Intent(getContext(), PhotoZoomActivity.class);

               // intent.putExtra("ImageUrl", imagUrl);

               // startActivity(intent);
          //  }
      //  });


       commentsAdapter.setOnBtnClick(new CommentsAdapter.OnBtnClick() {
           @Override
           public void Onclick(int Id, int positon) {
               commentsViewModel.DeleteComment(id, Id, new ResponseCallback<Comment>() {
                   @Override
                   public void onSuccess(Comment data, String message) {
                       commentList.remove(positon);
                       commentsAdapter.notifyItemRemoved(positon);
                   }

                   @Override
                   public void onError(String message) {

                   }
               });
           }
       });

        binding.imgbtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                View view1 = getLayoutInflater().inflate(R.layout.dialogue_input, null);

                TextInputLayout commentedit = view1.findViewById(R.id.commentedit);

                new AlertDialog.Builder(getContext()).setView(view1)

                        .setPositiveButton(
                                "Send",
                                (dialog, which) -> {

                                    String comment = commentedit.getEditText().getText().toString();
                                    RequestComment requestComment=new RequestComment();
                                    requestComment.setComment(comment);
                                    commentsViewModel.creatApiComment(requestComment, id, new ResponseCallback<Comment>() {
    @Override
    public void onSuccess(Comment data, String message) {
        commentList.add(data);
        categoryAdapter.notifyDataSetChanged();
    }

    @Override
    public void onError(String message) {

    }
});
                                //    commentsViewModel.saveComment(id, comment, new ResponseCallback<Comment>() {

                                  //              @Override
                                    //            public void onSuccess(Comment data, String message) {

                                      //          }

                                        //        @Override
                                          //      public void onError(String message) {

                                              //  }
                                          //  }
                                   // );
                                }
                       )

                        .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())

                        .show();
            }
        });

        return binding.getRoot();
    }

    public void setSelectedStatus() {
        if (statusId != null) {
            for (int i = 0; i < statusList.size(); i++) {
                Status status = statusList.get(i);
                if (statusId.equals(status.getId())) {
                    binding.spinner.setSelection(i + 1);
                    break;
                }
            }
        }
    }

    public void setSelectedCategory() {
        if (categoryID != null) {
            for (int i = 0; i < categories.size(); i++) {
                Category category = categories.get(i);
                if (categoryID.equals(category.getId())) {
                    binding.categoryspinner.setSelection(i + 1);
                    break;
                }
            }
        }
    }

    public void setSelectedPeriority() {
        if (periorityId != null) {
            for (int i = 0; i < periorities.size(); i++) {
                Periority periority = periorities.get(i);
                if (periorityId.equals(periority.getId())) {
                    binding.periorityspinner.setSelection(i + 1);
                    break;
                }
            }
        }
    }
}