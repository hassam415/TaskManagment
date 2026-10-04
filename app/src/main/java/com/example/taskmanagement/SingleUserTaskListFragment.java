package com.example.taskmanagement;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.example.taskmanagement.callback.ResponseCallback;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;

import org.checkerframework.checker.units.qual.C;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class SingleUserTaskListFragment extends Fragment {
    RecyclerView taskrecycler;
    TaskAdapter taskAdapter;
    List<Task> taskList = new ArrayList<>();
    TaskViewModel taskViewModel;
    FirebaseAuth auth;
    EditText edtfromdate,editto;
    String[] statusse={" Status", "To do","In Progess","In Review","Completed"};
    LinearLayout loader;
    Spinner statusSpinner;
    Button applybtn;
String status;
DrawerLayout drawar_layout;
    String id;
    Calendar fromDate=null;
    Calendar toDate=null;

    public SingleUserTaskListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_single_user_task_list, container, false);
        taskrecycler = view.findViewById(R.id.taskrecycler);
        loader = view.findViewById(R.id.loader);
        edtfromdate = view.findViewById(R.id.edtfromdate);
        applybtn = view.findViewById(R.id.applybtn);
        editto = view.findViewById(R.id.editto);
        ImageView filterBtn=requireActivity().findViewById(R.id.filterBtn);
        statusSpinner = view.findViewById(R.id.statusSpinner);
        drawar_layout = view.findViewById(R.id.drawar_layout);
        taskrecycler.setHasFixedSize(true);
        taskrecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        taskAdapter = new TaskAdapter(getContext(), taskList);
        taskrecycler.setAdapter(taskAdapter);
        filterBtn.setVisibility(View.VISIBLE);
        auth = FirebaseAuth.getInstance();
        ArrayAdapter<String>arrayAdapter=new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item,statusse);
statusSpinner.setAdapter(arrayAdapter);
        id = auth.getCurrentUser().getUid();
        taskViewModel = new TaskViewModel(new TaskRepository());
        loader.setVisibility(View.VISIBLE);
        filterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawar_layout.openDrawer(GravityCompat.END);
            }
        });
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
        edtfromdate.setOnClickListener(v -> {
            Calendar now = Calendar.getInstance();
            new DatePickerDialog(requireContext(), (view1, year, month, day) -> {
                fromDate = Calendar.getInstance();
                fromDate.set(year, month, day);
                edtfromdate.setText(day + "/" + (month + 1) + "/" + year);
            }, now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH)).show();
        });
        editto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar last=Calendar.getInstance();
                new DatePickerDialog(requireContext(),(view1,year,month,day)->{
                    toDate=Calendar.getInstance();
                    toDate.set(year,month,day);
                    editto.setText(day + "/" +(month+1) + "/" +year);
                },last.get(Calendar.YEAR),last.get(Calendar.MONTH),Calendar.DAY_OF_MONTH).show();
            }
        });
applybtn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        String selectedspiner=statusSpinner.getSelectedItem().toString();
        Calendar startDate=(Calendar) fromDate.clone();
        startDate.set(Calendar.HOUR_OF_DAY,0);
        startDate.set(Calendar.MINUTE,0);
        startDate.set(Calendar.SECOND,0);
        startDate.set(Calendar.MILLISECOND,0);
        Calendar EndDate=(Calendar) toDate.clone();
        EndDate.set(Calendar.HOUR_OF_DAY,0);
        EndDate.set(Calendar.MINUTE,0);
        EndDate.set(Calendar.SECOND,0);
        EndDate.set(Calendar.MILLISECOND,0);
        taskViewModel.getTaskByFilter(selectedspiner, new Timestamp(startDate.getTime()), new Timestamp(EndDate.getTime()), new ResponseCallback<List<Task>>() {
            @Override
            public void onSuccess(List<Task> data, String message) {
                taskList.clear();
                taskList.addAll(data);
                taskAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(String message) {

            }
        });
    }
});
        taskAdapter.setOnTaskClick(new TaskAdapter.OnTaskClick() {
            @Override
            public void onClick(int position) {
                Task task = taskList.get(position);
                String id = task.getUid();
                TaskDisplayFragment fragment = new TaskDisplayFragment();
                Bundle bundle = new Bundle();
                bundle.putString("Id", id);
                fragment.setArguments(bundle);

                getParentFragmentManager().beginTransaction().replace(R.id.fragmentcontainer, fragment).addToBackStack(null).commit();
            }
        });



        return view;
    }
}