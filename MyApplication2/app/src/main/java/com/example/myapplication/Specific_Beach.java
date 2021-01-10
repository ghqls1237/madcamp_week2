package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class Specific_Beach extends AppCompatActivity {
    private Document doc = null;
    Elements content = null;
    Elements content2 = null;
    private static int position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specific__beach);

        Intent intent = getIntent();
        position = intent.getIntExtra("position",0);
        regionData task = new regionData();
        task.execute();
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

                for (int i=0; i<content.size(); i++){
                    beach = content.get(i).select("h4").text();
                    wave_height = content2.get(i).text();
                    arrayList.add(new wsb_beach(beach,wave_height));
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

            return arrayList;
        }

        @Override
        protected void onPostExecute(ArrayList<wsb_beach> arrayList) {
            // doInBackground 완료 후 실행시킬 코드
            for (int i = 0; i<content.size(); i++ ){
                System.out.println(arrayList.get(i).get_location());
                System.out.println(arrayList.get(i).getWave_height());
            }
        }
    }
}