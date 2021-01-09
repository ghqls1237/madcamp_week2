package com.example.myapplication;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
//969e55fb388c.ngrok.io
public class RetrofitClient {
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://dcb0a08cb406.ngrok.io")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public ApiService apiService = retrofit.create(ApiService.class);
}
