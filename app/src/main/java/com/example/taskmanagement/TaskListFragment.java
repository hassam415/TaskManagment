
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

import androidx.annotation.NonNull;
import androidx.core.util.Pair;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskmanagement.callback.ResponseCallback;
import com.example.taskmanagement.databinding.FragmentTaskListBinding;
import com.example.taskmanagement.response.PaginateResponse;
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



    TaskAdapter taskAdapter;
    List<Task> taskList = new ArrayList<>();

    TaskViewModel taskViewModel;
    String status;
    String id;



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
    FragmentTaskListBinding binding;

    public TaskListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        binding= DataBindingUtil.inflate(inflater, R.layout.fragment_task_list, container, false);


        apiTaskViewModel = new ApiTaskViewModel(new ApiTaskRepository());


        ImageView filterBtn = requireActivity().findViewById(R.id.filterBtn);

        statusViewModel = new StatusViewModel(new StatusRepository());
        categoryViewModel = new CategoryViewModel(new CategoryRepository());
        periorityViewModel = new PeriorityViewModel(new PeriorityRepository());
        taskViewModel = new TaskViewModel(new TaskRepository());

        taskAdapter = new TaskAdapter(requireContext(), taskList);

        binding.taskrecycler.setHasFixedSize(true);
        binding.taskrecycler.setLayoutManager(
                new LinearLayoutManager(requireContext()));
        binding.taskrecycler.setAdapter(taskAdapter);

        binding.Loader.setVisibility(View.VISIBLE);
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

        binding.spinner.setAdapter(statusAdapter);

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

        binding.categoryspiner.setAdapter(categoryAdapter);

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

        binding.periorityspiner.setAdapter(periorityAdapter);

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

            binding.filterDrawer.openDrawer(GravityCompat.END);

        });


        binding.selectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MaterialDatePicker<Pair<Long, Long>> materialDatePicker = MaterialDatePicker.Builder.dateRangePicker().build();
                materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Pair<Long, Long>>() {
                    @Override
                    public void onPositiveButtonClick(Pair<Long, Long> longLongPair) {
                        Long firstDate = longLongPair.first;
                        Long lastDate = longLongPair.second;
                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                        startDate = (sdf.format(new Date(firstDate)));
                        endDate = (sdf.format(new Date(lastDate)));
                        binding.selectDate.setText(startDate + "  " + endDate);
                    }
                });
                materialDatePicker.show(getParentFragmentManager(), "");
            }
        });


        binding.applybtn.setOnClickListener(v -> {

            String statusId = null;
            String categoryId = null;
            String periorityId = null;


            if (binding.spinner.getSelectedItemPosition() > 0) {
                String selectedStatus = binding.spinner.getSelectedItem().toString();

                for (Status item : statusList) {
                    if (selectedStatus.equals(item.getName())) {
                        statusId = item.getId();
                        break;
                    }
                }
            } else if (id != null) {

                statusId = id;
            }


            if (binding.categoryspiner.getSelectedItemPosition() > 0) {
                String selectedCategory =
                        binding.categoryspiner.getSelectedItem().toString();

                for (Category item : categories) {
                    if (selectedCategory.equals(item.getName())) {
                        categoryId = item.getId();
                        break;
                    }
                }
            }

            if (binding.periorityspiner.getSelectedItemPosition() > 0) {
                String selectedPriority =
                        binding.periorityspiner.getSelectedItem().toString();

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

                startTimestamp = new Timestamp(new Date(startDate));
            }
            if (endDate != null) {
                endTimestamp = new Timestamp(new Date(endDate));
            }

            binding.Loader.setVisibility(View.VISIBLE);

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

                            binding.Loader.setVisibility(View.GONE);

                            taskList.clear();
                            taskList.addAll(data);
                            taskAdapter.notifyDataSetChanged();

                            binding.filterDrawer.closeDrawer(GravityCompat.END);
                        }

                        @Override
                        public void onError(String message) {
                            if (!isAdded()) return;

                            binding.Loader.setVisibility(View.GONE);

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

        binding.taskbtn.setOnClickListener(v -> {
            getParentFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentContainer, new TaskFragment())
                    .addToBackStack(null)
                    .commit();
        });


        return binding.getRoot();
    }

    private void loadTasks() {
        if (id != null && !id.isEmpty()) {

            binding.taskbtn.setVisibility(View.GONE);

            taskViewModel.getTaskByStatus(
                    id,
                    new ResponseCallback<List<Task>>() {
                        @Override
                        public void onSuccess(List<Task> data, String message) {
                            if (!isAdded()) return;

                            binding.Loader.setVisibility(View.GONE);
                            taskList.clear();
                            taskList.addAll(data);
                            taskAdapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onError(String message) {
                            if (!isAdded()) return;

                            binding.Loader.setVisibility(View.GONE);
                            Log.e("getTaskByStatus", message);

                            Toast.makeText(requireContext(),
                                    message, Toast.LENGTH_SHORT).show();
                        }
                    });

        } else if (status != null && !status.isEmpty()) {

            binding.taskbtn.setVisibility(View.GONE);

            taskViewModel.getTaskByStatus(
                    status,
                    new ResponseCallback<List<Task>>() {
                        @Override
                        public void onSuccess(List<Task> data, String message) {
                            if (!isAdded()) return;

                            binding.Loader.setVisibility(View.GONE);
                            taskList.clear();
                            taskList.addAll(data);
                            taskAdapter.notifyDataSetChanged();

                            binding.taskrecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
                                @Override
                                public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                                    super.onScrolled(recyclerView, dx, dy);
                                }
                            });
                        }

                        @Override
                        public void onError(String message) {
                            if (!isAdded()) return;

                            binding.Loader.setVisibility(View.GONE);
                            Toast.makeText(requireContext(),
                                    message, Toast.LENGTH_SHORT).show();
                        }
                    });

        } else {

            binding.taskbtn.setVisibility(View.VISIBLE);
            apiTaskViewModel.getTask(new ResponseCallback<>() {
                @Override
                public void onSuccess(PaginateResponse<Task> data, String message) {

                    binding.Loader.setVisibility(View.GONE);
                    taskList.clear();
//                    JsonArray array = jsonReader.getAsJsonArray("data");
//                    for (JsonElement jsonElement : array) {
//                        Task task = new Gson().fromJson(jsonElement, Task.class);
//                        taskList.add(task);
//                    }
                    taskList.addAll(data.getData());

                    taskAdapter.notifyDataSetChanged();
                }

                @Override
                public void onError(String message) {

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
