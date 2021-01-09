package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment3#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment3 extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_3, null);
//        Button logoutBtn = view.findViewById(R.id.logout_btn);
//        logoutBtn.setOnClickListener(new Button.OnClickListener(){
//            @Override
//            public void onClick(View v) {
//                Log.d("Button", "Click");
//                FirebaseAuth.getInstance().signOut();
//                LoginManager.getInstance().logOut();
//                Intent intent = new Intent(getActivity(), LoginActivity.class);
//                startActivity(intent);
//                getActivity().finish();
//            }
//        });
        return view;
    }
}