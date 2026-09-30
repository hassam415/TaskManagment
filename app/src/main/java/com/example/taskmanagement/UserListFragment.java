package com.example.taskmanagement;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.taskmanagement.callback.ResponseCallback;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class UserListFragment extends Fragment {
RecyclerView userrecyler;
FloatingActionButton userbtn;
    USerViewModel uSerViewModel;
    UserAdapter userAdapter;
    List<User> userList=new ArrayList<>();
LinearLayout loader;

    public UserListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_user_list,container,false);
       userrecyler=view.findViewById(R.id.userrecyeler);
        loader=view.findViewById(R.id.loader);
userbtn=view.findViewById(R.id.userbtn);
userrecyler.setHasFixedSize(true);
//AdminActivity mainActivity=(AdminActivity) requireActivity();
//mainActivity.toolbar1.setTitle("Users");
userrecyler.setLayoutManager(new LinearLayoutManager(getContext()));
userAdapter=new UserAdapter(getContext(),userList);
userrecyler.setAdapter(userAdapter);
loader.setVisibility(View.VISIBLE);
userAdapter.setOnUserClick(new UserAdapter.OnUserClick() {
    @Override
    public void OnClick(int position) {
        User user=userList.get(position);
        String id=user.getUuid();
        UserDetailFragment userDetailFragment=new UserDetailFragment();
        Bundle bundle=new Bundle();
        bundle.putString("Id",id);
        userDetailFragment.setArguments(bundle);
        getParentFragmentManager().beginTransaction().replace(R.id.fragmentContainer,userDetailFragment).addToBackStack(null).commit();
    }
});
userbtn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        getParentFragmentManager().beginTransaction().replace(R.id.fragmentContainer,new UserFragment()).addToBackStack(null).commit();
    }
});
uSerViewModel=new USerViewModel(new UserRepository());
        uSerViewModel.getUser(new ResponseCallback<List<User>>() {
            @Override
            public void onSuccess(List<User> data, String message) {
                loader.setVisibility(View.GONE);
                userList.clear();
                userList.addAll(data);
                userAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(String message) {
                loader.setVisibility(View.GONE);
            }
        });
        return view;
    }
}