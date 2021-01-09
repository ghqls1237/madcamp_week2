package com.example.myapplication;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SubActivity extends AppCompatActivity {
    private static String uid;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);




        Button button1 = (Button)findViewById(R.id.cancel_button);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(SubActivity.this,MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });

        Button button2 = (Button)findViewById(R.id.save_button);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText Name = (EditText)findViewById(R.id.address_name);
                EditText Phone = (EditText)findViewById(R.id.address_phone);

                Intent listener = getIntent();
                uid = listener.getStringExtra("uid");

                RetrofitClient retrofitClient = new RetrofitClient();
                Contact contact = new Contact(Name.getText().toString(),Phone.getText().toString(),"null",uid);

                Call<String> call = retrofitClient.apiService.createPost(contact);
                call.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                        if (response.isSuccessful()) {
                            //System.out.println("성공함");
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        //System.out.println("실패함");
                    }

                });


               // System.o

                Intent intent=new Intent(SubActivity.this,MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });
    }
}
