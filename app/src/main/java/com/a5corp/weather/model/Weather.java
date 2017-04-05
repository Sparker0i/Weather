package com.a5corp.weather.model;

public class Weather {

    public class Coordinates {
        private double latitude;
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

    public class Sys {
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

    public class CurrentConditions {
        private float pressure;
        private int humidity;

        public float getPressure() {
            return pressure;
        }

        public void setPressure(float pressure) {
            this.pressure = pressure;
        }

        public int getHumidity() {
            return humidity;
        }

        public void setHumidity(int humidity) {
            this.humidity = humidity;
        }
    }

    public class CurrentWeather {
        private String description;

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description.substring(0, 1).toUpperCase() + description.substring(1);
        }
    }

    public class Wind {
        private float speed;
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

    public class Temperature {
        private float temp;

        public float getTemp() {
            return temp;
        }

        public void setTemp(float temp) {
            this.temp = temp;
        }
    }
}
