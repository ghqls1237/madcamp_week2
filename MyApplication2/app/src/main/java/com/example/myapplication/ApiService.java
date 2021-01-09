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





























//    @DELETE("/contacts/{id}")
//    Call<JsonArray> deletePost(@Path("id") int id);


    //@PUT("/contacts")
    //Call<JsonArray> putPost(@POST("id") int id, @Body Post post);

//    //For Login
//    @GET
//    Call
//    @FormUrlEncoded
//    @POST("/users/login/{uid}&{email}&{method}&{nickname}")
//    @FormUrlEncoded
    @POST("/users/login/")
    Call<String> login(@Body User user);

    @GET("/users/login")
    Call<String> login_get();
}
