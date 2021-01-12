package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostListActivity extends AppCompatActivity {

    RecyclerView postRecyclerView;
    PostLinearAdapter postLinearAdapter = new PostLinearAdapter();
//    public PostItem(String user, String title, String date, String pk, String text) {
    RetrofitClient retrofitClient = new RetrofitClient();
    String beach;
    String sea_pk;
    String beach_title;
    String sea_temp_name;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posts);
        Log.d("PostList", FirebaseAuth.getInstance().getCurrentUser().getUid());

        postRecyclerView = findViewById(R.id.post_list_recyclerView);
        postRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        postRecyclerView.setAdapter(postLinearAdapter);


        //get beach information
        Intent intent = getIntent();
        beach = intent.getStringExtra("beach");
        sea_pk = intent.getStringExtra("sea");
        beach_title = intent.getStringExtra("title");
        sea_temp_name = intent.getStringExtra("sea_name");
        System.out.println("sea is : " + sea_temp_name);
        TextView title_temp = findViewById(R.id.beach_title);
        title_temp.setText(beach_title);

        postLinearAdapter.setOnItemClickListener(new RecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                PostItem postItem = postLinearAdapter.getItem(position);
//                Log.d("get post", "pk : " + postItem.pk + " title : " + postItem.title);
                Intent intent = new Intent(getApplicationContext(), PostDetailActivity.class);
                intent.putExtra("post_pk", postItem.pk);
                intent.putExtra("beach", beach);
                intent.putExtra("pkk", sea_pk);
                intent.putExtra("beach_title", beach_title);
                intent.putExtra("sea_name", sea_temp_name);
                startActivity(intent);
                finish();
            }
        });

        Button to_beaches_btn = findViewById(R.id.to_beaches_btn);
        to_beaches_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Specific_Beach.class);
                intent.putExtra("pkk", sea_pk);
                intent.putExtra("sea", sea_temp_name);
                startActivity(intent);
                finish();
            }
        });


        Call<JsonArray> call = retrofitClient.apiService.getPosts(beach);
        call.enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                if(response.isSuccessful()){
                    JsonArray jsonArr = response.body();
                    for(JsonElement item : jsonArr) {
                        String user = item.getAsJsonObject().get("user").getAsString();
                        String title = item.getAsJsonObject().get("title").getAsString();
                        String text = item.getAsJsonObject().get("text").getAsString();
                        String post_pk = item.getAsJsonObject().get("post_pk").getAsString();
                        String date = item.getAsJsonObject().get("date").getAsString();
                        Log.d("Date : ", date);

                        String[] data_date_splited = date.split("T");
                        Log.d("Date 1", data_date_splited[0]);
                        Log.d("Date 2", data_date_splited[1]);

                        //Date
                        String[] data_date = data_date_splited[0].split("-");
                        int data_year = Integer.parseInt(data_date[0]);
                        int data_month = Integer.parseInt(data_date[1]);
                        int data_day = Integer.parseInt(data_date[2]);

                        //time
                        String[] data_time_all = data_date_splited[1].substring(0,8).split(":");
                        int data_hour = Integer.parseInt(data_time_all[0])+9;
                        int data_minute = Integer.parseInt(data_time_all[1]);
                        int data_second = Integer.parseInt(data_time_all[2]);

                        SimpleDateFormat format1 = new SimpleDateFormat ( "yyyy-MM-dd:HH-mm-ss");
                        Date time = new Date();
                        String current = format1.format(time);
                        String[] current_date_splited = current.split(":");
                        String[] current_date = current_date_splited[0].split("-");
                        String[] current_time = current_date_splited[1].split("-");
                        Log.d("Current Time : ", current);
                        //date
                        int current_year = Integer.parseInt(current_date[0]);
                        int current_month = Integer.parseInt(current_date[1]);
                        int current_day = Integer.parseInt(current_date[2]);

                        //time
                        int current_hour = Integer.parseInt(current_time[0]);
                        int current_minute = Integer.parseInt(current_time[1]);
                        int current_second = Integer.parseInt(current_time[2]);

                        String result_date;
                        if(data_year == current_year){
                            if(data_month == current_month){
                                if(data_day == current_day){
                                    if(data_hour == current_hour){
                                        if(data_minute == current_minute){
                                            result_date = Integer.toString(current_second-data_second) + "초 전";
                                        }
                                        else{
                                            result_date = Integer.toString(current_minute-data_minute) + "분 전";
                                        }
                                    }
                                    else{
                                        result_date = Integer.toString(current_hour-data_hour) + "시간 전";
                                    }
                                }
                                else{
                                    result_date = Integer.toString(current_day-data_day) + "일 전";
                                }
                            }
                            else{
                                result_date = Integer.toString(current_month - data_month) + "달 전";
                            }
                        }
                        else{
                            result_date = Integer.toString(current_year-data_year) + "년 전";
                        }

                        Log.d("Current Date", current);

                        postLinearAdapter.addItem(new PostItem(user, title, result_date, post_pk, text));
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {
                Log.d("Fail", "Get posts Fail");
            }
        });

       ImageButton postAddBtn = findViewById(R.id.post_add_btn);
       postAddBtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Log.d("Post", "Onclick");

               Intent intent = new Intent(getApplicationContext(), CreatePostActivity.class);
               //should change
               intent.putExtra("beach", beach);
               intent.putExtra("pkk", sea_pk);
               intent.putExtra("beach_title", beach_title);
               intent.putExtra("sea_name", sea_temp_name);
               startActivity(intent);
               finish();
           }
       });
    }
}
