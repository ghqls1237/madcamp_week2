package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment3#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment3 extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Fragment3() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment3.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment3 newInstance(String param1, String param2) {
        Fragment3 fragment = new Fragment3();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        //남자 여자 사진 크기 맞추기

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_3, null);

        ImageButton button1 = view.findViewById(R.id.male_button);
        ImageButton button2 = view.findViewById(R.id.female_button);
        TextView text1 = view.findViewById(R.id.male_text);
        TextView text2 = view.findViewById(R.id.female_text);
        ImageView sunglasses1 = view.findViewById(R.id.sunglasses_1);

        //디스플레이 크기 가져옴
        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) getActivity().getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(metrics);

        //이미지 width, height 디스플레이에 맞게 설정
        Point winSize = new Point();
        getActivity().getWindowManager().getDefaultDisplay().getSize(winSize);
        int width = winSize.x / 2 - 5;
        int sunglasses_width = winSize.x / 3 - 5;
        int sunglasses_height = winSize.y / 2;

        //이미지 위치 설정
        button1.setLayoutParams(new LinearLayout.LayoutParams(width,width));
//        button1.bringToFront();
        button2.setLayoutParams(new LinearLayout.LayoutParams(width,width));
        text1.setLayoutParams(new LinearLayout.LayoutParams(width, 100));
        text2.setLayoutParams(new LinearLayout.LayoutParams(width, 100));
        sunglasses1.setLayoutParams(new LinearLayout.LayoutParams(sunglasses_width, sunglasses_height - 10));

        //애니메이션 효과 추가
        Animation mAnim = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.activity_tab3_animation_drop); // 선글라스
        Animation mAnim2 = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.activity_tab3_animation_scale_male); // 소년 움직이기
        Animation mAnim5 = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.activity_tab3_animation_scale_female); // 소녀 움직이기
        Animation mAnim3 = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.activity_tab3_animation_run_right); // 소녀 튀기
        Animation mAnim4 = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.activity_tab3_animation_run_left); // 소년 튀기

        mAnim.setInterpolator(getActivity().getApplicationContext(),android.R.anim.accelerate_interpolator);
        mAnim2.setInterpolator(getActivity().getApplicationContext(),android.R.anim.accelerate_interpolator);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //BGM 설정
                MediaPlayer mediaPlayer = MediaPlayer.create(getActivity().getApplicationContext(), R.raw.thug_life);

                mediaPlayer.start();

                sunglasses1.setVisibility(View.VISIBLE);
                sunglasses1.setAnimation(mAnim);
                button1.setAnimation(mAnim2);
                button2.setAnimation(mAnim3);

                sunglasses1.startAnimation(mAnim);
                button1.startAnimation(mAnim2);
                text1.setVisibility(View.INVISIBLE);
                text2.setVisibility(View.INVISIBLE);

                //Timer 설정후 끝나면 액티비티 실행
                CountDownTimer countDownTimer = new CountDownTimer(7000,10) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                    }

                    @Override
                    public void onFinish() {
                        mediaPlayer.stop();
                        mediaPlayer.reset();

                        Intent intent = new Intent(getActivity(), Tab3Male.class);
                        startActivity(intent);
                        sunglasses1.setVisibility(View.INVISIBLE);
                        sunglasses1.clearAnimation();
                        button1.clearAnimation();
                        button2.clearAnimation();
                        text1.setVisibility(View.VISIBLE);
                        text2.setVisibility(View.VISIBLE);
                    }
                };
                countDownTimer.start();

            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //BGM 설정
                MediaPlayer mediaPlayer = MediaPlayer.create(getActivity().getApplicationContext(), R.raw.thug_life);

                mediaPlayer.start();

                sunglasses1.setVisibility(View.VISIBLE);
                sunglasses1.setAnimation(mAnim);
                button2.setAnimation(mAnim5);
                button1.setAnimation(mAnim4);

                sunglasses1.startAnimation(mAnim);
                button2.startAnimation(mAnim5);
                text1.setVisibility(View.INVISIBLE);
                text2.setVisibility(View.INVISIBLE);

                //Timer 설정후 끝나면 액티비티 실행
                CountDownTimer countDownTimer = new CountDownTimer(7000,10) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                    }

                    @Override
                    public void onFinish() {
                        mediaPlayer.stop();
                        mediaPlayer.reset();

                        Intent intent = new Intent(getActivity(), Tab3Female.class);
                        startActivity(intent);
                        sunglasses1.setVisibility(View.INVISIBLE);
                        sunglasses1.clearAnimation();
                        button1.clearAnimation();
                        button2.clearAnimation();
                        text1.setVisibility(View.VISIBLE);
                        text2.setVisibility(View.VISIBLE);
                    }
                };
                countDownTimer.start();

            }
        });
        return view;
    }
}