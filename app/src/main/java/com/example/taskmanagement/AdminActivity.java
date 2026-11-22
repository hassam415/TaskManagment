package com.example.taskmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
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
import com.google.firebase.auth.FirebaseAuth;

public class AdminActivity extends MyBaseActivity implements NavigationView.OnNavigationItemSelectedListener {

    ActionBarDrawerToggle toggle;
    ActivityMainBinding binding;
    FirebaseAuth mauth;


    @Override
    public String gettoolbartitle() {
        return "Admin Panel";
    }

    @Override
    public View getView() {
        mauth = FirebaseAuth.getInstance();
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        binding.navigation.setNavigationItemSelectedListener(this);
        super.binding.filterBtn.setVisibility(View.GONE);
        toggle = new ActionBarDrawerToggle(this, binding.drawerLayout, super.binding.toolbar, R.string.open, R.string.close);
        binding.drawerLayout.addDrawerListener(toggle);
        toggle.getDrawerArrowDrawable().setColor(ContextCompat.getColor(this, R.color.white));
        toggle.syncState();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new DashboardFragment()).commit();
        setToolbarText(getResources().getString(R.string.Dashboard));
        return binding.getRoot();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();
        if (id == R.id.user) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new UserListFragment()).addToBackStack(null).commit();
            setToolbarText(getResources().getString(R.string.User_Managment));

        }
        if (id == R.id.task) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new TaskListFragment()).addToBackStack(null).commit();
            setToolbarText(getResources().getString(R.string.app_name));

        }
        if (id==R.id.profile){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,new ProfileFragment()).addToBackStack(null).commit();
            setToolbarText(getString(R.string.profile));
        }
        if (id == R.id.dashboard) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new DashboardFragment()).commit();
            setToolbarText(getResources().getString(R.string.Dashboard));
        }

        if (id==R.id.report){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,new ReportFragment()).addToBackStack(null).commit();
            setToolbarText(getResources().getString(R.string.Reports));
        }


if (id==R.id.project){
    getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,new ProjectFragment()).addToBackStack(null).commit();
    setToolbarText(getResources().getString(R.string.Projects_Workspace));
}
if (id==R.id.team){
    getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,new TeamFragment()).addToBackStack(null).commit();
    setToolbarText(getResources().getString(R.string.Team_Operational_Squads));
}
if (id==R.id.calendar){
    getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,new CalendarFragment()).addToBackStack(null).commit();
    setToolbarText(getResources().getString(R.string.Interactive_Calendar_Deadline));
}
        binding.drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    public void onBackPressed() {

        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {

            binding.drawerLayout.closeDrawer(GravityCompat.START);

        } else {
            super.onBackPressed();
        }
    }
}
