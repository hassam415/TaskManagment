package com.example.taskmanagement;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.taskmanagement.callback.ResponseCallback;
import com.example.taskmanagement.databinding.FragmentTaskBinding;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class TaskFragment extends Fragment {
FragmentTaskBinding binding;


    TaskViewModel taskViewModel;

    USerViewModel uSerViewModel;
    List<User> userList = new ArrayList<>();

    String selectedDate;
    private ActivityResultLauncher<String> gallerLauncher;
    private ActivityResultLauncher<Uri> cameraLauncher;
    private Uri cameraUri;
    CustomImagView customImagView;

    AuthViewModel authViewModel;
    List<String> userListspinner = new ArrayList<>();
    List<Integer> userID = new ArrayList<>();
    ArrayAdapter<String> reiverAdpater;

    List<Team> teamList = new ArrayList<>();
    ArrayAdapter<Team> teamAdapter;
    ArrayAdapter<String> statusAdapter;
    List<Status> statusList = new ArrayList<>();
    StatusViewModel statusViewModel;
    List<String> statusName = new ArrayList<>();
    List<Category> categories = new ArrayList<>();
    List<String> categoryName = new ArrayList<>();
List<Project>projectList=new ArrayList<>();
    ArrayAdapter<String> categoryAdapter;
    CategoryViewModel categoryViewModel;
    List<Periority> periorities = new ArrayList<>();
    List<String> periorityName = new ArrayList<>();
    ArrayAdapter<String> periorityAdapter;
    PeriorityViewModel periorityViewModel;
    ApiTaskViewModel apiTaskViewModel;

    StatusApiRepository statusApiRepository;
    PriorityRepository priorityRepository;
    CategoryApiRepository categoryApiRepository;
    String Due_Date;
    String editCategory;
    String editPriority;
    String editTeam;
    int editReview ,selectedProjectId,getProjecctID;
    String id;
    private ArrayList<Integer> selectedUserAssignes = new ArrayList<>();
    ProjectViewModel projectViewModel;

    public TaskFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding=FragmentTaskBinding.inflate(inflater,container,false);


        authViewModel = new AuthViewModel(new AuthRepository());

//customImagView=view.findViewById(R.id.customImageView);

        statusApiRepository = new StatusApiRepository();
        priorityRepository = new PriorityRepository();
        categoryApiRepository = new CategoryApiRepository();
        projectViewModel=new ProjectViewModel(new ProjectRepository());
        categoryViewModel = new CategoryViewModel(new CategoryRepository());
        periorityViewModel = new PeriorityViewModel(new PeriorityRepository());
        statusViewModel = new StatusViewModel(new StatusRepository());
        taskViewModel = new TaskViewModel(new TaskRepository());
        uSerViewModel = new USerViewModel(new UserRepository());
        apiTaskViewModel = new ApiTaskViewModel(new ApiTaskRepository());
        binding.startDate.setFocusable(false);
        binding.startDate.setClickable(true);

        binding.dueDate.setFocusable(false);
        binding.dueDate.setClickable(true);

        binding.startDate.setOnClickListener(v -> openDatePicker(true));

        binding.dueDate.setOnClickListener(v -> openDatePicker(false));
        Bundle bundle = getArguments();
        if (bundle != null) {
            id = bundle.getString("taskId");
            if (id != null) {
                String titl1e = bundle.getString("title");
                String discrip = bundle.getString("discription");
                editCategory = bundle.getString("category");
                String hourqs = bundle.getString("hourss");
                String startDatee = bundle.getString("startDate");
                String dueDatee = bundle.getString("dueDate");
                editReview = bundle.getInt("reviewrID", 0);
                Log.d("REVIEWER_API", "Bundle reviewer: " + editReview);
                editPriority = bundle.getString("priority");
                getProjecctID=bundle.getInt("projectId");
                editTeam = bundle.getString("team");
                selectedUserAssignes =
                        bundle.getIntegerArrayList("assignessId");
                binding.title.getEditText().setText(titl1e);

                binding.discription.getEditText().setText(discrip);
                binding.hours.getEditText().setText(hourqs);
                binding.startDate.setText(startDatee);
                binding.dueDate.setText(dueDatee);
            }
        }
        reiverAdpater = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, userListspinner);
        reiverAdpater.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.revierSpiner.setAdapter(reiverAdpater);
        authViewModel.getUserList(new AuthRepository.TaskCallBack() {
            @Override
            public void OnSuccess(JsonObject jsonReader) {


                JsonArray array = jsonReader.getAsJsonArray("data");
                for (JsonElement jsonElement : array) {
                    JsonObject user = jsonElement.getAsJsonObject();
                    String name = user.get("name").getAsString();
                    int id = user.get("id").getAsInt();
                    userID.add(id);
                    userListspinner.add(name);
                }
                reiverAdpater.notifyDataSetChanged();

                if (editReview != 0) {

                    boolean found = false;

                    for (int i = 0; i < userID.size(); i++) {

                        if (userID.get(i) == editReview) {

                            binding.revierSpiner.setSelection(i);
                            found = true;

                            Log.d("REVIEWER_API",
                                    "Selected reviewer: "
                                            + userListspinner.get(i)
                                            + ", ID: " + userID.get(i));

                            break;
                        }
                    }

                    if (!found) {
                        Log.e("REVIEWER_API",
                                "Reviewer ID users list mein nahi mili: "
                                        + editReview);
                    }
                }
            }

            @Override
            public void Onerror(String message) {

            }
        });
        projectViewModel.getProjectList(new ResponseCallback<List<Project>>() {
            @Override
            public void onSuccess(List<Project> data, String message) {

                projectList.clear();
                projectList.addAll(data);

                for (Project project : projectList) {
                    Log.d("PROJECT_CHECK",
                            "Project Name: " + project.getName()
                                    + " | Project ID: " + project.getId());
                }

                ArrayAdapter<Project> arrayAdapter = new ArrayAdapter<>(
                        requireContext(),
                        R.layout.dropdown,
                        projectList
                );

                binding.projectSpinner.setAdapter(arrayAdapter);

                for (Project project : projectList) {
                    if (project.getId() == getProjecctID) {
                        binding.projectSpinner.setText(project.getName(), false);

                        Log.d("PROJECT_CHECK",
                                "EDIT PROJECT FOUND: "
                                        + project.getName()
                                        + " | ID: " + project.getId());
                        break;
                    }
                }
            }

            @Override
            public void onError(String message) {

            }
        });
        //    statusName.add("Status");
        //  statusAdapter=new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item,statusName);
        // statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //  spinner.setAdapter(statusAdapter);

        //   statusViewModel.getList(new ResponseCallback<List<Status>>() {
        //     @Override
        //   public void onSuccess(List<Status> data, String message) {
        //     statusList.clear();
        //   statusList.addAll(data);
        // statusName.clear();
        //   statusName.add("Status");
        // for (Status status:data){
        //   statusName.add(status.getName());

        //   }
        //   statusAdapter.notifyDataSetChanged();
        //  }

        //  @Override
        //  public void onError(String message) {

        // }
        // });
        teamList = new ArrayList<>();

        teamList.add(new Team(-1, "Select Team"));

        teamAdapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_spinner_item,
                teamList
        );

        teamAdapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item
        );

        binding.team.setAdapter(teamAdapter);

        apiTaskViewModel.getTeam(new ApiTaskRepository.TaskCallBack() {
            @Override
            public void OnSuccess(JsonObject jsonReader) {

                if (!jsonReader.has("data")
                        || !jsonReader.get("data").isJsonArray()) {
                    Log.e("TEAM_API", "Teams data array nahi mila");
                    return;
                }

                JsonArray array = jsonReader.getAsJsonArray("data");

                teamList.clear();
                teamList.add(new Team(-1, "Select Team"));

                for (JsonElement element : array) {

                    JsonObject teamObject = element.getAsJsonObject();

                    int teamId = teamObject.get("id").getAsInt();
                    String teamName = teamObject.get("name").getAsString();

                    Team teamItem = new Team(teamId, teamName);
                    teamList.add(teamItem);

                    Log.d("TEAM_API",
                            "Team name: " + teamName + ", ID: " + teamId);
                }

                teamAdapter.notifyDataSetChanged();
                if (editTeam != null) {
                    for (int i = 0; i < teamList.size(); i++) {
                        Team item = teamList.get(i);
                        if (item != null) {
                            binding.team.setSelection(i);
                        }

                    }
                }

            }

            @Override
            public void Onerror(String message) {
                Log.e("TEAM_API", "Error: " + message);
            }
        });
        //  statusApiRepository.getStatusList(new ResponseCallback<List<String>>() {
        //    @Override
        //  public void onSuccess(List<String> data, String message) {

        //    statusAdapter=new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item,data);
        //   statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //  spinner.setAdapter(statusAdapter);
        //   }

        //   @Override
        // public void onError(String message) {

        //  }
        // });
        categoryApiRepository.getCategoryList(new ResponseCallback<List<String>>() {

            @Override
            public void onSuccess(List<String> data, String message) {

                categoryAdapter = new ArrayAdapter<>(
                        requireContext(),
                        android.R.layout.simple_spinner_item,
                        data
                );

                categoryAdapter.setDropDownViewResource(
                        android.R.layout.simple_spinner_dropdown_item
                );

                binding.categoryspinner.setAdapter(categoryAdapter);

                // Adapter load hone ke baad category select karo
                if (editCategory != null) {

                    boolean found = false;

                    for (int i = 0; i < data.size(); i++) {

                        String item = data.get(i);

                        if (item != null &&
                                item.trim().equalsIgnoreCase(editCategory.trim())) {

                            binding.categoryspinner.setSelection(i);
                            found = true;

                            Log.d("EDIT_TASK",
                                    "Category selected: " + item
                                            + ", position: " + i);
                            break;
                        }
                    }

                    if (!found) {
                        Log.e("EDIT_TASK",
                                "Category list mein nahi mili: " + editCategory);
                        Log.d("EDIT_TASK", "Available categories: " + data);
                    }
                }
            }

            @Override
            public void onError(String message) {
                Log.e("CATEGORY_API", "Error: " + message);
            }
        });
//        categoryName.add("Category");
        //      categoryAdapter=new ArrayAdapter<>(getContext(),android.R.layout.simple_spinner_item,categoryName);
        //    categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //  categoryspinner.setAdapter(categoryAdapter);
        //   categoryViewModel.getList(new ResponseCallback<List<Category>>() {
        //     @Override
        //   public void onSuccess(List<Category> data, String message) {
        //     categories.clear();
        //   categories.addAll(data);
        // for (Category category:data){
        //   categoryName.add(category.getName());

        //    }
        //  categoryAdapter.notifyDataSetChanged();
        //   }

        // @Override
        //  public void onError(String message) {

        //  }
        //  });

        //    periorityName.add("Periority");
        //  periorityAdapter=new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item,periorityName);
        //   periorityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // periorityspinner.setAdapter(periorityAdapter);
        //  periorityViewModel.getList(new ResponseCallback<List<Periority>>() {
        //    @Override
        //  public void onSuccess(List<Periority> data, String message) {
        //    periorities.clear();
        //  periorities.addAll(data);
        //for (Periority periority:data){
        //  periorityName.add(periority.getName());
        //   }
        //  periorityAdapter.notifyDataSetChanged();
        //  }

        //  @Override
        //public void onError(String message) {

        //  }
        // });
        priorityRepository.getPriority(new ResponseCallback<List<String>>() {
            @Override
            public void onSuccess(List<String> data, String message) {
                periorityAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, data);
                periorityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                binding.periorityspinner.setAdapter(periorityAdapter);
                if (editPriority != null) {
                    for (int i = 0; i < data.size(); i++) {
                        String item = data.get(i);
                        if (item != null &&
                                item.trim().equalsIgnoreCase(editPriority.trim())) {

                            binding.periorityspinner.setSelection(i);
                        }
                    }
                }
            }

            @Override
            public void onError(String message) {

            }
        });
        cameraLauncher = registerForActivityResult(new ActivityResultContracts.TakePicture(), result -> {
            if (result && cameraUri != null) {
                customImagView.uriList.add(cameraUri.toString());
                customImagView.imageAdpter.notifyDataSetChanged();
            }
        });
        //     customImagView.setOnCameraClick(() -> {

        //       ContentValues values = new ContentValues();

        //     values.put(
        //           MediaStore.Images.Media.DISPLAY_NAME,
        //         "task_" + System.currentTimeMillis() + ".jpg"
        //   );

        //  values.put(
        //        MediaStore.Images.Media.MIME_TYPE,
        //      "image/jpeg"
        //   );

        // cameraUri = requireContext()
        //       .getContentResolver()
        //     .insert(
        //           MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
        //         values
        //  );

        //  if (cameraUri != null) {
        //    cameraLauncher.launch(cameraUri);
        //  }
        //  });
        //gallerLauncher = registerForActivityResult(
        //      new ActivityResultContracts.GetMultipleContents(),
        //    uris -> {

        //      if (uris != null) {

        //        customImagView.uriList.clear();
        //      for (Uri uri:uris){
        //        customImagView.uriList.add(uri.toString());
        //  }


        //customImagView.imageAdpter.notifyDataSetChanged();
        //  }
        //  }
        //  );
        //customImagView.setOnGallerClick(new CustomImagView.OnGallerClick() {
        //  @Override
        //public void onClick() {
        //  gallerLauncher.launch("image/*");
        //  }
        // });
        // uSerViewModel.getUser(new ResponseCallback<List<User>>() {
        //   @Override
        // public void onSuccess(List<User> data, String message) {
        //   userList.clear();
        // customUser.userList.addAll(data);

        //  }

        //  @Override
        //public void onError(String message) {

        //  }
        // });


        authViewModel.getUserList(new AuthRepository.TaskCallBack() {
            @Override
            public void OnSuccess(JsonObject jsonReader) {
                Log.d("ASSIGNEE_API", "Full response: " + jsonReader);

                if (!jsonReader.has("data") ||
                        !jsonReader.get("data").isJsonArray()) {
                    Log.e("ASSIGNEE_API", "Response mein data array nahi hai");
                    return;
                }

                JsonArray array = jsonReader.getAsJsonArray("data");

                Log.d("ASSIGNEE_API", "API array size: " + array.size());

                List<User> users = new ArrayList<>();

                for (JsonElement element : array) {
                    User user = new Gson().fromJson(element, User.class);

                    if (user != null) {
                        users.add(user);
                        Log.d("ASSIGNEE_API",
                                "Parsed user: " + user.getName());
                    }
                }

                Log.d("ASSIGNEE_API",
                        "Parsed users count: " + users.size());
                binding.customUser.setUsers(users);

                if (selectedUserAssignes == null) {
                    selectedUserAssignes = new ArrayList<>();
                }

                Log.d("ASSIGNEE_EDIT",
                        "IDs from TaskAdapter: " + selectedUserAssignes);

                binding.customUser.setSelectedUserIds(selectedUserAssignes);

                Log.d("ASSIGNEE_API",
                        "setUsers() called with: " + users.size());
            }

            @Override
            public void Onerror(String message) {
                Log.e("ASSIGNEE_API", "API error: " + message);
            }
        });


        binding.savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title1 = binding.title.getEditText().getText().toString();
                String discpt = binding.discription.getEditText().getText().toString();
                String projectname = binding.projectSpinner.getText().toString().trim();

                selectedProjectId = 0;

                for (Project project : projectList) {

                    if (project.getName().equalsIgnoreCase(projectname)) {

                        selectedProjectId = project.getId();

                    }
                }
                        if (title1.isEmpty()) {
                    Toast.makeText(getContext(), " Please Create title ", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (discpt.isEmpty()) {
                    Toast.makeText(getContext(), " Please Create discription ", Toast.LENGTH_SHORT).show();
                    return;
                }

//                String statusPosition = spinner.getSelectedItem().toString();
                String categoryPosition = binding.categoryspinner.getSelectedItem().toString();
                String periorityPosition = binding.periorityspinner.getSelectedItem().toString();

                //     if (statusPosition <= 0) {
                //       Toast.makeText(getContext(), "Please Select Status", Toast.LENGTH_SHORT).show();
                //     return;
                //   }

                // if (categoryPosition <= 0) {
                //   Toast.makeText(getContext(), "Please Select Category", Toast.LENGTH_SHORT).show();
                // return;
                //     }

                //   if (periorityPosition <= 0) {
                //     Toast.makeText(getContext(), "Please Select Periority", Toast.LENGTH_SHORT).show();
                //    return;
                //   }

//                String statusId = statusList.get(statusPosition - 1).getId();
                //              String categoryId = categories.get(categoryPosition - 1).getId();
                //            String periorityId = periorities.get(periorityPosition - 1).getId();

                Team selectedTeam = (Team) binding.team.getSelectedItem();

                if (selectedTeam == null || selectedTeam.getId() == -1) {
                    Toast.makeText(requireContext(),
                            "Please select a team",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                int selectedTeamId = selectedTeam.getId();
                int selectedRevier = binding.revierSpiner.getSelectedItemPosition();
                int reviwerID = userID.get(selectedRevier);
                Log.d("TEAM_API", "Selected Team ID = " + selectedTeamId);
                Log.d("TEAM_API", "Selected Team Name = " + selectedTeam.getName());


                String estimatTime = binding.hours.getEditText().getText().toString().trim();
                Log.e("API TASK", "onClick: " + estimatTime);
                binding.Loader.setVisibility(View.VISIBLE);
                selectedDate = binding.startDate.getText().toString().trim();
                Due_Date = binding.dueDate.getText().toString().trim();

                Log.d("TASK_DATE", "Start date before API = " + selectedDate);
                Log.d("TASK_DATE", "Due date before API = " + Due_Date);
                Task taskReq = new Task();
                taskReq.setTitle(title1);
                taskReq.setEstimated_hours(estimatTime);
                taskReq.setDescription(discpt);
                taskReq.setTeamId(selectedTeamId);
                taskReq.setPeriority(periorityPosition);
                taskReq.setCategory(categoryPosition);
                taskReq.setReviewer_id(reviwerID);
                taskReq.setStart_date(selectedDate);
                taskReq.setDue_date(Due_Date);
                taskReq.setProject_id(selectedProjectId);

                Log.d("TASK_API", "Selected priority: "
                        + binding.periorityspinner.getSelectedItem());

                Log.d("TASK_API", "Priority value: "
                        + taskReq.getPeriority());

                List<Integer> selectedUser = binding.customUser.getSelectedUserIds();


                String titleValue =
                        binding.title.getEditText().getText().toString().trim();

                String priorityValue =
                        binding.periorityspinner.getSelectedItem().toString().toLowerCase();

                String descriptionValue =
                        binding.discription.getEditText().getText().toString().trim();

                String dueDateValue =
                        binding.dueDate.getText().toString().trim();
                String startDateValue = binding.startDate.getText().toString().trim();
                binding.Loader.setVisibility(View.VISIBLE);
                UpdateTask request = new UpdateTask(titleValue, descriptionValue, categoryPosition, priorityValue, selectedTeamId, estimatTime, startDateValue, dueDateValue, reviwerID, selectedUser);
                if (id != null) {

                    // Existing task ko update karo
                    apiTaskViewModel.updateTask(id, request, new ResponseCallback<Task>() {
                        @Override
                        public void onSuccess(Task data, String message) {

                            binding.Loader.setVisibility(View.GONE);
//                            Log.e("API TASK", "OnSuccess: " + jsonReader);
                            Toast.makeText(getContext(), "Task Updated", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onError(String message) {
                            binding.Loader.setVisibility(View.GONE);

                        }
                    });

                } else {

                    // Naya task create karo
                    apiTaskViewModel.creatTask(taskReq, selectedUser, new ResponseCallback<Task>() {
                        @Override
                        public void onSuccess(Task data, String message) {
                            binding.Loader.setVisibility(View.GONE);
                            Toast.makeText(getContext(), "Task Created", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onError(String message) {
                            binding.Loader.setVisibility(View.GONE);

                        }
                    });
                }
                //  taskViewModel.saveData(title1, discpt,statusId,categoryId,periorityId,  customUser.list,customImagView.uriList,new ResponseCallback<Task>() {
                //    @Override
                //  public void onSuccess(Task data, String message) {
                //    Loader.setVisibility(View.GONE);
                //  title.getEditText().setText("");
                //    discription.getEditText().setText("");
                //  customUser.selecteduser.clear();
                //    customImagView.uriList.clear();
                //  Toast.makeText(getContext(), "Task is Created", Toast.LENGTH_SHORT).show();

                //    }

                //  @Override
                //    public void onError(String message) {
                //      Loader.setVisibility(View.GONE);
                //   }
                //  });
            }
        });
        return binding.getRoot();

    }

    private void openDatePicker(boolean isStartDate) {
        Calendar calendar = Calendar.getInstance();

        DatePickerDialog dialog = new DatePickerDialog(
                requireContext(),
                (datePicker, year, month, day) -> {
                    String date = String.format(
                            Locale.US,
                            "%04d-%02d-%02d",
                            year, month + 1, day
                    );

                    if (isStartDate) {
                        selectedDate = date;
                        binding.startDate.setText(date);
                    } else {
                        Due_Date = date;
                        binding.dueDate.setText(date);
                    }

                    Log.d("TASK_DATE", "Start date = " + selectedDate);
                    Log.d("TASK_DATE", "Due date = " + Due_Date);
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );

        dialog.show();
    }
}