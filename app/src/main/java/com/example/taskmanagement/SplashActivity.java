package com.example.taskmanagement;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class SplashActivity extends AppCompatActivity {
    FirebaseFirestore ff;
    FirebaseAuth mauth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splash);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        ff=FirebaseFirestore.getInstance();
        mauth=FirebaseAuth.getInstance();
        String id=mauth.getCurrentUser().getUid();
        if (id==null){
            Intent intent=new Intent(SplashActivity.this, LoginActivity.class);
            startActivity(intent);
        }else {
            ff.collection("User").document(id).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    String role=documentSnapshot.getString("role");
                    if ("Admin".equals(role)){
                        Intent intent=new Intent(SplashActivity.this, AdminActivity.class);
                        startActivity(intent);
                    }else {
                        Intent intent=new Intent(SplashActivity.this,UserDashBoardActivity.class);
                        startActivity(intent);
                    }
                }
            });
        }

    }
}