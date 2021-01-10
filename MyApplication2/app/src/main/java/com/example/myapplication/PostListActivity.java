package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;

public class PostListActivity extends AppCompatActivity {

    RecyclerView postRecyclerView;
    PostLinearAdapter postLinearAdapter = new PostLinearAdapter();
//    public PostItem(String user, String title, String date, String pk, String text) {
    PostItem temp1 = new PostItem("원진", "하이", "2021-2-3", "3", "안녕하세요 안녕하세요 안녕하세요 안녕하세요 안녕하세요 안녕하세요 안녕하세요 안녕하세요 안녕하세요 안녕하세요 안녕하세요 안녕하세요 안녕하세요 안녕하세요 안녕하세요 안녕하세요 안녕하세요 ");


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posts);
        Log.d("PostList", FirebaseAuth.getInstance().getCurrentUser().getUid());

        postRecyclerView = findViewById(R.id.post_list_recyclerView);
        postRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        postRecyclerView.setAdapter(postLinearAdapter);

       ImageButton postAddBtn = findViewById(R.id.post_add_btn);
       postAddBtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Log.d("Post", "Onclick");

               Intent intent = new Intent(getApplicationContext(), CreatePostActivity.class);
               intent.putExtra("beach", "1");
               startActivity(intent);
               finish();
           }
       });
    }
}
