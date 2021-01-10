package com.example.myapplication;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PostLinearAdapter extends RecyclerView.Adapter<PostLinearAdapter.MyViewHolder> {
    ArrayList<PostItem> posts = new ArrayList<PostItem>();


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.post_list_item, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.userName.setText(posts.get(position).user);
        holder.title.setText(posts.get(position).title);
        holder.date.setText(posts.get(position).date);
        String postText = posts.get(position).text;
        if(postText.length() > 33){
            postText = postText.substring(0, 33) + " ...";
        }
        holder.text.setText(postText);
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public void addItem(PostItem postItem){
        Log.d("AddPost", "Item");
        posts.add(postItem);
        notifyDataSetChanged();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView userName;
        TextView title;
        TextView date;
        TextView text;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            userName = itemView.findViewById(R.id.post_user_name);
            title = itemView.findViewById(R.id.post_title);
            date = itemView.findViewById(R.id.post_date);
            text = itemView.findViewById(R.id.post_text);
        }
    }
}
