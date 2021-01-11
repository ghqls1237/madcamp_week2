package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    private static String[] PKK;
    private static String[] sea;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    myAdapter adapter;
    //String[] sea = new String[4];

    String server_result = null;

    public Fragment3() {
        // Required empty public constructor
    }

    //json 파싱하기
    void doJSONParser() throws IOException {
        // json 데이터

        StringBuffer sb = new StringBuffer();

        String str = server_result;

        try {
            JSONArray jarray = new JSONArray(str);
            int len = jarray.length();
            PKK = new String[len];
            sea = new String[len];

            for (int i=0;i<len;i++){
                JSONObject jObject = jarray.getJSONObject(i);

                String pk = jObject.getString("pkk");
                String name = jObject.getString("name");

                PKK[i] = pk;
                sea[i] = name;
                //append("name:"+name+", phone:"+ phone);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
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

    class myAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return sea.length;
        }

        @Override
        public Object getItem(int position) {
            return sea[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView view = new TextView(getActivity().getApplicationContext());
            view.setText(sea[position]);
            view.setTextSize(30.0f);
            view.setGravity(Gravity.CENTER);
            view.setTextColor(Color.BLACK);
            return view;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_3, null);

        ListView listView = (ListView) view.findViewById(R.id.listView_tab3);


        RetrofitClient retrofitClient = new RetrofitClient();
        Call<JsonArray> call = retrofitClient.apiService.getSeas();
        call.enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                if (response.isSuccessful()) {
                    server_result = response.body().toString();
                    System.out.println("성공함");
                    try {
                        doJSONParser();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    adapter = new myAdapter();
                    listView.setAdapter(adapter);
                }
            }


            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {
                System.out.println("실패함");
            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(),Specific_Beach.class);
                intent.putExtra("pkk",PKK[position]);
                startActivity(intent);

            }
        });
        return view;
    }


//
//    textView = (TextView) view.findViewById(R.id.crawling);
//
//
//    doc = Jsoup.connect("https://www.wsbfarm.com/wavecam/WaveCamList").get();
//    Elements content = doc.select("div.video-post-content < h4");
//                    System.out.println(content.text());




}