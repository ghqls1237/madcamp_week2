package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

import org.json.JSONException;

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

    //Fragment2 <-> Fragment2Bookmarks transaction에 사용
    public void onFragmentChange(int index) {
        Fragment2Bookmarks fragment2Bookmarks = new Fragment2Bookmarks();
        Fragment2 fragment2 = new Fragment2();

//        fragment2Detail.zoomImageFromThumb(R.drawable.a1);
//        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment2Detail).commit();
        if(index == 0) {
            getSupportFragmentManager().beginTransaction().remove(fragment2Bookmarks).commit();
            getSupportFragmentManager().beginTransaction().replace(R.id.container_bookmarks, fragment2).commit();
        }
        else if (index ==1) {
            getSupportFragmentManager().beginTransaction().remove(fragment2).commit();
            getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment2Bookmarks).commit();
        }
    }




}