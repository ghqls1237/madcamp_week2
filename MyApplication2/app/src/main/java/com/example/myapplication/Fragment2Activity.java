package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment2#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment2Activity extends Activity implements View.OnClickListener {


    //추가 변수
    private int pos;
    private ViewPager viewPager;

    View t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20, t21;
    private View[] thumbView = {t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20, t21};
    private Integer[] images = GlobalVariables.images;

    public Fragment2Activity(){

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_2_detail);

        //fragment에서 누른 이미지 확대
        Intent intent = getIntent();
        pos = intent.getExtras().getInt("id");

        viewPager = (ViewPager) findViewById(R.id.viewpager_fragment2_detail) ;
        BookmarksVPAdapter pagerAdapter = new BookmarksVPAdapter(this, 0) ;
        viewPager.setAdapter(pagerAdapter) ;
        viewPager.setCurrentItem(pos);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                ImageView heartBlankView = (ImageView) findViewById(R.id.heart_blank);
                ImageView heartView = (ImageView) findViewById(R.id.heart);

                if (GlobalVariables.inBookMarks(images[position])) {
                    heartBlankView.setVisibility(View.INVISIBLE);
                    heartView.setVisibility(View.VISIBLE);
                }
                else if (!GlobalVariables.inBookMarks(images[position])) {
                    heartBlankView.setVisibility(View.VISIBLE);
                    heartView.setVisibility(View.INVISIBLE);
                }
                switchHeartListener(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });


        //clickListener 설정
        for (int i = 0; i <= GlobalVariables.IMAGEMAX - 1; i++) {
            thumbView[i] = findViewById(GlobalVariables.thumbButton[i]);
            thumbView[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int index;
                    for (index = 0; index < GlobalVariables.IMAGEMAX - 1; index++) {
                        if (v.getId() == GlobalVariables.thumbButton[index]) { break;}
                    }
                    zoomImageFromThumb(GlobalVariables.images[index]);
                }
            });
        }

        switchHeartListener(pos);
        heartIndicator();
    }

    public void heartIndicator() {
        ImageView heartBlankView = (ImageView) findViewById(R.id.heart_blank);
        ImageView heartView = (ImageView) findViewById(R.id.heart);

        if (GlobalVariables.inBookMarks(images[pos])) {
            heartBlankView.setVisibility(View.INVISIBLE);
            heartView.setVisibility(View.VISIBLE);
        }
        else if (!GlobalVariables.inBookMarks(images[pos])) {
            heartBlankView.setVisibility(View.VISIBLE);
            heartView.setVisibility(View.INVISIBLE);
        }
    }


    public void switchHeartListener(int position) {
        View heartView = findViewById(R.id.heart);
        View heartBlankView = findViewById(R.id.heart_blank);
        heartBlankView.bringToFront();
        heartView.bringToFront();
        heartView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                switchHeart(position);
            }
        });
        heartBlankView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                switchHeart(position);
            }
        });
    }

    public void switchHeart(int position){
        final ImageView expandedImageView = (ImageView) findViewById(R.id.expanded_image);
        final ImageView heartView = (ImageView) findViewById(R.id.heart);
        final ImageView heartBlankView = (ImageView) findViewById(R.id.heart_blank);
        if (heartView.getVisibility() == View.VISIBLE) {
            heartView.setVisibility(View.INVISIBLE);

            //하트 없앨시 즐겨찾기 삭제
            GlobalVariables.removeBookMarks(images[position]);

            heartBlankView.setVisibility(View.VISIBLE);
        }
        else if (heartView.getVisibility() == View.INVISIBLE) {
            heartView.setVisibility(View.VISIBLE);

            //하트 생길시 즐겨찾기 추가
            GlobalVariables.addBookMarks(images[position]);

            heartBlankView.setVisibility(View.INVISIBLE);
        }
    }

    private void zoomImageFromThumb(int imageResId) {
        final ImageView expandedImageView = (ImageView) findViewById(R.id.expanded_image);
        final ImageView heartView = (ImageView) findViewById(R.id.heart);
        final ImageView heartBlankView = (ImageView) findViewById(R.id.heart_blank);

//        expandedImageView.setImageResource(images[position] = imageResId);
        int temp = 0;
        for (int i = 0; i < images.length; i++) {
            if (images[i] == imageResId) {
                temp = i;
                break;
            }
        }
        pos = temp;
        viewPager.setCurrentItem(pos);

        //즐겨찾기에 있으면 하트 표시, 없으면 반대 표시
        if (GlobalVariables.inBookMarks(images[pos])) {
            heartView.setVisibility(View.VISIBLE);
            heartBlankView.setVisibility(View.INVISIBLE);
        }
        else {
            heartView.setVisibility(View.INVISIBLE);
            heartBlankView.setVisibility(View.VISIBLE);
        }
        expandedImageView.setVisibility(View.VISIBLE);
    }

}
