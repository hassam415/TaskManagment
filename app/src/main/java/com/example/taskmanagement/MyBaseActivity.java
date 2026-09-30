package com.example.taskmanagement;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toolbar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.databinding.DataBindingUtil;

import com.example.taskmanagement.databinding.ActivityMyBaseBinding;

public abstract class MyBaseActivity extends AppCompatActivity {
    ActivityMyBaseBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_my_base);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        binding.toolbar.setTitle(gettoolbartitle());
        binding.container.addView(getView());
        onViewCreated();
    }

    protected void onViewCreated() {

    }

    public void setToolbarText(String toolbarText) {

        binding.toolbar.setTitle(toolbarText);

    }

    public abstract String gettoolbartitle();

    public abstract View getView();
}