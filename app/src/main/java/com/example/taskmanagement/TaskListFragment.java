package com.example.taskmanagement;

import static android.content.ContentValues.TAG;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.taskmanagement.callback.ResponseCallback;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.Timestamp;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class TaskListFragment extends Fragment {

    RecyclerView taskrecycler;
    FloatingActionButton taskbtn;
    TaskAdapter taskAdapter;
    List<Task> taskList = new ArrayList<>();
    TaskViewModel taskViewModel;
    String status;
    String []statuses={" Status", "To do","In Progess","In Review","Completed"};
LinearLayout Loader;
    DrawerLayout filterDrawer;
    Spinner spinner;
    Button applybtn;
    EditText edtfromdate,editto;
    Calendar startDate=null;
    Calendar EndDate=null;
    public TaskListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task_list, container, false);
        taskrecycler = view.findViewById(R.id.taskrecycler);
        taskbtn = view.findViewById(R.id.taskbtn);
        Loader = view.findViewById(R.id.Loader);
        applybtn = view.findViewById(R.id.applybtn);
ImageView filterBtn=requireActivity().findViewById(R.id.filterBtn);

        edtfromdate = view.findViewById(R.id.edtfromdate);
        editto = view.findViewById(R.id.editto);
        filterDrawer = view.findViewById(R.id.filterDrawer);
        spinner = view.findViewById(R.id.spinner);

        taskAdapter = new TaskAdapter(getContext(), taskList);
        taskrecycler.setHasFixedSize(true);
        taskrecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        taskrecycler.setAdapter(taskAdapter);
        taskViewModel = new TaskViewModel(new TaskRepository());
        Loader.setVisibility(View.VISIBLE);
        filterBtn.setVisibility(View.VISIBLE);

        filterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterDrawer.openDrawer(GravityCompat.END);
            }
        });
        ArrayAdapter<String>arrayAdapter=new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item,statuses);
        spinner.setAdapter(arrayAdapter);
        applybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selectedspiner=spinner.getSelectedItem().toString();

                Calendar last = (EndDate != null) ? EndDate : startDate;

                Calendar start = (Calendar) startDate.clone();
                start.set(Calendar.HOUR_OF_DAY, 0);
                start.set(Calendar.MINUTE, 0);
                start.set(Calendar.SECOND, 0);
                start.set(Calendar.MILLISECOND, 0);

                Calendar end = (Calendar) last.clone();
                end.set(Calendar.HOUR_OF_DAY, 0);
                end.set(Calendar.MINUTE, 0);
                end.set(Calendar.SECOND, 0);
                end.set(Calendar.MILLISECOND, 0);
                end.add(Calendar.DAY_OF_MONTH, 1);

                String statusFilter = spinner.getSelectedItemPosition() == 0
                        ? null : spinner.getSelectedItem().toString();
                taskViewModel.getTaskByFilter(selectedspiner,new Timestamp(start.getTime()), new Timestamp(end.getTime()), new ResponseCallback<List<Task>>() {
                    @Override
                    public void onSuccess(List<Task> data, String message) {
                        taskList.clear();
                        taskList.addAll(data);
                        taskAdapter.notifyDataSetChanged();
                        filterDrawer.closeDrawer(GravityCompat.END);
                    }

                    @Override
                    public void onError(String message) {
                    }
                });
            }
        });
        edtfromdate.setOnClickListener(v -> {
            Calendar now = Calendar.getInstance();
            new DatePickerDialog(requireContext(), (view1, year, month, day) -> {
                startDate = Calendar.getInstance();
                startDate.set(year, month, day);
                edtfromdate.setText(day + "/" + (month + 1) + "/" + year);
            }, now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH)).show();
        });
        editto.setOnClickListener(v -> {
            Calendar now = Calendar.getInstance();
            new DatePickerDialog(requireContext(), (view1, year, month, day) -> {
                EndDate = Calendar.getInstance();
                EndDate.set(year, month, day);
                editto.setText(day + "/" + (month + 1) + "/" + year);
            }, now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH)).show();
        });
        if (getArguments() != null && getArguments().getString("status") != null) {
taskbtn.setVisibility(View.GONE);
            status = getArguments().getString("status");
            Log.e("getTaskByStatus: ", status + "");

            taskViewModel.getTaskByStatus(status, new ResponseCallback<List<Task>>() {
                        @Override
                        public void onSuccess(List<Task> data, String message) {
Loader.setVisibility(View.GONE);
                            Log.e("getTaskByStatus: ", data.size() + "");
                            taskList.clear();
                            taskList.addAll(data);
                            taskAdapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onError(String message) {
                            Loader.setVisibility(View.GONE);
                            Log.e("getTaskByStatus: ", status + " " + message);

                        }
                    }
            );


        } else {

            taskViewModel.getTask(new ResponseCallback<List<Task>>() {
                                      @Override
                                      public void onSuccess(List<Task> data, String message) {
                                          Loader.setVisibility(View.GONE);
                                          taskList.clear();
                                          taskList.addAll(data);
                                          taskAdapter.notifyDataSetChanged();
                                      }

                                      @Override
                                      public void onError(String message) {
                                          Loader.setVisibility(View.GONE);
                                      }
                                  }
            );
        }

        taskAdapter.setOnTaskClick(new TaskAdapter.OnTaskClick() {
            @Override
            public void onClick(int position) {
                Task task = taskList.get(position);
                String Id = task.getUid();
                TaskDisplayFragment taskListFragment = new TaskDisplayFragment();
                Bundle bundle = new Bundle();
                bundle.putString("Id", Id);
                taskListFragment.setArguments(bundle);
                getParentFragmentManager().beginTransaction().replace(R.id.fragmentContainer, taskListFragment).addToBackStack(null).commit();

            }
        });
        taskbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getParentFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new TaskFragment()).addToBackStack(null).commit();
            }
        });
        return view;
    }
}