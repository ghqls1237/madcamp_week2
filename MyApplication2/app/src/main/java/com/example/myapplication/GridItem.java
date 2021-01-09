package com.example.myapplication;

import android.graphics.Bitmap;

public class GridItem {
    public Bitmap image;
    public String pk;


    public GridItem(Bitmap image, String pk){
        this.image = image;
        this.pk = pk;
    }
}
