package com.example.taskmanagement;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskmanagement.databinding.CategoryItemBinding;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryViewHolder> {
    Context context;
    List<Category>categories;
OnCategoryClick onCategoryClick;

    public void setOnCategoryClick(OnCategoryClick onCategoryClick) {
        this.onCategoryClick = onCategoryClick;
    }

    public CategoryAdapter(Context context, List<Category> categories) {
        this.context = context;
        this.categories = categories;
    }
    public interface OnCategoryClick{
        void OnClick(Category category);
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        CategoryItemBinding binding=CategoryItemBinding.inflate(inflater,parent,false);
        return new CategoryViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
Category category=categories.get(position);
holder.binding.category.setText(category.getName());
holder.binding.optionbtn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        PopupMenu menu=new PopupMenu(context,holder.binding.optionbtn);
        menu.getMenuInflater().inflate(R.menu.update_menu,menu.getMenu());
        menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId()==R.id.update){
                    onCategoryClick.OnClick(category);
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
        return categories.size();
    }
}
