
package com.example.taskmanagement;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.util.Pair;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskmanagement.callback.ResponseCallback;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.Timestamp;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.json.JSONArray;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TaskListFragment extends Fragment {

    RecyclerView taskrecycler;
    FloatingActionButton taskbtn;
    TaskAdapter taskAdapter;
    List<Task> taskList = new ArrayList<>();

    TaskViewModel taskViewModel;
    String status;
    String id;
TextView selectDate;
    LinearLayout Loader;
    DrawerLayout filterDrawer;

    Spinner spinner, categoryspiner, periorityspiner;
    Button applybtn;

  String startDate;
  String endDate;

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
    ApiTaskViewModel apiTaskViewModel;

    public TaskListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(
                R.layout.fragment_task_list, container, false);

        selectDate = view.findViewById(R.id.selectDate);
        taskrecycler = view.findViewById(R.id.taskrecycler);
        taskbtn = view.findViewById(R.id.taskbtn);
        Loader = view.findViewById(R.id.Loader);
        applybtn = view.findViewById(R.id.applybtn);

apiTaskViewModel=new ApiTaskViewModel(new ApiTaskRepository());
        categoryspiner = view.findViewById(R.id.categoryspiner);
        periorityspiner = view.findViewById(R.id.periorityspiner);
        spinner = view.findViewById(R.id.spinner);

        filterDrawer = view.findViewById(R.id.filterDrawer);

        ImageView filterBtn = requireActivity().findViewById(R.id.filterBtn);

        statusViewModel = new StatusViewModel(new StatusRepository());
        categoryViewModel = new CategoryViewModel(new CategoryRepository());
        periorityViewModel = new PeriorityViewModel(new PeriorityRepository());
        taskViewModel = new TaskViewModel(new TaskRepository());

        taskAdapter = new TaskAdapter(requireContext(), taskList);

        taskrecycler.setHasFixedSize(true);
        taskrecycler.setLayoutManager(
                new LinearLayoutManager(requireContext()));
        taskrecycler.setAdapter(taskAdapter);

        Loader.setVisibility(View.VISIBLE);
filterBtn.setVisibility(View.VISIBLE);
        Bundle bundle = getArguments();

        if (bundle != null) {
            id = bundle.getString("statusId");
            status = bundle.getString("status");
        }

        statusName.clear();
        statusName.add("Status");

        statusAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, statusName);

        statusAdapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(statusAdapter);

        statusViewModel.getList(new ResponseCallback<List<Status>>() {
            @Override
            public void onSuccess(List<Status> data, String message) {
                if (!isAdded()) return;

                statusList.clear();
                statusList.addAll(data);

                statusName.clear();
                statusName.add("Status");

                for (Status item : data) {
                    statusName.add(item.getName());
                }

                statusAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(String message) {
                Log.e("StatusList", message);
            }
        });
taskAdapter.setOnDeletBtnClick(new TaskAdapter.OnDeletBtnClick() {
    @Override
    public void onClick(String taskID, int position) {
        apiTaskViewModel.delettask(taskID, new ApiTaskRepository.TaskCallBack() {
            @Override
            public void OnSuccess(JsonObject jsonReader) {
                taskList.remove(jsonReader);
                taskAdapter.notifyDataSetChanged();
            }

            @Override
            public void Onerror(String message) {

            }
        });
    }
});

        categoryName.clear();
        categoryName.add("Category");

        categoryAdapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_spinner_item,
                categoryName);

        categoryAdapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);

        categoryspiner.setAdapter(categoryAdapter);

        categoryViewModel.getList(new ResponseCallback<List<Category>>() {
            @Override
            public void onSuccess(List<Category> data, String message) {
                if (!isAdded()) return;

                categories.clear();
                categories.addAll(data);

                categoryName.clear();
                categoryName.add("Category");

                for (Category item : data) {
                    categoryName.add(item.getName());
                }

                categoryAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(String message) {
                Log.e("CategoryList", message);
            }
        });

        periorityName.clear();
        periorityName.add("Priority");

        periorityAdapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_spinner_item,
                periorityName);

        periorityAdapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);

        periorityspiner.setAdapter(periorityAdapter);

        periorityViewModel.getList(new ResponseCallback<List<Periority>>() {
            @Override
            public void onSuccess(List<Periority> data, String message) {
                if (!isAdded()) return;

                periorities.clear();
                periorities.addAll(data);

                periorityName.clear();
                periorityName.add("Priority");

                for (Periority item : data) {
                    periorityName.add(item.getName());
                }

                periorityAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(String message) {
                Log.e("PriorityList", message);
            }
        });


        filterBtn.setOnClickListener(v -> {

                filterDrawer.openDrawer(GravityCompat.END);

        });


selectDate.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        MaterialDatePicker<Pair<Long, Long>> materialDatePicker=MaterialDatePicker.Builder.dateRangePicker().build();
materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Pair<Long, Long>>() {
    @Override
    public void onPositiveButtonClick(Pair<Long, Long> longLongPair) {
Long firstDate=longLongPair.first;
Long lastDate=longLongPair.second;
        SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy",Locale.getDefault());
         startDate= (sdf.format(new Date(firstDate)));
         endDate= (sdf.format(new Date(lastDate)));
        selectDate.setText(startDate+"  "+endDate);
    }
});
materialDatePicker.show(getParentFragmentManager(),"");
    }
});


        applybtn.setOnClickListener(v -> {

            String statusId = null;
            String categoryId = null;
            String periorityId = null;


            if (spinner.getSelectedItemPosition() > 0) {
                String selectedStatus = spinner.getSelectedItem().toString();

                for (Status item : statusList) {
                    if (selectedStatus.equals(item.getName())) {
                        statusId = item.getId();
                        break;
                    }
                }
            } else if (id != null) {

                statusId = id;
            }


            if (categoryspiner.getSelectedItemPosition() > 0) {
                String selectedCategory =
                        categoryspiner.getSelectedItem().toString();

                for (Category item : categories) {
                    if (selectedCategory.equals(item.getName())) {
                        categoryId = item.getId();
                        break;
                    }
                }
            }

            if (periorityspiner.getSelectedItemPosition() > 0) {
                String selectedPriority =
                        periorityspiner.getSelectedItem().toString();

                for (Periority item : periorities) {
                    if (selectedPriority.equals(item.getName())) {
                        periorityId = item.getId();
                        break;
                    }
                }
            }


            Timestamp startTimestamp = null;
            Timestamp endTimestamp = null;

            if (startDate != null) {

                startTimestamp=new Timestamp(new Date(startDate));
            }
if (endDate!=null){
    endTimestamp=new Timestamp(new Date(endDate));
}

            Loader.setVisibility(View.VISIBLE);

            taskViewModel.getTaskByFilter(
                    statusId,
                    categoryId,
                    periorityId,
                    startTimestamp,
                    endTimestamp,
                    new ResponseCallback<List<Task>>() {
                        @Override
                        public void onSuccess(List<Task> data, String message) {
                            if (!isAdded()) return;

                            Loader.setVisibility(View.GONE);

                            taskList.clear();
                            taskList.addAll(data);
                            taskAdapter.notifyDataSetChanged();

                            filterDrawer.closeDrawer(GravityCompat.END);
                        }

                        @Override
                        public void onError(String message) {
                            if (!isAdded()) return;

                            Loader.setVisibility(View.GONE);

                            Toast.makeText(requireContext(),
                                    message, Toast.LENGTH_SHORT).show();
                        }
                    });
        });


        loadTasks();


        taskAdapter.setOnTaskClick(position -> {
            if (position < 0 || position >= taskList.size()) return;

            Task task = taskList.get(position);

            Bundle taskBundle = new Bundle();
            taskBundle.putString("Id", task.getId());

            TaskDisplayFragment fragment = new TaskDisplayFragment();
            fragment.setArguments(taskBundle);

            getParentFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentContainer, fragment)
                    .addToBackStack(null)
                    .commit();
        });

        taskbtn.setOnClickListener(v -> {
            getParentFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentContainer, new TaskFragment())
                    .addToBackStack(null)
                    .commit();
        });

        return view;
    }

    private void loadTasks() {
        if (id != null && !id.isEmpty()) {

            taskbtn.setVisibility(View.GONE);

            taskViewModel.getTaskByStatus(
                    id,
                    new ResponseCallback<List<Task>>() {
                        @Override
                        public void onSuccess(List<Task> data, String message) {
                            if (!isAdded()) return;

                            Loader.setVisibility(View.GONE);
                            taskList.clear();
                            taskList.addAll(data);
                            taskAdapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onError(String message) {
                            if (!isAdded()) return;

                            Loader.setVisibility(View.GONE);
                            Log.e("getTaskByStatus", message);

                            Toast.makeText(requireContext(),
                                    message, Toast.LENGTH_SHORT).show();
                        }
                    });

        } else if (status != null && !status.isEmpty()) {

            taskbtn.setVisibility(View.GONE);

            taskViewModel.getTaskByStatus(
                    status,
                    new ResponseCallback<List<Task>>() {
                        @Override
                        public void onSuccess(List<Task> data, String message) {
                            if (!isAdded()) return;

                            Loader.setVisibility(View.GONE);
                            taskList.clear();
                            taskList.addAll(data);
                            taskAdapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onError(String message) {
                            if (!isAdded()) return;

                            Loader.setVisibility(View.GONE);
                            Toast.makeText(requireContext(),
                                    message, Toast.LENGTH_SHORT).show();
                        }
                    });

        } else {

            taskbtn.setVisibility(View.VISIBLE);
apiTaskViewModel.getTask(new ApiTaskRepository.TaskCallBack() {
    @Override
    public void OnSuccess(JsonObject jsonReader) {

                                  Loader.setVisibility(View.GONE);
                               taskList.clear();
        JsonArray array= jsonReader.getAsJsonArray("data");
        for (JsonElement jsonElement :array){
            Task task=new Gson().fromJson(jsonElement,Task.class);
            taskList.add(task);
        }

                           taskAdapter.notifyDataSetChanged();
    }

    @Override
    public void Onerror(String message) {
        Log.e("API Task", "Onerror: "+message );
    }
});
         //   taskViewModel.getTask(
           //         new ResponseCallback<List<Task>>() {
             //           @Override
               //         public void onSuccess(List<Task> data, String message) {
                 //           if (!isAdded()) return;
//
   //                          Loader.setVisibility(View.GONE);
     //                       taskList.clear();
       //                     taskList.addAll(data);
         //                   taskAdapter.notifyDataSetChanged();
           //             }
//
   //                      @Override
     //                   public void onError(String message) {
       //                     if (!isAdded()) return;

         //                   Loader.setVisibility(View.GONE);
           //                 Toast.makeText(requireContext(),
             //                       message, Toast.LENGTH_SHORT).show();
               //         }
                 //   });
        }
    }
}
