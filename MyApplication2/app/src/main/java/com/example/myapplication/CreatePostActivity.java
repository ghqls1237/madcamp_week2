package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreatePostActivity extends AppCompatActivity {

    EditText editTitle;
    EditText editText;
    String beach;
    RetrofitClient retrofitClient = new RetrofitClient();
    String sea_pkk;
    String beach_title;
    String sea_temp_name;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);

        Intent intent = getIntent();
        beach = intent.getStringExtra("beach");
        sea_pkk = intent.getStringExtra("pkk");
        beach_title = intent.getStringExtra("beach_title");
        sea_temp_name = intent.getStringExtra("sea_name");

        editTitle = findViewById(R.id.create_post_title);
        editText = findViewById(R.id.create_post_text);

        Button backBtn = findViewById(R.id.to_posts_list_btn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PostListActivity.class);
                intent.putExtra("beach", beach);
                intent.putExtra("sea", sea_pkk);
                intent.putExtra("title", beach_title);
                intent.putExtra("sea_name", sea_temp_name);
                startActivity(intent);
                finish();
            }
        });

        Button submitBtn = findViewById(R.id.submit_btn);
        submitBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String title = editTitle.getText().toString();
                String text = editText.getText().toString();
                String uid =  FirebaseAuth.getInstance().getCurrentUser().getUid();
                Log.d("Submit", beach);
                Call<String> call = retrofitClient.apiService.createPost(new Post(beach, uid, title, text));
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if(response.isSuccessful()){
                            Log.d("Success", "Create Post");
                            Intent intent = new Intent(getApplicationContext(), PostListActivity.class);
                            intent.putExtra("beach", beach);
                            intent.putExtra("sea", sea_pkk);
                            intent.putExtra("title", beach_title);
                            intent.putExtra("sea_name", sea_temp_name);
                            startActivity(intent);
                            finish();
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Log.d("Failure", "Create Post");
                    }
                });
            }
        });
    }
}
