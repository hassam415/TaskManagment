package com.example.taskmanagement;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskmanagement.databinding.ChecklistItemBinding;

import java.util.List;

public class CheckListAdapter extends RecyclerView.Adapter<CheckListViewHolder> {
    Context context;
    List<CheckList>checkLists;
OnCheckBoxCLick onCheckBoxCLick;

    public void setOnCheckBoxCLick(OnCheckBoxCLick onCheckBoxCLick) {
        this.onCheckBoxCLick = onCheckBoxCLick;
    }

    public CheckListAdapter(Context context, List<CheckList> checkLists) {
        this.context = context;
        this.checkLists = checkLists;
    }
   public interface OnCheckBoxCLick{
        void onClick(int Id,int position);
   }

    @NonNull
    @Override
    public CheckListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        ChecklistItemBinding  binding=ChecklistItemBinding.inflate(inflater,parent,false);
        return new CheckListViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CheckListViewHolder holder, int position) {
CheckList checkList=checkLists.get(position);
holder.binding.name.setText(checkList.getTitle());

        holder.binding.checkbox.setChecked(checkList.isIs_completed());
holder.binding.deletbtn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

        onCheckBoxCLick.onClick(checkList.getId(),position);
    }
});
holder.binding.checkbox.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        int checkListId=checkList.getId();
        onCheckBoxCLick.onClick(checkListId,position);

    }
});
    }

    @Override
    public int getItemCount() {
        return checkLists.size();
    }
}
