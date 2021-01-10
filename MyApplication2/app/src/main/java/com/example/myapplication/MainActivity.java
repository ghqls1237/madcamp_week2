package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

import org.json.JSONException;
import org.jsoup.Jsoup;
import org.w3c.dom.Document;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ViewPager vp = findViewById(R.id.viewpager);
        VPAdapter adapter = null;
        try {
            adapter = new VPAdapter(getSupportFragmentManager());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        vp.setAdapter(adapter);

        //tab과 viewpager 연동시키는 과정
        TabLayout tab=findViewById(R.id.tab);
        tab.setupWithViewPager(vp);

    }
    




}