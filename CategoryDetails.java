package com.example.abgomsale.iot;


import android.graphics.Color;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by mani8177 on 7/16/15.
 */
public class CategoryDetails {

    public static String[] cardHeading = {"Temperature","Ambient Light","Blind Status"};

    static Integer[] id_ = {0, 1, 2, 3, 4, 5, 6, 7,8};

    static ArrayList<Integer> myCardColors = new ArrayList<Integer>(Arrays.asList(
            Integer.valueOf(Color.parseColor("#F44336")), // Red 500
            Integer.valueOf(Color.parseColor("#E91E63")), // Pink 500
            Integer.valueOf(Color.parseColor("#9C27B0")), // Purple 500
            Integer.valueOf(Color.parseColor("#2196F3")), // Blue 500
            Integer.valueOf(Color.parseColor("#0277BD")), // Light Blue 800
            Integer.valueOf(Color.parseColor("#00838F")), // Cyan 800
            Integer.valueOf(Color.parseColor("#009688")), // Teal 500
            Integer.valueOf(Color.parseColor("#43A047")), // Green 600
            Integer.valueOf(Color.parseColor("#689F38")), // Light Green 700
            Integer.valueOf(Color.parseColor("#CDDC39")), // Lime 500
            Integer.valueOf(Color.parseColor("#FFC107")), // Amber 500
            Integer.valueOf(Color.parseColor("#EF6C00")), // Orange 800
            Integer.valueOf(Color.parseColor("#FF5722")), // Deep Orange 500
            Integer.valueOf(Color.parseColor("#607D8B")), // Blue Grey 500
            Integer.valueOf(Color.parseColor("#673AB7")), // Deep Purple 500
            Integer.valueOf(Color.parseColor("#3F51B5")), // Indigo 500
            Integer.valueOf(Color.parseColor("#757575")), // Grey 600
            Integer.valueOf(Color.parseColor("#795548")))); // Brown 500


static ArrayList<Integer> myTempColors = new ArrayList<Integer>(Arrays.asList(
        Integer.valueOf(Color.parseColor("#0D47A1")), // BLUE 900
        Integer.valueOf(Color.parseColor("#2196F3")), // BLUE 500
        Integer.valueOf(Color.parseColor("#689F38")), // GREEN 700
        Integer.valueOf(Color.parseColor("#FF6C00")), // ORANGE 800
        Integer.valueOf(Color.parseColor("#FF3D00")))); // RED A400


        static ArrayList<Integer> myAmbientColors = new ArrayList<Integer>(Arrays.asList(
                Integer.valueOf(Color.parseColor("#212121")), // Black 900
                Integer.valueOf(Color.parseColor("#757575")), // grey 500
                Integer.valueOf(Color.parseColor("#78909C")))); // Orange A400


        static String ambient ;
        static String temperature;
        static String blind;
}



