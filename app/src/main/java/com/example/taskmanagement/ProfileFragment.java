package com.example.taskmanagement;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.example.taskmanagement.callback.ResponseCallback;
import com.example.taskmanagement.databinding.FragmentProfileBinding;
import com.example.taskmanagement.response.LogoutResponse;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.Locale;


public class ProfileFragment extends Fragment {
FragmentProfileBinding binding;

AuthViewModel authViewModel;
String name,jobtitle;
    public ProfileFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding=FragmentProfileBinding.inflate(inflater,container,false);
        ImageView filterBtn=requireActivity().findViewById(R.id.filterBtn);
        filterBtn.setVisibility(View.GONE);
        authViewModel=new AuthViewModel(new AuthRepository());
        showlanguage();
        showTheme();
        binding.loader.setVisibility(View.VISIBLE);

        authViewModel.getAuthMe(new ResponseCallback<User>() {
            @Override
            public void onSuccess(User data, String message) {
                binding.loader.setVisibility(View.GONE);
                binding.name.setText(data.getName());

                binding.email.setText(data.getEmail());
                binding.role.setText(data.getRole());
                binding.jobtitle.setText(data.getJob_title() );
                binding.status.setText("Status :"+data.getStatus());
                name=data.getName();
                jobtitle=data.getJob_title();
            }

            @Override
            public void onError(String message) {

            }
        });
        binding.optionbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateProfileFragment fragment = new UpdateProfileFragment();

                Bundle bundle = new Bundle();
                bundle.putString("name", name);
                bundle.putString("jobTitle", jobtitle);

                fragment.setArguments(bundle);

                getParentFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragmentContainer, fragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
        binding.editProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.progressloader.setVisibility(View.VISIBLE);
                authViewModel.logout(new ResponseCallback<LogoutResponse>() {
                    @Override
                    public void onSuccess(LogoutResponse data, String message) {
                        binding.progressloader.setVisibility(View.GONE);
                        ApiSesson.clearSession(getContext());
                        Toast.makeText(getContext(), "Logout", Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(requireActivity(), LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                                Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }

                    @Override
                    public void onError(String message) {

                    }
                });
            }
        });
        binding.langnuagecard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLanguageDialog();
            }
        });
binding.themecard.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        showThemeDialogue();
        
    }
});
        return binding.getRoot();
    }

    private void showTheme() {
        int theme=ThemeHelper.getTheme(getContext());
        if (theme==AppCompatDelegate.MODE_NIGHT_YES){
            binding.selectedtheme.setText("Dark Theme");
        }
        else {
            binding.selectedtheme.setText("Light Theme");
        }
    }

    private void showThemeDialogue() {
        String []Theme={"Light","Dark"};
        AlertDialog.Builder builder=new MaterialAlertDialogBuilder(getContext());
        builder.setTitle("SELECT THEME");
        builder.setSingleChoiceItems(Theme, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
if (which==0){
    ThemeHelper.savetheme(getContext(), AppCompatDelegate.MODE_NIGHT_NO);
    Toast.makeText(getContext(), "Light Mode On Please Restart the Application", Toast.LENGTH_LONG).show();
}
if (which==1){
    ThemeHelper.savetheme(requireContext(),AppCompatDelegate.MODE_NIGHT_YES);
    Toast.makeText(getContext(), "Dark Mode On Please Restart the Application", Toast.LENGTH_LONG).show();
}
            }
        });
        builder.create();
        builder.show();
    }

    private void showLanguageDialog() {
String []language={"English","Urdu"};
AlertDialog.Builder builder=new MaterialAlertDialogBuilder(getContext());
builder.setTitle("SELECT LANGAUGE");
builder.setSingleChoiceItems(language, -1, new DialogInterface.OnClickListener() {
    @Override
    public void onClick(DialogInterface dialog, int which) {
        if (which==0){
            setLocal("en");
        }
        if (which==1){
            setLocal("ur");
        }
    }
});
builder.create();
builder.show();
    }

    private void setLocal(String language) {

        LanguageHelper.saveLanguage(requireContext(), language);

        requireActivity().recreate();
    }

    private void showlanguage(){
        String language=LanguageHelper.getLanguage(getContext());
        if (language.equals("ur")){
            binding.selectedLanguage.setText("Urdu");
        }else {
            binding.selectedLanguage.setText("English");
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        ((AdminActivity) requireActivity()).setToolbarText(getResources().getString(R.string.profile));
    }
    
}