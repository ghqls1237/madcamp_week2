package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment4 extends Fragment {


    RecyclerView notiRecyclerView;
    NotificationAdapter notiAdapter = new NotificationAdapter();
    RetrofitClient retrofitClient = new RetrofitClient();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_4, container, false);
        notiRecyclerView = view.findViewById(R.id.noti_recyclerView);
        notiRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        notiRecyclerView.setAdapter(notiAdapter);
        notiAdapter.remove_all();
        notiAdapter.setOnItemClickListener(new RecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                NotiItem notiItem = notiAdapter.getItem(position);
//                Log.d("get post", "pk : " + postItem.pk + " title : " + postItem.title);
                Intent intent = new Intent(requireContext(), PostDetailActivity.class);
                intent.putExtra("post_pk", notiItem.post_pk);
                intent.putExtra("beach", notiItem.beach);
                intent.putExtra("pkk", notiItem.pkk);
                intent.putExtra("beach_title", notiItem.beach_title);
                startActivity(intent);
                getActivity().finish();
            }
        });


        Call<JsonArray> call = retrofitClient.apiService.getNotis(FirebaseAuth.getInstance().getCurrentUser().getUid());
        call.enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                if(response.isSuccessful()){
                    Log.d("Get notis", "Success");
                    JsonArray items = response.body();
                    for(JsonElement noti : items){
                        String noti_post_pk = noti.getAsJsonObject().get("post_pk").getAsString();
                        String noti_user = noti.getAsJsonObject().get("user").getAsString();
                        String noti_pkk = noti.getAsJsonObject().get("pkk").getAsString();
                        String noti_beach = noti.getAsJsonObject().get("beach").getAsString();
                        String noti_beach_title = noti.getAsJsonObject().get("beach_title").getAsString();
                        String noti_date = noti.getAsJsonObject().get("date").getAsString();
                        Log.d("Noti Date", noti_date);

                        String[] data_date_splited = noti_date.split("T");

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
                        //                    NotiItem(String text, String post_pk, String date, String user, String pkk, String beach, String beach_title){

                        notiAdapter.addItem(new NotiItem(noti_post_pk, result_date, noti_user, noti_pkk, noti_beach, noti_beach_title));
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {
                Log.d("Get notis", "Failure");
            }
        });

        return view;
    }
}