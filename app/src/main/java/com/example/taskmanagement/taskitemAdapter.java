package com.example.taskmanagement;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskmanagement.databinding.TaskuserBinding;

import java.util.ArrayList;
import java.util.List;

public class taskitemAdapter extends RecyclerView.Adapter<TaskItemViewHolder> {
    Context context;
    List<User>stringList;
    OnDelet onDelet;
    Boolean showdeletbtn=true;
    public taskitemAdapter(Context context, List<User> stringList) {
        this.context = context;
        this.stringList = stringList;
    }

    public void setOnDelet(OnDelet onDelet) {
        this.onDelet = onDelet;
    }

    public interface OnDelet{
        void Ondelet(int position);
}

public void RemoveDeletebtn(){
        this.showdeletbtn=false;
        notifyDataSetChanged();

}
    @NonNull
    @Override
    public TaskItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        TaskuserBinding binding= TaskuserBinding.inflate(inflater,parent,false);
        return new TaskItemViewHolder(binding);
    }


    @Override
    public void onBindViewHolder(@NonNull TaskItemViewHolder holder, int position) {
User user=stringList.get(position);
holder.binding.user.setText(user.getName());
        if (showdeletbtn) {
            holder.binding.imgbtn.setVisibility(View.VISIBLE);
        } else {
            holder.binding.imgbtn.setVisibility(View.GONE);
        }
holder.binding.imgbtn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

        onDelet.Ondelet(position);



    }
});
    }

    @Override
    public int getItemCount() {
        return stringList.size();
    }
}
