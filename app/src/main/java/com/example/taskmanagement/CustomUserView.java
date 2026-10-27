
package com.example.taskmanagement;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class CustomUserView extends LinearLayout {

    GridLayout assigneeGrid;
    FirebaseFirestore fs;

    public List<User> userList = new ArrayList<>();
    public List<User> selecteduser = new ArrayList<>();

    RecyclerView recylerView;
    List<String> list = new ArrayList<>();
    taskitemAdapter taskitemAdapter;
  //  TaskViewModel taskViewModel;

    private ProgressBar loader;

    public CustomUserView(Context context) {
        super(context);
        init(context);
    }

    public CustomUserView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CustomUserView(
            Context context,
            @Nullable AttributeSet attrs,
            int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public CustomUserView(
            Context context,
            AttributeSet attrs,
            int defStyleAttr,
            int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    public void init(Context context) {
        setOrientation(VERTICAL);

        LayoutInflater.from(context).inflate(R.layout.user_view, this, true);

        loader = findViewById(R.id.loader);
        recylerView = findViewById(R.id.recylerView);

        recylerView.setLayoutManager(new GridLayoutManager(context, 2));
        recylerView.setHasFixedSize(true);
        recylerView.setNestedScrollingEnabled(false);

        taskitemAdapter = new taskitemAdapter(
                context,
                new ArrayList<>(),
                selecteduser
        );
        recylerView.setAdapter(taskitemAdapter);


    }

    // Keep existing method unchanged.
   // public void setview() {
     //   if (plus_btn != null) {
       //     plus_btn.setVisibility(View.GONE);
      //  }

        //if (taskitemAdapter != null) {
          //  taskitemAdapter.RemoveDeletebtn();
      //  }
  //  }

    // Keep existing method unchanged.
    public void showLoader() {
        loader.setVisibility(View.VISIBLE);
    }


    public void hideLoader() {
        loader.setVisibility(View.GONE);
    }
    public void setUsers(List<User> users) {
        userList.clear();

        if (users != null) {
            userList.addAll(users);
        }

        taskitemAdapter.setUsers(new ArrayList<>(userList));
    }


    public List<User> getSelectedUsers() {
        return new ArrayList<>(selecteduser);
    }





    public void clearSelection() {
        selecteduser.clear();
        list.clear();

    }

    private boolean isUserSelected(User user) {
        String userId = String.valueOf(user.getId());

        for (User selected : selecteduser) {
            if (String.valueOf(selected.getId()).equals(userId)) {
                return true;
            }
        }

        return false;
    }



    public void showUsersByIds(List<String> userIds) {
        selecteduser.clear();

        if (userIds == null) {

            return;
        }

        for (String userId : userIds) {
            for (User user : userList) {
                if (String.valueOf(user.getId()).equals(userId)) {
                    selecteduser.add(user);
                    break;
                }
            }
        }


    }
    public void setSelectedUserIds(List<Integer> selectedIds) {
        selecteduser.clear();

        if (selectedIds != null) {
            for (User user : userList) {
                if (user == null) continue;

                for (Integer id : selectedIds) {
                    if (id != null && user.getId() == id) {
                        selecteduser.add(user);
                        break;
                    }
                }
            }
        }

        taskitemAdapter.setSelectedUsers(new ArrayList<>(selecteduser));

        Log.d("ASSIGNEE_EDIT",
                "Selected IDs after matching: " + getSelectedUserIds());
    }

    public List<Integer> getSelectedUserIds() {
        List<Integer> ids = new ArrayList<>();

        for (User user : selecteduser) {
            if (user != null && user.getId() > 0) {
                ids.add(user.getId());
            }
        }

        return ids;
    }
}
