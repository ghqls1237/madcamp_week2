package com.example.myapplication;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Base64;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;


public class Fragment2 extends Fragment{

    ArrayList<GridItem> image_list = new ArrayList<GridItem>();
    int columns = 3;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    GridRecyclerViewAdapter recyclerViewAdapter = new GridRecyclerViewAdapter();

    //retrofit
    RetrofitClient retrofitClient = new RetrofitClient();
    private static final int REQUEST_CODE = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_2, container, false);

        //Recycler View
        recyclerView = view.findViewById(R.id.recycler_view_grid);
        layoutManager = new GridLayoutManager(getActivity(), columns);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(recyclerViewAdapter);

        Call<JsonArray> call = retrofitClient.apiService.getImages(FirebaseAuth.getInstance().getCurrentUser().getUid());
        call.enqueue(new Callback<JsonArray>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                if(response.isSuccessful()) {
                    Log.d("Success", "Response Success");
                    JsonArray jsonArr = response.body();
                    for(JsonElement item : jsonArr){
                        String data = item.getAsJsonObject().get("image").getAsString();
                        String pk = item.getAsJsonObject().get("pk").getAsString();
//                        Log.d("Element", data);
                        byte[] bytePlainOrg = Base64.getDecoder().decode(data);
                        ByteArrayInputStream inputStream = new ByteArrayInputStream(bytePlainOrg);
                        Bitmap bitmap = BitmapFactory.decodeStream((inputStream));
                        recyclerViewAdapter.addItem(new GridItem(bitmap, pk));
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {
                Log.d("Error", "Getting Images Fail");
            }
        });


        ImageButton add_btn = view.findViewById(R.id.add_btn);
        add_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });

        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == REQUEST_CODE)
        {
            if(resultCode == RESULT_OK)
            {
                try{
                    InputStream in = getActivity().getContentResolver().openInputStream(data.getData());

                    Bitmap bitmap = BitmapFactory.decodeStream(in);
                    Bitmap bmpCompressed = Bitmap.createScaledBitmap(bitmap, 300, 300, true);
                    in.close();
                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    bmpCompressed.compress(Bitmap.CompressFormat.PNG, 100, outputStream);

                    byte[] image = outputStream.toByteArray();
                    String imageBase64 = Base64.getEncoder().encodeToString(image);

                    Image image_server = new Image(FirebaseAuth.getInstance().getCurrentUser().getUid(), imageBase64);
                    //server로 보내기
                    Call<String> call = retrofitClient.apiService.images(image_server);
                    call.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            String pk = response.body();
                            Log.d("image", "통신 성공함");
                            recyclerViewAdapter.addItem(new GridItem(bitmap, pk));
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
                            Log.d("image", "통신 실패");
                        }
                    });

                }catch(Exception e)
                {

                }
            }
            else if(resultCode == RESULT_CANCELED)
            {
                Toast.makeText(getActivity(), "사진 선택 취소", Toast.LENGTH_LONG).show();
            }
        }
    }

}
