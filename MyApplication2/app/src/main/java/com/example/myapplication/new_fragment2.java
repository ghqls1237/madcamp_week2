package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;


public class new_fragment2 extends AppCompatActivity {


    //gridView 사용 위한 ImageAdapter
    public class ImageAdapter extends BaseAdapter {
        private Context context;

        private Integer[] images = GlobalVariables.images;

        public ImageAdapter(Context con){
            this.context = con;
        }

        @Override
        public int getCount() {
            return images.length;
        }

        @Override
        public Object getItem(int position) {
            return images[position];
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageView;

            Point winSize = new Point();
            getWindowManager().getDefaultDisplay().getSize(winSize);
            int width = winSize.x;
            int height = winSize.y;
            width = width / 3 - 10;
            height = height / 5 - 20;

            if(convertView==null) {
                imageView = new ImageView(context);
                imageView.setLayoutParams(new ViewGroup.LayoutParams(width,height));
                imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                imageView.setPadding(5,5,5,5);
            }
            else {
                imageView = (ImageView) convertView;
            }
            imageView.setImageResource(images[position]);
            return imageView;
        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_fragment2);

        Intent listen =getIntent();
        String name = listen.getStringExtra("name");
        String phone = listen.getStringExtra("phone");
        int index3 = listen.getIntExtra("index3",0);
        int position2 = listen.getIntExtra("index",0);
        int picture = listen.getIntExtra("picture",0);

        GridView gridView = (GridView) findViewById(R.id.new_gridView1);
        gridView.setAdapter(new ImageAdapter(this));

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {

                GlobalVariables.image_change = 1;
                Intent i = new Intent(new_fragment2.this, Address_revise.class);
                //i.putExtra("picture",)
                i.putExtra("id", position);
                i.putExtra("name",name);
                i.putExtra("phone",phone);
                i.putExtra("index3",index3);
                i.putExtra("index",position2);
                startActivity(i);
                finish();
            }
        });
    }


}