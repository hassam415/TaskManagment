package com.example.taskmanagement;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskmanagement.databinding.PeriorityItemBinding;

import java.util.List;

public class PeriorityAdapter extends RecyclerView.Adapter<PeriorityViewHolder> {
    Context context;
    List<Periority>periorities;
OnPeriorityClick onPeriorityClick;

    public void setOnPeriorityClick(OnPeriorityClick onPeriorityClick) {
        this.onPeriorityClick = onPeriorityClick;
    }

    public PeriorityAdapter(Context context, List<Periority> periorities) {
        this.context = context;
        this.periorities = periorities;
    }
public interface OnPeriorityClick{
        void OnClick(Periority periority);
}
    @NonNull
    @Override
    public PeriorityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        PeriorityItemBinding binding=PeriorityItemBinding.inflate(inflater,parent,false);
        return new PeriorityViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PeriorityViewHolder holder, int position) {
Periority periority=periorities.get(position);
holder.binding.status.setText(periority.getName());
holder.binding.optionbtn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        PopupMenu menu=new PopupMenu(context,holder.binding.optionbtn);
        menu.getMenuInflater().inflate(R.menu.update_menu, menu.getMenu());
        menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId()==R.id.update){
                    onPeriorityClick.OnClick(periority);
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
        return periorities.size();
    }
}
