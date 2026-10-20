package com.example.taskmanagement;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.core.util.Pair;
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
import android.widget.TextView;

import com.example.taskmanagement.callback.ResponseCallback;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;

import org.checkerframework.checker.units.qual.C;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class SingleUserTaskListFragment extends Fragment {
    RecyclerView taskrecycler;
    TaskAdapter taskAdapter;
    List<Task> taskList = new ArrayList<>();
    TaskViewModel taskViewModel;
    FirebaseAuth auth;
   String startDate;
   String endDate;
    LinearLayout loader;
    Spinner spinner,categoryspiner,periorityspiner;
    Button applybtn;
    TextView selectDate;
String status;
DrawerLayout drawar_layout;
    String id;
    List<Status>statusList=new ArrayList<>();
    StatusViewModel statusViewModel;
    List<String>statusname=new ArrayList<>();
    ArrayAdapter<String>statusAdapter;
    List<Category>categoryList=new ArrayList<>();
    CategoryViewModel categoryViewModel;
    List<String>categoryName=new ArrayList<>();
    ArrayAdapter<String>categoryAdapter;

    List<Periority>periorityList=new ArrayList<>();
    PeriorityViewModel periorityViewModel;
    List<String>periorityname=new ArrayList<>();
    ArrayAdapter<String>periorityAdapter;

    public SingleUserTaskListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_single_user_task_list, container, false);
        taskrecycler = view.findViewById(R.id.taskrecycler);
        loader = view.findViewById(R.id.loader);

        applybtn = view.findViewById(R.id.applybtn);
        selectDate = view.findViewById(R.id.selectDate);
        ImageView filterBtn=requireActivity().findViewById(R.id.filterBtn);
        spinner = view.findViewById(R.id.spinner);
        categoryspiner = view.findViewById(R.id.categoryspiner);
        periorityspiner = view.findViewById(R.id.periorityspiner);
        drawar_layout = view.findViewById(R.id.drawar_layout);
        taskrecycler.setHasFixedSize(true);
        taskrecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        taskAdapter = new TaskAdapter(getContext(), taskList);
        statusViewModel=new StatusViewModel(new StatusRepository());
        categoryViewModel=new CategoryViewModel(new CategoryRepository());
        periorityViewModel=new PeriorityViewModel(new PeriorityRepository());
        taskrecycler.setAdapter(taskAdapter);
        filterBtn.setVisibility(View.VISIBLE);
        auth = FirebaseAuth.getInstance();
        id = auth.getCurrentUser().getUid();
        taskViewModel = new TaskViewModel(new TaskRepository());
        loader.setVisibility(View.VISIBLE);
        statusname.clear();
        statusname.add("Status");
        statusAdapter=new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item,statusname);
        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(statusAdapter);
        statusViewModel.getList(new ResponseCallback<List<Status>>() {
            @Override
            public void onSuccess(List<Status> data, String message) {
                statusList.clear();
                statusList.addAll(data);
                statusname.clear();
                statusname.add("Status");
                for (Status status1:statusList){
                    statusname.add(status1.getName());
                }
                statusAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(String message) {

            }
        });
        categoryName.clear();
        categoryName.add("Category");
        categoryAdapter=new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item,categoryName);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categoryspiner.setAdapter(categoryAdapter);
        categoryViewModel.getList(new ResponseCallback<List<Category>>() {
            @Override
            public void onSuccess(List<Category> data, String message) {
                categoryList.clear();
                categoryList.addAll(data);
                categoryName.clear();
                categoryName.add("Category");
                for (Category category:categoryList){
                    categoryName.add(category.getName());
                }
                categoryAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(String message) {

            }
        });
        periorityname.clear();
        periorityname.add("Periority");
        periorityAdapter=new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item,periorityname);
        periorityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        periorityspiner.setAdapter(periorityAdapter);
        periorityViewModel.getList(new ResponseCallback<List<Periority>>() {
            @Override
            public void onSuccess(List<Periority> data, String message) {
                periorityList.clear();
                periorityList.addAll(data);
                periorityname.clear();
                periorityname.add("Priority");

                for (Periority item : data) {
                    periorityname.add(item.getName());
                }
                periorityAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(String message) {

            }
        });
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
       selectDate.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               MaterialDatePicker<Pair<Long, Long>> materialDatePicker=MaterialDatePicker.Builder.dateRangePicker().build();
               materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Pair<Long, Long>>() {
                   @Override
                   public void onPositiveButtonClick(Pair<Long, Long> longLongPair) {
                       Long firstDate=longLongPair.first;
                       Long lastDate=longLongPair.second;
                       SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());;
                       startDate=sdf.format(new Date(firstDate));
                       endDate=sdf.format(new Date(lastDate));
                       selectDate.setText(startDate+"  "+endDate);
                   }
               });
               materialDatePicker.show(getParentFragmentManager(),"");
           }
       });
applybtn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        String statusID=null;
        String categoryID=null;
        String periorityID=null;
        String selectstatus=spinner.getSelectedItem().toString();
        for (Status item:statusList){
            if (selectstatus.equals(item.getName())){
                statusID=item.getId();
            }

        }
        String selectedCategory=categoryspiner.getSelectedItem().toString();
        for (Category category:categoryList){
            if (selectedCategory.equals(category.getName())){
                categoryID=category.getId();
            }

        }
        String selectedPeriority=periorityspiner.getSelectedItem().toString();
        for (Periority periority:periorityList){
            if (selectedPeriority.equals(periority.getName())){
                periorityID=periority.getId();
            }
        }

        Timestamp startstamp;
        Timestamp endstamp;
        startstamp=new Timestamp(new Date(startDate));
        endstamp=new Timestamp(new Date(endDate));

            taskViewModel.getTaskByFilter(statusID,categoryID,periorityID, startstamp,endstamp, new ResponseCallback<List<Task>>() {
              @Override
            public void onSuccess(List<Task> data, String message) {
              taskList.clear();
            taskList.addAll(data);
          taskAdapter.notifyDataSetChanged();
          drawar_layout.closeDrawer(GravityCompat.END);
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
