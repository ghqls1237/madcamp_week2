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

import java.util.ArrayList;

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

        notiAdapter.setOnItemClickListener(new RecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                NotiItem notiItem = notiAdapter.getItem(position);
//                Log.d("get post", "pk : " + postItem.pk + " title : " + postItem.title);
                Intent intent = new Intent(requireContext(), PostDetailActivity.class);
                intent.putExtra("post_pk", notiItem.post_pk);
                intent.putExtra("beach", notiItem.beach);
                intent.putExtra("pkk", notiItem.pkk;
                intent.putExtra("beach_title", notiItem.beach_title);
                startActivity(intent);
                getActivity().finish();
            }
        });


        Call<ArrayList<NotiItem>> call = retrofitClient.apiService.getNotis(FirebaseAuth.getInstance().getCurrentUser().getUid());
        call.enqueue(new Callback<ArrayList<NotiItem>>() {

            @Override
            public void onResponse(Call<ArrayList<NotiItem>> call, Response<ArrayList<NotiItem>> response) {
                if(response.isSuccessful()){
                    Log.d("Get notis", "Success");
                }
            }

            @Override
            public void onFailure(Call<ArrayList<NotiItem>> call, Throwable t) {
                Log.d("Get notis", "Failure");
            }
        });

        return view;
    }
}