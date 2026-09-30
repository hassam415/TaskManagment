package com.example.taskmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.taskmanagement.databinding.ActivityMainBinding;
import com.google.android.material.navigation.NavigationView;

public class AdminActivity extends MyBaseActivity implements NavigationView.OnNavigationItemSelectedListener {

    ActionBarDrawerToggle toggle;
    ActivityMainBinding binding;




    @Override
    public String gettoolbartitle() {
        return "Admin Panel";
    }

    @Override
    public View getView() {
        binding=ActivityMainBinding.inflate(getLayoutInflater());




        binding.navigation.setNavigationItemSelectedListener(this);
        toggle=new ActionBarDrawerToggle(this,binding.drawerLayout,super.binding.toolbar,R.string.open,R.string.close);
        binding.drawerLayout.addDrawerListener(toggle);
        toggle.getDrawerArrowDrawable().setColor(ContextCompat.getColor(this,R.color.white));
        toggle.syncState();

        return binding.getRoot();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id=menuItem.getItemId();
        if (id ==R.id.user){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,new UserListFragment()).commit();
setToolbarText("User");

        }
        if (id ==R.id.task){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,new TaskListFragment()).commit();
setToolbarText("Task");

        }
        if (id ==R.id.dashboard){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,new DashboardFragment()).commit();
setToolbarText("DashBoard");
        }
        if (id==R.id.logout){
            Toast.makeText(this, "Logout", Toast.LENGTH_SHORT).show();
            Intent  intent=new Intent(AdminActivity.this,LoginActivity.class);
            startActivity(intent);
        }
        binding.drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}