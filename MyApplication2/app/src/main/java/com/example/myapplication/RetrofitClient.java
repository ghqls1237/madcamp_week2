package com.example.myapplication;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://c4ce576a616c.ngrok.io")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public ApiService apiService = retrofit.create(ApiService.class);
}
