package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.util.ArrayList;

public class BookmarksVPAdapter extends PagerAdapter {

    private Context mContext = null;
    private int flag;

    private Integer[] items = GlobalVariables.bookMarks.toArray(new Integer[GlobalVariables.bookMarks.size()]);
    private Integer[] originals = GlobalVariables.images;

    public BookmarksVPAdapter(){
    }

    // Context를 전달받아 mContext에 저장하는 생성자 추가.
    public BookmarksVPAdapter(Context context, int flag) {
        mContext = context ;
        this.flag = flag;
    }

    private View mCurrentView;

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        mCurrentView = (View)object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = null ;
        if (mContext != null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if (flag == 0) {
                view = inflater.inflate(R.layout.fragment_2_pager, container, false);

                ImageView imageView = (ImageView) view.findViewById(R.id.expanded_image);
                imageView.setImageResource(originals[position]);

            } else if (flag == 1) {
                view = inflater.inflate(R.layout.fragment_2_bookmarks_pager, container, false);

                ImageView imageView = (ImageView) view.findViewById(R.id.expanded_image_pager);
                imageView.setImageResource(items[position]);
            }
        }
        // 뷰페이저에 추가.
        container.addView(view) ;
        return view ;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // 뷰페이저에서 삭제.
//        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        if (flag==0) return originals.length;
        return items.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (View) object;
    }

}

