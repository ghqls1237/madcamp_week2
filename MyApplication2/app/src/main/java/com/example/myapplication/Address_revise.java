package com.example.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Address_revise extends AppCompatActivity {

EditText name;
EditText phone;
int index3;
String prev_phone;
String prev_name;
int position;
int picture;
    //name.setText("")

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_revise);
        ImageButton imageButton = findViewById(R.id.frag1_human_edit);

        Intent message = getIntent();

        picture = message.getIntExtra("picture",0);
        position = message.getIntExtra("index",0);

        prev_name = message.getStringExtra("name");
        prev_phone = message.getStringExtra("phone");
        index3 = message.getIntExtra("index3",0);

        name = findViewById(R.id.edit_address_name);
        phone = findViewById(R.id.edit_address_phone);

        name.setText(prev_name);
        phone.setText(prev_phone);
        imageButton.setImageResource(picture);

        if (GlobalVariables.image_change == 1){
            int index = message.getIntExtra("id", 0);
            picture = GlobalVariables.images[index];
            imageButton.setImageResource(picture);
            GlobalVariables.image_change = 0;

        }





        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Address_revise.this);
                builder.setNeutralButton("select image",new DialogInterface.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent=new Intent(Address_revise.this,new_fragment2.class);
                        intent.putExtra("name",name.getText().toString());
                        intent.putExtra("phone",phone.getText().toString());
                        intent.putExtra("index3",index3);
                        intent.putExtra("index",position);
                        startActivity(intent);
                        finish();
                    }
                });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });




        Button button1 = (Button)findViewById(R.id.cancel_button2);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Address_revise.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        Button button2 = (Button)findViewById(R.id.save_button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //name.getText()
                //phone.getText()

                BufferedReader br = null;
                try {
                    br = new BufferedReader(new FileReader(getFilesDir() + "address4.txt"));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                String result = "";
                String dummy = "";
                String str_input = null;
                int line_num = 0;
                while (true) {
                    line_num = line_num +1;
                    try {
                        if ((str_input = br.readLine()) != null){
                            if (line_num==(index3*5+3)){
                                dummy = dummy + str_input;
                                result = result +
                                        "    \"picture\":\""+picture+"\",\n" +
                                        "    \"name\":\""+name.getText().toString()+"\",\n" +
                                        "    \"phone\":\""+phone.getText().toString()+"\"\n" ;
 ;
                            }
                            else if((line_num>(index3*5+3)) && line_num <=(index3*5+5)){
                                dummy = dummy + str_input;
                            }
                            else{
                                 result += str_input + "\n";
                            }
                        }
                        else{
                            break;
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                try {

                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                BufferedWriter bw = null;
                try {
                    bw = new BufferedWriter(new FileWriter(getFilesDir() + "address4.txt", false));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    bw.write(result);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    bw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Intent intent=new Intent(Address_revise.this,MainActivity.class);
                startActivity(intent);
                finish();
            }

        });
    }
}