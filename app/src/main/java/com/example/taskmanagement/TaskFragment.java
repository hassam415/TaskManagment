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

TextInputLayout title,discription,hours;
Button savebtn;
TextInputEditText startDate,dueDate;
TaskViewModel taskViewModel;
LinearLayout Loader;
USerViewModel uSerViewModel;
List<User>userList=new ArrayList<>();

    String selectedDate;
private ActivityResultLauncher<String>gallerLauncher;
private ActivityResultLauncher<Uri> cameraLauncher;
private Uri cameraUri;
CustomImagView customImagView;
CustomUserView customUser;
AuthViewModel authViewModel;
    List<Team> teamList = new ArrayList<>();
    ArrayAdapter<Team> teamAdapter;
    Spinner spinner,categoryspinner,periorityspinner,team;
    ArrayAdapter<String>statusAdapter;
    List<Status>statusList=new ArrayList<>();
    StatusViewModel statusViewModel;
    List<String>statusName=new ArrayList<>();
    List<Category>categories=new ArrayList<>();
    List<String>categoryName=new ArrayList<>();

    ArrayAdapter<String> categoryAdapter;
    CategoryViewModel categoryViewModel;
    List<Periority>periorities=new ArrayList<>();
    List<String>periorityName=new ArrayList<>();
    ArrayAdapter<String>periorityAdapter;
    PeriorityViewModel periorityViewModel;
    ApiTaskViewModel apiTaskViewModel;

    StatusApiRepository statusApiRepository;
    PriorityRepository priorityRepository ;
    CategoryApiRepository categoryApiRepository;
    String Due_Date ;
    public TaskFragment() {

    }





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_task,container,false);
        spinner=view.findViewById(R.id.spinner);
        categoryspinner=view.findViewById(R.id.categoryspinner);
        hours=view.findViewById(R.id.hours);
        startDate=view.findViewById(R.id.startDate);
        team=view.findViewById(R.id.team);
        dueDate=view.findViewById(R.id.dueDate);
        periorityspinner=view.findViewById(R.id.periorityspinner);
        title=view.findViewById(R.id.title);
        Loader=view.findViewById(R.id.Loader);
        authViewModel=new AuthViewModel(new AuthRepository());
        discription=view.findViewById(R.id.discription);
customImagView=view.findViewById(R.id.customImageView);
        customUser=view.findViewById(R.id.customUser);
        statusApiRepository=new StatusApiRepository();
        priorityRepository=new PriorityRepository();
        categoryApiRepository=new CategoryApiRepository();
        savebtn=view.findViewById(R.id.savebtn);
        categoryViewModel=new CategoryViewModel(new CategoryRepository());
        periorityViewModel=new PeriorityViewModel(new PeriorityRepository());
        statusViewModel=new StatusViewModel(new StatusRepository());
        taskViewModel=new TaskViewModel(new TaskRepository());
        uSerViewModel=new USerViewModel(new UserRepository());
        apiTaskViewModel=new ApiTaskViewModel(new ApiTaskRepository());
        startDate.setFocusable(false);
        startDate.setClickable(true);

        dueDate.setFocusable(false);
        dueDate.setClickable(true);

        startDate.setOnClickListener(v -> openDatePicker(true));

        dueDate.setOnClickListener(v -> openDatePicker(false));
      Bundle bundle=getArguments();
      String id=bundle.getString("taskId");
      if (id!=null){
          String titl1e=bundle.getString("title");
          String discrip=bundle.getString("discription");
          String category=bundle.getString("category");
          String hourqs=bundle.getString("hourss");
          title.getEditText().setText(titl1e);
                  discription.getEditText().setText(discrip);
                  hours.getEditText().setText(hourqs);

      }

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

        team.setAdapter(teamAdapter);

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
                team.setSelection(0);
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
        categoryAdapter=new ArrayAdapter<>(getContext(),android.R.layout.simple_spinner_item,data);
            categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
          categoryspinner.setAdapter(categoryAdapter);
    }

    @Override
    public void onError(String message) {

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
                  periorityAdapter=new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item,data);
                   periorityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                 periorityspinner.setAdapter(periorityAdapter);
            }

            @Override
            public void onError(String message) {

            }
        });
        cameraLauncher=registerForActivityResult(new ActivityResultContracts.TakePicture(),result ->{
            if (result&&cameraUri!=null){
                customImagView.uriList.add(cameraUri.toString());
                customImagView.imageAdpter.notifyDataSetChanged();
            }
        } );
        customImagView.setOnCameraClick(() -> {

            ContentValues values = new ContentValues();

            values.put(
                    MediaStore.Images.Media.DISPLAY_NAME,
                    "task_" + System.currentTimeMillis() + ".jpg"
            );

            values.put(
                    MediaStore.Images.Media.MIME_TYPE,
                    "image/jpeg"
            );

            cameraUri = requireContext()
                    .getContentResolver()
                    .insert(
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                            values
                    );

            if (cameraUri != null) {
                cameraLauncher.launch(cameraUri);
            }
        });
        gallerLauncher = registerForActivityResult(
                new ActivityResultContracts.GetMultipleContents(),
                uris -> {

                    if (uris != null) {

                        customImagView.uriList.clear();
                        for (Uri uri:uris){
                            customImagView.uriList.add(uri.toString());
                        }


                        customImagView.imageAdpter.notifyDataSetChanged();
                    }
                }
        );
        customImagView.setOnGallerClick(new CustomImagView.OnGallerClick() {
            @Override
            public void onClick() {
                gallerLauncher.launch("image/*");
            }
        });
    //    uSerViewModel.getUser(new ResponseCallback<List<User>>() {
      //      @Override
        //    public void onSuccess(List<User> data, String message) {
          //      userList.clear();
            //    customUser.userList.addAll(data);

           // }

         //   @Override
           // public void onError(String message) {
//
   //          }
     //   });


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

                customUser.setUsers(users);

                Log.d("ASSIGNEE_API",
                        "setUsers() called with: " + users.size());
            }

            @Override
            public void Onerror(String message) {
                Log.e("ASSIGNEE_API", "API error: " + message);
            }
        });



        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title1=title.getEditText().getText().toString();
                String discpt=discription.getEditText().getText().toString();

                if (title1.isEmpty()){
                    Toast.makeText(getContext(), " Please Create title ", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (discpt.isEmpty()){
                    Toast.makeText(getContext(), " Please Create discription ", Toast.LENGTH_SHORT).show();
                    return;
                }

//                String statusPosition = spinner.getSelectedItem().toString();
                String categoryPosition = categoryspinner.getSelectedItem().toString();
                String periorityPosition = periorityspinner.getSelectedItem().toString();

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

                Team selectedTeam = (Team) team.getSelectedItem();

                if (selectedTeam == null || selectedTeam.getId() == -1) {
                    Toast.makeText(requireContext(),
                            "Please select a team",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                int selectedTeamId = selectedTeam.getId();

                Log.d("TEAM_API", "Selected Team ID = " + selectedTeamId);
                Log.d("TEAM_API", "Selected Team Name = " + selectedTeam.getName());

                String estimatTime=hours.getEditText().getText().toString().trim();
                Log.e("API TASK", "onClick: "+estimatTime );
                Loader.setVisibility(View.VISIBLE);
                selectedDate = startDate.getText().toString().trim();
                Due_Date = dueDate.getText().toString().trim();

                Log.d("TASK_DATE", "Start date before API = " + selectedDate);
                Log.d("TASK_DATE", "Due date before API = " + Due_Date);
                Task taskReq=new Task();
             taskReq.setTitle(title1);
             taskReq.setEstimated_hours(estimatTime);
             taskReq.setDiscription(discpt);
             taskReq.setTeamId(selectedTeamId);
             taskReq.setPeriority(periorityPosition);
taskReq.setCategory(categoryPosition);
taskReq.setStart_date(selectedDate);
taskReq.setDue_date(Due_Date);

                Log.d("TASK_API", "Selected priority: "
                        + periorityspinner.getSelectedItem());

                Log.d("TASK_API", "Priority value: "
                        + taskReq.getPeriority());
                List<Integer> selectedUser = customUser.getSelectedUserIds();

                Loader.setVisibility(View.VISIBLE);
                apiTaskViewModel.creatTask(taskReq, selectedUser, new ApiTaskRepository.TaskCallBack() {
                    @Override
                    public void OnSuccess(JsonObject jsonReader) {

                        Loader.setVisibility(View.GONE);
                        Log.e("API TASK", "OnSuccess: "+jsonReader );
                    }

                    @Override
                    public void Onerror(String message) {

                    }
                });
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
        return view;

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
                        startDate.setText(date);
                    } else {
                        Due_Date = date;
                        dueDate.setText(date);
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