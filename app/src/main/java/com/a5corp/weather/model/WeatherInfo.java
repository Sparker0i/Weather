package com.a5corp.weather.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class WeatherInfo {
    private Coordinates coord = new Coordinates();
    private Main main = new Main();
    private String name;
    private Sys sys = new Sys();
    private Wind wind = new Wind();
    private long dt;
    private int cod;
    private List<Weather> weather = new ArrayList<Weather>();

    public void setName (String str) {
        name = str;
    }

    public String getName() {
        return name;
    }

    public void setDt(long lo) {
        dt = lo;
    }

    public long getDt() {
        return dt;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public long getCod() {
        return cod;
    }
}

class Main {
    private float temp;
    private float humidity;

    public float getTemp() {
        return temp;
    }

    public void setTemp(float temp) {
        this.temp = temp;
    }

    public float getHumidity() {
        return humidity;
    }

    public void setHumidity(float humidity) {
        this.humidity = humidity;
    }
}

class Weather {
    private int id;
    private String description;

    public void setDescription (String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setId (int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}

class Wind {
    private float speed;
    @SerializedName("deg")
    private float direction;

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public float getDirection() {
        return direction;
    }

    public void setDirection(float direction) {
        this.direction = direction;
    }
}

class Sys {
    private long sunrise;
    private long sunset;
    private String country;

    public long getSunrise() {
        return sunrise;
    }

    public void setSunrise(long sunrise) {
        this.sunrise = sunrise;
    }

    public long getSunset() {
        return sunset;
    }

    public void setSunset(long sunset) {
        this.sunset = sunset;
    }

    private String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}

class Coordinates {
    @SerializedName("lat")
    private double latitude;
    @SerializedName("lon")
    private double longitude;

    public void setLatitude (double latitude) {
        this.latitude = latitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLongitude (double longitude) {
        this.longitude = longitude;
    }

    public double getLongitude() {
        return longitude;
    }
}