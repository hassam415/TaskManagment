package com.example.taskmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.taskmanagement.callback.ResponseCallback;
import com.example.taskmanagement.databinding.ActivityUserDashBoardBinding;
import com.google.android.material.navigation.NavigationView;
import com.google.common.net.InternetDomainName;
import com.google.firebase.auth.FirebaseAuth;

public class UserDashBoardActivity extends MyBaseActivity implements NavigationView.OnNavigationItemSelectedListener {
    ActivityUserDashBoardBinding binding;
    FirebaseAuth auth;
    ActionBarDrawerToggle toggle;
    TaskViewModel taskViewModel;
    String Id;

    @Override
    public String gettoolbartitle() {
        return "Dashboard";
    }

    @Override
    public View getView() {
        auth=FirebaseAuth.getInstance();
        binding = ActivityUserDashBoardBinding.inflate(getLayoutInflater());
        binding.navigationbar.setNavigationItemSelectedListener(UserDashBoardActivity.this);
        taskViewModel = new TaskViewModel(new TaskRepository());
        toggle = new ActionBarDrawerToggle(this, binding.drawerLayout, super.binding.toolbar, R.string.open, R.string.close);
        binding.drawerLayout.addDrawerListener(toggle);
        toggle.getDrawerArrowDrawable().setColor(ContextCompat.getColor(this, R.color.white));
        toggle.syncState();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentcontainer,new UserDashboardFragment()).commit();
        return binding.getRoot();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();
        if (id == R.id.task) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentcontainer, new SingleUserTaskListFragment()).commit();
            binding.drawerLayout.closeDrawer(GravityCompat.START);
            setToolbarText("Assigned Task");
        }
        if (id == R.id.dashboard) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentcontainer, new UserDashboardFragment()).commit();
            binding.drawerLayout.closeDrawer(GravityCompat.START);
            setToolbarText("Dashboard");
        }
        if (id == R.id.logout) {
            auth.signOut();
            Toast.makeText(this, "Logout", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(UserDashBoardActivity.this, LoginActivity.class);

            startActivity(intent);
            finish();
        }

        return true;
    }
}