package com.example.taskmanagement;

import android.content.Context;
import android.content.Intent;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskmanagement.databinding.StatusItemBinding;

import java.util.List;

public class StatusAdapter extends RecyclerView.Adapter<StatusViewHolder> {
    Context context;
    List<Status>statusList;
OnThreeDotCLick onThreeDotCLick;

    public void setOnThreeDotCLick(OnThreeDotCLick onThreeDotCLick) {
        this.onThreeDotCLick = onThreeDotCLick;
    }

    public StatusAdapter(Context context, List<Status> statusList) {
        this.context = context;
        this.statusList = statusList;
    }
public interface OnThreeDotCLick{
        void onClick(Status status);
}
    @NonNull
    @Override
    public StatusViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        StatusItemBinding binding=StatusItemBinding.inflate(inflater,parent,false);
        return new StatusViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull StatusViewHolder holder, int position) {
Status status=statusList.get(position);
holder.binding.status.setText(status.getName());
holder.binding.optionbtn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        PopupMenu menu=new PopupMenu(context,holder.binding.optionbtn);
        menu.getMenuInflater().inflate(R.menu.update_menu, menu.getMenu());
        menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId()==R.id.update){
                 onThreeDotCLick.onClick(status);
                }
                return true;
            }
        });
        menu.show();
    }
});
    }

    @Override
    public int getItemCount() {
        return statusList.size();
    }
}
