package com.example.myapplication;

public class NotiItem {
    String post_pk;
    String date;
    String user;
    String pkk; //sea
    String beach; //beach
    String beach_title; //beach_name

    NotiItem(String text, String post_pk, String date, String user, String pkk, String beach, String beach_title){
        this.post_pk = post_pk;
        this.date = date;
        this.user = user;
        this.pkk = pkk;
        this.beach = beach;
        this.beach_title = beach_title;
    }
}

