package com.example.myapplication;

public class PostItem {
    final String user;
    final String title;
    final String date;
    final String pk;
    final String text;

    public PostItem(String user, String title, String date, String pk, String text) {
        this.user = user;
        this.title = title;
        this.date = date;
        this.pk = pk;
        this.text = text;
    }
}
