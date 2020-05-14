package com.androcrush.sqlite2;

import android.annotation.SuppressLint;

import java.util.ArrayList;


@SuppressWarnings("ALL")
@SuppressLint("Registered")
public class catlist {
     //static List<String> categories = new ArrayList<String>();
     static ArrayList<String> categories= new ArrayList<String>();



    public ArrayList<String> getCategories() {
        return categories;
    }

    public static void setCategories(String item) {
        categories.add(item);
    }
}
