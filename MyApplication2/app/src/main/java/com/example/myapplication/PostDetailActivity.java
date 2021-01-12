package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.w3c.dom.Comment;
import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostDetailActivity extends AppCompatActivity {
    private String pk;
    String pkk; //sea
    String beach; //beach
    String beach_title;
    RetrofitClient retrofitClient = new RetrofitClient();
    RecyclerView commentRecyclerView;
    CommentLinearAdapter commentLinearAdapter = new CommentLinearAdapter();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);
        Intent intent = getIntent();
        pk = intent.getStringExtra("post_pk");
        pkk = intent.getStringExtra("pkk");
        beach = intent.getStringExtra("beach");
        beach_title = intent.getStringExtra("beach_title");

        TextView title = findViewById(R.id.post_detail_title);
        TextView text = findViewById(R.id.post_detail_text);
        TextView date = findViewById(R.id.post_detail_date);
        TextView user = findViewById(R.id.post_detai_user);


        //뒤로 가기 버튼

        Button backBtn = findViewById(R.id.to_post_list_btn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PostListActivity.class);
                intent.putExtra("beach", beach);
                intent.putExtra("sea", pkk);
                intent.putExtra("title", beach_title);
                startActivity(intent);
                finish();
            }
        });

        commentRecyclerView = findViewById(R.id.post_detail_recyclerView);
        commentRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        commentRecyclerView.setAdapter(commentLinearAdapter);

        EditText commentText = findViewById(R.id.put_comment);

        ImageButton sendBtn = findViewById(R.id.comment_send_btn);
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String send_text = commentText.getText().toString();
                if(send_text != "") {
                    String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    String post = pk;

                    PostCommentItem comment = new PostCommentItem(uid, send_text, post);
                    Call<CommentItem> call2 = retrofitClient.apiService.postComment(comment);
                    commentText.setText(null);
                    call2.enqueue(new Callback<CommentItem>() {
                        @Override
                        public void onResponse(Call<CommentItem> call, Response<CommentItem> response) {
                            if (response.isSuccessful()) {
                                Log.d("Success", "Posting Comment");
                                CommentItem new_comment = response.body();
                                String new_comment_pk = new_comment.pk;
                                String new_comment_user = new_comment.user;
                                String new_comment_text = new_comment.text;
                                String new_comment_date = new_comment.date;

                                String data1 = new_comment_date;

                                String[] data_date_splited = data1.split("T");
                                Log.d("Date 1", data_date_splited[0]);
                                Log.d("Date 2", data_date_splited[1]);

                                //Date
                                String[] data_date = data_date_splited[0].split("-");
                                int data_year = Integer.parseInt(data_date[0]);
                                int data_month = Integer.parseInt(data_date[1]);
                                int data_day = Integer.parseInt(data_date[2]);

                                //time
                                String[] data_time_all = data_date_splited[1].substring(0, 8).split(":");
                                int data_hour = Integer.parseInt(data_time_all[0]);
                                int data_minute = Integer.parseInt(data_time_all[1]);
                                int data_second = Integer.parseInt(data_time_all[2]);

                                SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd:HH-mm-ss");
                                Date time = new Date();
                                String current = format1.format(time);
                                Log.d("Current Time", current.toString());
                                String[] current_date_splited = current.split(":");
                                String[] current_date = current_date_splited[0].split("-");
                                String[] current_time = current_date_splited[1].split("-");

                                //date
                                int current_year = Integer.parseInt(current_date[0]);
                                int current_month = Integer.parseInt(current_date[1]);
                                int current_day = Integer.parseInt(current_date[2]);

                                //time
                                int current_hour = Integer.parseInt(current_time[0]);
                                int current_minute = Integer.parseInt(current_time[1]);
                                int current_second = Integer.parseInt(current_time[2]);

                                String result_date;
                                if (data_year == current_year) {
                                    if (data_month == current_month) {
                                        if (data_day == current_day) {
                                            if (data_hour == current_hour) {
                                                if (data_minute == current_minute) {
                                                    result_date = Integer.toString(current_second - data_second) + "초 전";
                                                } else {
                                                    result_date = Integer.toString(current_minute - data_minute) + "분 전";
                                                }
                                            } else {
                                                result_date = Integer.toString(current_hour - data_hour) + "시간 전";
                                            }
                                        } else {
                                            result_date = Integer.toString(current_day - data_day) + "일 전";
                                        }
                                    } else {
                                        result_date = Integer.toString(current_month - data_month) + "달 전";
                                    }
                                } else {
                                    result_date = Integer.toString(current_year - data_year) + "년 전";
                                }
                                commentLinearAdapter.addItem(new CommentItem(new_comment_text,new_comment_user,new_comment_pk,result_date));
                            }
                        }

                        @Override
                        public void onFailure(Call<CommentItem> call, Throwable t) {
                            Log.d("Failure", "Posting Comment");
                        }
                    });
                }
            }
        });

        Call<PostItem> call = retrofitClient.apiService.getPostDetail(pk);
        call.enqueue(new Callback<PostItem>() {
            @Override
            public void onResponse(Call<PostItem> call, Response<PostItem> response) {
                if (response.isSuccessful()) {
//                    Log.d("PostDetail", " " + response.body());
                    PostItem post = response.body();
                    title.setText(post.title);
                    text.setText(post.text);
                    user.setText(post.user);

                    String data1 = post.date;

                    String[] data_date_splited = data1.split("T");
                    Log.d("Date 1", data_date_splited[0]);
                    Log.d("Date 2", data_date_splited[1]);

                    //Date
                    String[] data_date = data_date_splited[0].split("-");
                    int data_year = Integer.parseInt(data_date[0]);
                    int data_month = Integer.parseInt(data_date[1]);
                    int data_day = Integer.parseInt(data_date[2]);

                    //time
                    String[] data_time_all = data_date_splited[1].substring(0, 8).split(":");
                    int data_hour = Integer.parseInt(data_time_all[0]);
                    int data_minute = Integer.parseInt(data_time_all[1]);
                    int data_second = Integer.parseInt(data_time_all[2]);

                    SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd:HH-mm-ss");
                    Date time = new Date();
                    String current = format1.format(time);
                    String[] current_date_splited = current.split(":");
                    String[] current_date = current_date_splited[0].split("-");
                    String[] current_time = current_date_splited[1].split("-");

                    //date
                    int current_year = Integer.parseInt(current_date[0]);
                    int current_month = Integer.parseInt(current_date[1]);
                    int current_day = Integer.parseInt(current_date[2]);

                    //time
                    int current_hour = Integer.parseInt(current_time[0]);
                    int current_minute = Integer.parseInt(current_time[1]);
                    int current_second = Integer.parseInt(current_time[2]);

                    String result_date;
                    if (data_year == current_year) {
                        if (data_month == current_month) {
                            if (data_day == current_day) {
                                if (data_hour == current_hour) {
                                    if (data_minute == current_minute) {
                                        result_date = Integer.toString(current_second - data_second) + "초 전";
                                    } else {
                                        result_date = Integer.toString(current_minute - data_minute) + "분 전";
                                    }
                                } else {
                                    result_date = Integer.toString(current_hour - data_hour) + "시간 전";
                                }
                            } else {
                                result_date = Integer.toString(current_day - data_day) + "일 전";
                            }
                        } else {
                            result_date = Integer.toString(current_month - data_month) + "달 전";
                        }
                    } else {
                        result_date = Integer.toString(current_year - data_year) + "년 전";
                    }
                    date.setText(result_date);
                }
            }

            @Override
            public void onFailure(Call<PostItem> call, Throwable t) {
                Log.d("Failure", "Get Post Detail");
            }
        });

        Call<ArrayList<CommentItem>> call1 = retrofitClient.apiService.getComments(pk);
        call1.enqueue(new Callback<ArrayList<CommentItem>>() {

            @Override
            public void onResponse(Call<ArrayList<CommentItem>> call, Response<ArrayList<CommentItem>> response) {
                if (response.isSuccessful()) {
                    ArrayList<CommentItem> comments = response.body();
//                    CommentItem(String text, String user, String post_pk, String date){
                    for(CommentItem item : comments){
                        String data1 = item.date;

                        String[] data_date_splited = data1.split("T");
                        Log.d("Date 1", data_date_splited[0]);
                        Log.d("Date 2", data_date_splited[1]);

                        //Date
                        String[] data_date = data_date_splited[0].split("-");
                        int data_year = Integer.parseInt(data_date[0]);
                        int data_month = Integer.parseInt(data_date[1]);
                        int data_day = Integer.parseInt(data_date[2]);

                        //time
                        String[] data_time_all = data_date_splited[1].substring(0, 8).split(":");
                        int data_hour = Integer.parseInt(data_time_all[0]);
                        int data_minute = Integer.parseInt(data_time_all[1]);
                        int data_second = Integer.parseInt(data_time_all[2]);

                        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd:HH-mm-ss");
                        Date time = new Date();
                        String current = format1.format(time);
                        String[] current_date_splited = current.split(":");
                        String[] current_date = current_date_splited[0].split("-");
                        String[] current_time = current_date_splited[1].split("-");

                        //date
                        int current_year = Integer.parseInt(current_date[0]);
                        int current_month = Integer.parseInt(current_date[1]);
                        int current_day = Integer.parseInt(current_date[2]);

                        //time
                        int current_hour = Integer.parseInt(current_time[0]);
                        int current_minute = Integer.parseInt(current_time[1]);
                        int current_second = Integer.parseInt(current_time[2]);

                        String result_date;
                        if (data_year == current_year) {
                            if (data_month == current_month) {
                                if (data_day == current_day) {
                                    if (data_hour == current_hour) {
                                        if (data_minute == current_minute) {
                                            result_date = Integer.toString(current_second - data_second) + "초 전";
                                        } else {
                                            result_date = Integer.toString(current_minute - data_minute) + "분 전";
                                        }
                                    } else {
                                        result_date = Integer.toString(current_hour - data_hour) + "시간 전";
                                    }
                                } else {
                                    result_date = Integer.toString(current_day - data_day) + "일 전";
                                }
                            } else {
                                result_date = Integer.toString(current_month - data_month) + "달 전";
                            }
                        } else {
                            result_date = Integer.toString(current_year - data_year) + "년 전";
                        }
                        commentLinearAdapter.addItem(new CommentItem(item.text, item.user, item.pk, result_date));
                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<CommentItem>> call, Throwable t) {
                Log.d("Failure", "Get Comments");
            }
        });
    }
}
