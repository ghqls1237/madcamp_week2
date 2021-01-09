package com.example.myapplication;

import android.graphics.Bitmap;

public class GridItem {
    public Bitmap image;
    public int idx;


    public GridItem(Bitmap image, int idx){
        this.image = image;
        this.idx = idx;
    }
}
