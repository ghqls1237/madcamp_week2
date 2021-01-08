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

public interface ApiService {
    @GET("/contacts")
    Call<JsonArray> getretrofitdata();

    @FormUrlEncoded
    @POST("/contacts")
    Call<JsonArray> createPost(@Field("name") String name, @Field("phone") String phone, @Field("image") String image);

    @DELETE("/contacts")
    Call<Void> deletePost(@Path("id") int id);

    //@PUT("/contacts")
    //Call<JsonArray> putPost(@POST("id") int id, @Body Post post);
}
