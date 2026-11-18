package com.example.taskmanagement;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.taskmanagement.callback.ResponseCallback;
import com.example.taskmanagement.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {
    ActivityLoginBinding binding;

    USerViewModel uSerViewModel;
    AuthViewModel authViewModel;
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LanguageHelper.applyLanguage(newBase));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        uSerViewModel = new USerViewModel(new UserRepository());
        authViewModel = new AuthViewModel(new AuthRepository());
        ThemeHelper.applytheme(this);
        String token = ApiSesson.getAccess_Token(this);

        if (token != null && !token.isEmpty()) {

            Intent intent = new Intent(LoginActivity.this, AdminActivity.class);
            startActivity(intent);
            finish();
            return;
        }
        binding.loginbtn.setOnClickListener(v -> {
            String email = binding.email.getEditText().getText().toString();
            String pass = binding.password.getEditText().getText().toString();
            showLoader();
            loginmethod(email, pass);
        });
    }


    private void loginmethod(String email, String pass) {
//        uSerViewModel.loginmethod(email, pass, new ResponseCallback<User>() {
        //          @Override
        //        public void onSuccess(User data, String message) {
        //          binding.loader.setVisibility(View.GONE);
        //        if ("Admin Panel".equals(message)) {
        //          Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
        //        Intent intent = new Intent(LoginActivity.this, AdminActivity.class);

        //               intent.setFlags(
        //                     Intent.FLAG_ACTIVITY_NEW_TASK |
        //                           Intent.FLAG_ACTIVITY_CLEAR_TASK
        //         );
        //       startActivity(intent);
        //         finish();
        //       } else {
//
        //                  Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
        //               binding.main.setVisibility(View.GONE);
        //             binding.fragmentContainer.setVisibility(View.VISIBLE);
        //           Intent intent=new Intent(LoginActivity.this,UserDashBoardActivity.class);

        //         intent.setFlags(
        //               Intent.FLAG_ACTIVITY_NEW_TASK |
        //                     Intent.FLAG_ACTIVITY_CLEAR_TASK
        //         );
        //       startActivity(intent);
        //      finish();
        //         }
//
        //          }
//
        //          @Override
        //       public void onError(String message) {
        //         binding.loader.setVisibility(View.GONE);
        //      Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
        //    }
        //  });

        authViewModel.login(email, pass, new AuthRepository.LoginCallBack() {
            @Override
            public void OnSuccess(LoginResponse loginResponse) {
               HideLoader();

                String token = loginResponse.getAccess_token();
                ApiSesson.setAccess_Token(LoginActivity.this,token);
                Toast.makeText(LoginActivity.this, "Login SuccessFull ", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LoginActivity.this, AdminActivity.class);
                startActivity(intent);
            }

            @Override
            public void OnError(String message) {
                HideLoader();
                Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();

            }
        });
    }
    private void showLoader(){
        binding.loginbtn.setEnabled(false);
        binding.loginbtn.setText("");
        binding.loader.setVisibility(View.VISIBLE);
    }
    private void HideLoader(){
        binding.loginbtn.setEnabled(true);
        binding.loginbtn.setText("Login");
        binding.loader.setVisibility(View.GONE);
    }

}