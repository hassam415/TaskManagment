package com.example.taskmanagement;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.taskmanagement.callback.ResponseCallback;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;


public class TaskListFragment extends Fragment {

    RecyclerView taskrecycler;
    FloatingActionButton taskbtn;
    TaskAdapter taskAdapter;
    List<Task> taskList = new ArrayList<>();
    TaskViewModel taskViewModel;
    String status;
LinearLayout Loader;
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
        taskAdapter = new TaskAdapter(getContext(), taskList);
        taskrecycler.setHasFixedSize(true);
        taskrecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        taskrecycler.setAdapter(taskAdapter);
        taskViewModel = new TaskViewModel(new TaskRepository());
        Loader.setVisibility(View.VISIBLE);
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