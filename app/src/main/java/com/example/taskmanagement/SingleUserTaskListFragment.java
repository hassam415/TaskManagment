package com.example.taskmanagement;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.taskmanagement.callback.ResponseCallback;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;


public class SingleUserTaskListFragment extends Fragment {
    RecyclerView taskrecycler;
    TaskAdapter taskAdapter;
    List<Task> taskList = new ArrayList<>();
    TaskViewModel taskViewModel;
    FirebaseAuth auth;
    LinearLayout loader;
String status;
    String id;
    public SingleUserTaskListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_single_user_task_list, container, false);
        taskrecycler = view.findViewById(R.id.taskrecycler);
        loader = view.findViewById(R.id.loader);
        taskrecycler.setHasFixedSize(true);
        taskrecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        taskAdapter = new TaskAdapter(getContext(), taskList);
        taskrecycler.setAdapter(taskAdapter);
        auth = FirebaseAuth.getInstance();
        id = auth.getCurrentUser().getUid();
        taskViewModel = new TaskViewModel(new TaskRepository());
        loader.setVisibility(View.VISIBLE);
        if (getArguments()!=null){
            status=getArguments().getString("status");
            taskViewModel.getSingleTaskByStatus(id, status, new ResponseCallback<Task>() {
                @Override
                public void onSuccess(Task data, String message) {
                    loader.setVisibility(View.GONE);
                    taskList.clear();
                    taskList.add(data);
                    taskAdapter.notifyDataSetChanged();
                }

                @Override
                public void onError(String message) {

                }
            });
        }else {
            taskViewModel.getSingleUserTask(id, new ResponseCallback<List<Task>>() {

                @Override

                public void onSuccess(List<Task> data, String message) {
                    loader.setVisibility(View.GONE);
                    taskList.clear();
                    taskList.addAll(data);
                    taskAdapter.notifyDataSetChanged();
                }

                @Override
                public void onError(String message) {

                }
            });
        }


        taskAdapter.setOnTaskClick(new TaskAdapter.OnTaskClick() {
            @Override
            public void onClick(int position) {
                Task task = taskList.get(position);
                String id = task.getUid();
                TaskDisplayFragment fragment = new TaskDisplayFragment();
                Bundle bundle = new Bundle();
                bundle.putString("Id", id);
                fragment.setArguments(bundle);
                getParentFragmentManager().beginTransaction().replace(R.id.fragmentcontainer, fragment).commit();
            }
        });



        return view;
    }
}