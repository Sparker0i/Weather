package com.a5corp.weather.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("unused")
public class WeatherInfo implements Serializable{
    private Coordinates coord;
    private Main main;
    private String name;
    private Sys sys;
    private Wind wind;
    private long dt;
    private int cod;
    private List<Weather> weather;

    public String getName() {
        return name;
    }

    public long getDt() {
        return dt;
    }

    public long getCod() {
        return cod;
    }

    public Sys getSys() {
        return sys;
    }

    public Wind getWind() {
        return wind;
    }

    public Main getMain() {
        return main;
    }

    public List<Weather> getWeather() {
        return weather;
    }

    public class Main implements Serializable{
        private double temp;
        private double humidity;
        private double pressure;

        public double getTemp() {
            return temp;
        }

        public int getHumidity() {
            return (int) humidity;
        }

        public double getPressure() {
            return pressure;
        }
    }

    public static class Weather implements Serializable{
        private int id;
        private String description;

        public String getDescription() {
            return description;
        }

        public void setDescription(String desc) {
            this.description = desc;
        }

        public int getId() {
            return id;
        }
    }

    public class Wind implements Serializable{
        private float speed;
        @SerializedName("deg")
        private float direction;

        public float getSpeed() {
            return speed;
        }

        public int getDirection() {
            return (int) direction;
        }
    }

    public class Sys implements Serializable{
        private long sunrise;
        private long sunset;
        private String country;

        public long getSunrise() {
            return sunrise;
        }

        public long getSunset() {
            return sunset;
        }

        public String getCountry() {
            return country;
        }
    }

    public class Coordinates implements Serializable{
        @SerializedName("lat")
        private double latitude;
        @SerializedName("lon")
        private double longitude;

        public double getLatitude() {
            return latitude;
        }

        public double getLongitude() {
            return longitude;
        }
    }
}