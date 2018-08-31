package com.a5corp.weather.model;

public class AboutModel {
    public static final int ABOUT_1 = 1;
    public static final int ABOUT_2 = 2;
    public static final int ABOUT_3 = 3;
    public static final int ABOUT_4 = 4;
    public static final int ABOUT_5 = 5;

    public int type;
    public String text;

    public AboutModel(int type) {
        this.type = type;
    }
}
