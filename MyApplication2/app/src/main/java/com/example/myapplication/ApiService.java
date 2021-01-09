package com.example.myapplication;

import android.icu.lang.UCharacter;

import androidx.core.content.res.FontResourcesParserCompat;

import com.google.gson.JsonArray;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    @GET("/contacts/")
    Call<JsonArray> getretrofitdata(@Query("uid") String uid);

    @POST("/contacts/")
    Call<String> createPost(@Body Contact contact);

    @POST("/images/")
    Call<String> images(@Body Image image);

    @GET("/images/")
    Call<JsonArray> getImages(@Query("uid") String uid);

    @POST("/users/login/")
    Call<String> login(@Body User user);

    @GET("/users/login")
    Call<String> login_get();
}
