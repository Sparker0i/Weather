package com.a5corp.weather.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WeatherInfo {
    public Coordinates coord;
    public List<Weather> weather;
    public Main main;
    public String name;
    public Sys sys;
    public Wind wind;
    public long dt;

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