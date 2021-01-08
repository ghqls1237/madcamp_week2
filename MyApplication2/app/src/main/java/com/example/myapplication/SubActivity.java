package com.example.myapplication;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;


public class SubActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);




        Button button1 = (Button)findViewById(R.id.cancel_button);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(SubActivity.this,contacts_dbaccess.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        Button button2 = (Button)findViewById(R.id.save_button);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText Name = (EditText)findViewById(R.id.address_name);
                EditText Phone = (EditText)findViewById(R.id.address_phone);

                /*
                {
                    "phone":"01021321",
                    "name":"min",
                    "phone":"01058368290"
                },
                */

                //기존 txt 파일 읽어오는 코드
                BufferedReader br = null;
                try {
                    br = new BufferedReader(new FileReader(getFilesDir() + "address4.txt"));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                String readStr = "";
                String str = null;
                while (true) {
                    try {
                        if (!((str = br.readLine()) != null)) break;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    readStr += str + "\n";
                }
                readStr = readStr.substring(0,readStr.length()-4);
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }



                try{
                    BufferedWriter bw = new BufferedWriter(new FileWriter(getFilesDir() + "address4.txt", false));

                    bw.write( readStr +
                            "  },\n" +
                            "  {\n" +
                            "    \"picture\":\""+R.drawable.human+"\",\n" +
                            "    \"name\":\""+Name.getText().toString()+"\",\n" +
                            "    \"phone\":\""+Phone.getText().toString()+"\"\n" +
                            "  }\n" +
                            "]" );
                    bw.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
               // System.o

                Intent intent=new Intent(SubActivity.this,MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }
}
