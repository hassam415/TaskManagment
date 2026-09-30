package com.example.taskmanagement;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskmanagement.databinding.CommentsItemBinding;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class CommentsAdapter extends RecyclerView.Adapter<CommentViewHolder> {
    Context context;
    List<Comment>commentList;
OnBtnClick onBtnClick;
    public void setOnBtnClick(OnBtnClick onBtnClick) {
        this.onBtnClick = onBtnClick;
    }

    public CommentsAdapter(Context context, List<Comment> commentList) {
        this.context = context;
        this.commentList = commentList;
    }
public interface  OnBtnClick{
        void  Onclick(String reply, String Id);
}

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        CommentsItemBinding binding=CommentsItemBinding.inflate(inflater,parent,false);
        return new CommentViewHolder(binding);
    }
    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
Comment comment=commentList.get(position);
holder.binding.commentTextView.setText(comment.getComment());

        holder.binding.username.setText(comment.getUsername() + " :");
        List<String> replies = comment.getReplies();
        List<String> replyUserNames = comment.getUserReplies();

        if (replies != null && replyUserNames != null)
        { int size = Math.min(replies.size(), replyUserNames.size());
            for (int i = 0; i < size; i++) {
               LayoutInflater inflater=LayoutInflater.from(context);
               View view=inflater.inflate(R.layout.reply_item,holder.binding.replyContainer,false);
               TextView replyUser=view.findViewById(R.id.replyUser);
               TextView replytxt=view.findViewById(R.id.replytxt);
               replyUser.setText(replyUserNames.get(i));
               replytxt.setText(replies.get(i));
               holder.binding.replyContainer.addView(view);
            }
        }
holder.binding.replybtn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.reply_dialogue,null);

        TextInputLayout replyinputedit=view.findViewById(R.id.replyinputedit);

        new AlertDialog.Builder(context).setView(view).setPositiveButton("Send",(dialog, which) -> {
            String reply=replyinputedit.getEditText().getText().toString();
            onBtnClick.Onclick(reply, comment.getId());
        }).setNegativeButton("Cancel",(dialog, which) -> {
            dialog.dismiss();
        }).show();

    }
});
    }

    @Override
    public int getItemCount() {
        return commentList.size();
    }
}
