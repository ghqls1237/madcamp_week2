package com.example.myapplication;

public class wsb_beach {
    private String location;
    private String wave_height;
    private String comment;

    public wsb_beach(String location, String wave_height, String comment){
        this.location = location;
        this.wave_height = wave_height;
        this.comment = comment;
    }

    public String get_location(){
        return location;
    }

    public String getWave_height(){
        return wave_height;
    }

    public String get_comment(){return comment;}

}
