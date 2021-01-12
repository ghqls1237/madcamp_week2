package com.example.myapplication;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuView;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.JsonArray;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GridRecyclerViewAdapter extends RecyclerView.Adapter<GridRecyclerViewAdapter.MyViewHolder> {

    ArrayList<GridItem> arr = new ArrayList<GridItem>();
    RetrofitClient retrofitClient = new RetrofitClient();
    int mPosition = 0;
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("OncreateViewHolder", "OK");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_image, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.imageView.setImageBitmap(arr.get(position).image);
    }

    @Override
    public int getItemCount() {
        return arr.size();
    }

    public void setPosition(int position){
        mPosition = position;
    }

    public void remove_all(){
        arr.removeAll(arr);
    }
    public void removeItem(int position){
        arr.remove(position);
        notifyDataSetChanged();
    }

    public void addItem(GridItem gridItem){
        Log.d("Add", "Item");
        arr.add(gridItem);
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.grid_image);

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    Log.d("LongClick", "Item : " + getAdapterPosition());
                    Call<String> call = retrofitClient.apiService.deleteImages(arr.get(getAdapterPosition()).pk);
                    removeItem(getAdapterPosition());
                    call.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            Log.d("Delete image", "Success");
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
                            Log.d("Delete image", "Error");
                        }
                    });
                    return false;
                }
            });


        }

    }
}