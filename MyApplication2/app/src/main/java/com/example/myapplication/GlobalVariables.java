package com.example.myapplication;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GlobalVariables {
    final static int IMAGEMAX = 21;

    //public static Integer[] phone_image = {R.drawable.human,R.drawable.human,R.drawable.human,R.drawable.human,R.drawable.human,R.drawable.human,R.drawable.human,R.drawable.human,R.drawable.human,R.drawable.human,R.drawable.human,R.drawable.human,R.drawable.human,R.drawable.human,R.drawable.human,R.drawable.human,R.drawable.human,R.drawable.human,R.drawable.human,R.drawable.human,R.drawable.human,R.drawable.human}
    public static Integer[] images = {R.drawable.a1, R.drawable.a2, R.drawable.a3, R.drawable.a4, R.drawable.a5, R.drawable.a6, R.drawable.a7, R.drawable.a8, R.drawable.a9, R.drawable.a10, R.drawable.a11, R.drawable.a12, R.drawable.a13, R.drawable.a14, R.drawable.a15, R.drawable.a16, R.drawable.a17, R.drawable.a18, R.drawable.a19, R.drawable.a20, R.drawable.a21};
    public static Integer[] thumbButton = {R.id.thumb_button_1, R.id.thumb_button_2, R.id.thumb_button_3, R.id.thumb_button_4, R.id.thumb_button_5, R.id.thumb_button_6, R.id.thumb_button_7, R.id.thumb_button_8, R.id.thumb_button_9, R.id.thumb_button_10, R.id.thumb_button_11, R.id.thumb_button_12, R.id.thumb_button_13, R.id.thumb_button_14, R.id.thumb_button_15, R.id.thumb_button_16, R.id.thumb_button_17, R.id.thumb_button_18, R.id.thumb_button_19, R.id.thumb_button_20, R.id.thumb_button_21};
    public static ArrayList<Integer> bookMarks = new ArrayList<Integer>();
    public static Integer[] male = {R.drawable.b1,R.drawable.b2,R.drawable.b3,R.drawable.b4,R.drawable.b5,R.drawable.b6,R.drawable.b7,R.drawable.b8,R.drawable.b9,R.drawable.b10,R.drawable.b11,R.drawable.b12,R.drawable.b13,R.drawable.b14,R.drawable.b15,R.drawable.b16};
    public static Integer[] female = {R.drawable.c1,R.drawable.c2,R.drawable.c3,R.drawable.c4,R.drawable.c5,R.drawable.c6,R.drawable.c7,R.drawable.c8,R.drawable.c9,R.drawable.c10,R.drawable.c11,R.drawable.c12,R.drawable.c13,R.drawable.c14,R.drawable.c15,R.drawable.c16};

    public static int image_pick = 0;
    public static int image_change = 0;



    private static GlobalVariables instance = null;
    public static synchronized GlobalVariables getInstance() {
        if(null == instance) {
            instance = new GlobalVariables();
        }
        return instance;
    }

    public static void addBookMarks(int ImageResId) {
        if(!bookMarks.contains(ImageResId)) { bookMarks.add(ImageResId); }
        System.out.println(bookMarks.toString());
    }

    public static void removeBookMarks(int ImageResId) {
        if(bookMarks.contains(ImageResId)) { bookMarks.remove( (Integer) ImageResId);}
        System.out.println(bookMarks.toString());
    }

    public static boolean inBookMarks(int ImageResId) {
        if(bookMarks.contains(ImageResId)) { return true;}
        return false;
    }

    public static Integer[] shuffle(Integer[] image) {
        Integer[] result = new Integer[image.length];
        int len = image.length;
        Random random = new Random();

        for (int i = 0; i < len; i++) {
            int randNum = (int)(random.nextInt(image.length-i));
            result[i] = image[randNum];
            image = removeItem(image,randNum);
        }
        return result;
    }

    public static Integer[] removeItem(Integer[] array, int index){
        Integer[] result = new Integer[array.length];

        for (int i=0; i<array.length; i++){
            if (i <index){
                result[i] = array[i];
            }
            else if (i > index){
                result[i-1] = array[i];
            }
        }
        return result;
    }
}
