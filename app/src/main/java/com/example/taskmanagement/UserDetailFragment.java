package com.example.taskmanagement;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.taskmanagement.callback.ResponseCallback;

import java.util.ArrayList;
import java.util.List;

public class UserDetailFragment extends Fragment {
RecyclerView userdetailrecycler;
UserDetailAdapter userDetailAdapter;
USerViewModel uSerViewModel;
List<User>userList=new ArrayList<>();
    public UserDetailFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View  view=inflater.inflate(R.layout.fragment_user_detail,container,false);
userdetailrecycler=view.findViewById(R.id.userdetailrecycler);
userdetailrecycler.setHasFixedSize(true);
        ImageView filterBtn=requireActivity().findViewById(R.id.filterBtn);
        filterBtn.setVisibility(View.GONE);
userdetailrecycler.setLayoutManager(new LinearLayoutManager(getContext()));
userDetailAdapter=new UserDetailAdapter(getContext(),userList);
userdetailrecycler.setAdapter(userDetailAdapter);
uSerViewModel=new USerViewModel(new UserRepository());
Bundle bundle=getArguments();
String id=bundle.getString("Id");
uSerViewModel.getSingelUser(id, new ResponseCallback<User>() {
    @Override
    public void onSuccess(User data, String message) {
        userList.clear();
        userList.add(data);
        userDetailAdapter.notifyDataSetChanged();
    }

    @Override
    public void onError(String message) {

    }
});
        return view;
    }
}