package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.facebook.login.LoginManager;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;

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

        Button logoutBtn = findViewById(R.id.logout_btn);
        logoutBtn.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                Log.d("Button", "Click");
                FirebaseAuth.getInstance().signOut();
                LoginManager.getInstance().logOut();
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

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