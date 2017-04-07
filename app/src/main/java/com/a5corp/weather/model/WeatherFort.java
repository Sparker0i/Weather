package com.a5corp.weather.model;

import java.util.ArrayList;
import java.util.List;

public class WeatherFort {
    private City city = new City();
    private int cod;
    private List<WeatherList> list;

    public int getCod() {
        return cod;
    }
}

class City {
    private String name;
    private Coordinates coord = new Coordinates();
    private String country;
}

class WeatherList {
    private int dt;
    private Temp temp;
    private double pressure;
    private double humidity;
    private double speed;
    private double deg;
    private double rain;
    private List<Weather> weather = new ArrayList<Weather>();
}

class Temp {
    private double day;
    private double min;
    private double max;
    private double night;
    private double eve;
    private double morn;
}