package com.a5corp.weather.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class WeatherFort implements Serializable{
    private City city = new City();
    private int cod;
    private List<WeatherList> list;

    public int getCod() {
        return cod;
    }

    public City getCity() {
        return city;
    }

    public class City {
        private String name;
        private WeatherInfo.Coordinates coord;
        private String country;

        public WeatherInfo.Coordinates getCoord() {
            return coord;
        }

        public String getName() {
            return name;
        }

        public String getCountry() {
            return country;
        }
    }

    public List<WeatherList> getList() {
        return list;
    }

    public class WeatherList implements Serializable{
        private long dt;
        private Temp temp;
        private double pressure;
        private double humidity;
        private double speed;
        private double deg;
        private double rain;
        private double snow;
        private List<WeatherInfo.Weather> weather = new ArrayList<>();

        public List<WeatherInfo.Weather> getWeather() {
            return weather;
        }

        public long getDt() {
            return dt;
        }

        public Temp getTemp() {
            return temp;
        }

        public double getSpeed() {
            return speed;
        }

        public double getPressure() {
            return pressure;
        }

        public double getHumidity() {
            return humidity;
        }

        public double getDeg() {
            return deg;
        }

        public double getRain() {
            return rain;
        }

        public double getSnow() {
            return snow;
        }
    }

    public class Temp {
        private double day;
        private double min;
        private double max;
        private double night;
        private double eve;
        private double morn;

        public int getMax() {
            return (int) max;
        }

        public int getMin() {
            return (int) min;
        }

        public int getDay() {
            return (int) day;
        }

        public int getNight() {
            return (int) night;
        }

        public int getMorn() {
            return (int) morn;
        }

        public int getEve() {
            return (int) eve;
        }
    }
}