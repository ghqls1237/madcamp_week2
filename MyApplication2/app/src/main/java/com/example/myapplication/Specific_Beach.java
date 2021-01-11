package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

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


public class Specific_Beach extends AppCompatActivity {
    private Document doc = null;
    Elements content = null;
    Elements content2 = null;
    Elements content3 = null;
    private static String pkk;

    private static String[] pkk_server;
    private static String[] beach_server;

    private static String[] level_server;
    private static String[] wave_server;

    private static String[] beach_wsb;
    private static String[] level_wsb;
    private static String[] wave_wsb;

    private static String result = null;

    private static int content_size = 0;
    void doJSONParser() throws IOException {
        // json 데이터

        StringBuffer sb = new StringBuffer();

        String str = result;

        try {
            JSONArray jarray = new JSONArray(str);
            int len = jarray.length();
            beach_server = new String[len];
            pkk_server = new String[len];

            for (int i=0;i<len;i++){
                JSONObject jObject = jarray.getJSONObject(i);


                String pkk2 = jObject.getString("pkk");
                String name = jObject.getString("name");

                pkk_server[i] = pkk2;
                beach_server[i] = name;

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specific__beach);


        Intent intent = getIntent();
        pkk = intent.getStringExtra("pkk"); //바다 pkk

        regionData task = new regionData();
        task.execute();


        ListView listView;
        beach_listview_adapter adapter;

        adapter = new beach_listview_adapter();

        listView = (ListView) findViewById(R.id.listview_beach);
        listView.setAdapter(adapter);

        try {
            Thread.sleep(800);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        RetrofitClient retrofitClient = new RetrofitClient();
        Call<JsonArray> call = retrofitClient.apiService.getBeaches(pkk);
        call.enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                if (response.isSuccessful()) {

                    result = response.body().toString();
                    try {
                        doJSONParser();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                    level_server = new String[beach_server.length];
                    wave_server = new String[beach_server.length];


                    adapter.delete_all();
                    for (int i = 0 ;i<beach_server.length;i++){
                        for (int j = 0; j<content_size;j++){
                            if (beach_wsb[j] != null && beach_wsb[j].contains(beach_server[i])){
                                level_server[i] = level_wsb[j];
                                wave_server[i] = wave_wsb[j];
                                adapter.addItem(beach_server[i],wave_server[i],level_server[i]);
                                break;
                            }
                        }
                    }


                    adapter.notifyDataSetChanged();
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
                Intent intent = new Intent(Specific_Beach.this,PostListActivity.class);
                intent.putExtra("beach",pkk_server[position]);
                intent.putExtra("sea", pkk);
                intent.putExtra("title",beach_server[position]);
                startActivity(intent);
                finish();
            }
        });


    }

    private class regionData extends AsyncTask<Void,Void, ArrayList<wsb_beach>> {

        @Override
        protected ArrayList<wsb_beach> doInBackground(Void... voids) {
            ArrayList<wsb_beach> arrayList = new ArrayList<wsb_beach>();
            try {
                doc = Jsoup.connect("https://www.wsbfarm.com/wavecam/WaveCamList").get();
                content = doc.select("div.video-post-content");
                content2 = doc.select("div.video-post-content ul li");
                String beach = null;
                String wave_height = null;
                String comment = null;

                content_size = content.size();
                content_size = content_size/2;
                for (int i=0; i<content_size; i++){
                    beach = content.get(i).select("h4").text();
                    wave_height = content2.get(2*i).text();
                    comment = content.get(i).select("h3").text();
                    arrayList.add(new wsb_beach(beach,wave_height,comment));
                }
                beach_wsb = new String[content_size];
                wave_wsb = new String[content_size];
                level_wsb = new String[content_size];
            } catch (IOException e) {
                e.printStackTrace();
            }

            return arrayList;
        }

        @Override
        protected void onPostExecute(ArrayList<wsb_beach> arrayList) {
            // doInBackground 완료 후 실행시킬 코드
            for (int i = 0; i<content_size; i++ ){
                if (i != 7 && i !=6 && i != 10 && i !=11 && i!=18 && i!=20 && i!=23 && i !=26 && i !=27 && i!=28 && i != 30 && i != 39){
                    beach_wsb[i] = arrayList.get(i).get_location();
                    level_wsb[i] = arrayList.get(i).get_comment();
                    wave_wsb[i] = arrayList.get(i).getWave_height();
                }
            }
        }
    }
}




