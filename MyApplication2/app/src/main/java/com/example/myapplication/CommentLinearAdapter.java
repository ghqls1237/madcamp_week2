package com.example.myapplication;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;

public class CommentLinearAdapter extends RecyclerView.Adapter<CommentLinearAdapter.MyViewHolder> {
    ArrayList<CommentItem> comments = new ArrayList<CommentItem>();

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.comment_list_item, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.user.setText(comments.get(position).user);
        holder.text.setText(comments.get(position).text);
        holder.date.setText(comments.get(position).date);
    }


    @Override
    public int getItemCount() {
        return comments.size();
    }

    public void addItem(CommentItem commentItem){
        Log.d("AddPost", "Item");
        comments.add(commentItem);
        notifyDataSetChanged();
    }


    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView user;
        TextView text;
        TextView date;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            user = itemView.findViewById(R.id.comment_user);
            date = itemView.findViewById(R.id.comment_date);
            text = itemView.findViewById(R.id.comment_text);
        }
    }
}
