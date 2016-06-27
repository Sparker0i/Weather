package com.a5corp.weather;

import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


import org.json.JSONObject;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

public class WeatherFragment extends Fragment {
    Typeface weatherFont;

    TextView cityField;
    TextView updatedField;
    TextView detailsField2 , detailsField1 , detailsField3;
    TextView currentTemperatureField;
    TextView weatherIcon2 , weatherIcon1 , weatherIcon3;

    Handler handler;
    private void updateWeatherData(final String city){
        new Thread(){
            public void run(){
                final JSONObject json = RemoteFetch.getJSON(getActivity(), city);
                if(json == null){
                    handler.post(new Runnable(){
                        public void run(){
                            Toast.makeText(getActivity(),
                                    getActivity().getString(R.string.place_not_found),
                                    Toast.LENGTH_LONG).show();
                        }
                    });
                } else {
                    handler.post(new Runnable(){
                        public void run(){
                            renderWeather(json);
                        }
                    });
                }
            }
        }.start();
    }

    /*private void setWeatherIcon(int actualId, long sunrise, long sunset){                   //CHANGE THIS SHIT ASAP
        int id = actualId / 100;
        String icon = "";
        if(actualId == 800){
            long currentTime = new Date().getTime();
            if(currentTime>=sunrise && currentTime<sunset) {
                icon = getActivity().getString(R.string.weather_sunny);
            } else {
                icon = getActivity().getString(R.string.weather_clear_night);
            }
        } else {
            switch(id) {
                case 2 : icon = getActivity().getString(R.string.weather_thunder);
                    break;
                case 3 : icon = getActivity().getString(R.string.weather_drizzle);
                    break;
                case 7 : icon = getActivity().getString(R.string.weather_foggy);
                    break;
                case 8 : icon = getActivity().getString(R.string.weather_cloudy);
                    break;
                case 6 : icon = getActivity().getString(R.string.weather_snowy);
                    break;
                case 5 : icon = getActivity().getString(R.string.weather_rainy);
                    break;
            }
        }
        weatherIcon2.setText(icon);
    }*/

    private void setWeatherIcon2(int id){
        String icon = "";
            switch(id) {
                case 501 : icon = getActivity().getString(R.string.weather_drizzle);
                    break;
                case 500 : icon = getActivity().getString(R.string.weather_drizzle);
                    break;
                case 502 : icon = getActivity().getString(R.string.weather_rainy);
                    break;
                case 503 : icon = getActivity().getString(R.string.weather_rainy);
                    break;
                case 504 : icon = getActivity().getString(R.string.weather_rainy);
                    break;
                case 511 : icon = getActivity().getString(R.string.weather_rain_wind);
                    break;
                case 520 : icon = getActivity().getString(R.string.weather_shower_rain);
                    break;
                case 521 : icon = getActivity().getString(R.string.weather_drizzle);
                    break;
                case 522 : icon = getActivity().getString(R.string.weather_thunder);
                    break;
                case 531 : icon = getActivity().getString(R.string.weather_thunder);
                    break;
                case 200 : icon = getActivity().getString(R.string.weather_thunder);
                    break;
                case 201 : icon = getActivity().getString(R.string.weather_thunder);
                    break;
                case 202 : icon = getActivity().getString(R.string.weather_thunder);
                    break;
                case 210 : icon = getActivity().getString(R.string.weather_thunder);
                    break;
                case 211 : icon = getActivity().getString(R.string.weather_thunder);
                    break;
                case 212 : icon = getActivity().getString(R.string.weather_thunder);
                    break;
                case 221 : icon = getActivity().getString(R.string.weather_thunder);
                    break;
                case 230 : icon = getActivity().getString(R.string.weather_thunder);
                    break;
                case 231 : icon = getActivity().getString(R.string.weather_thunder);
                    break;
                case 232 : icon = getActivity().getString(R.string.weather_thunder);
                    break;
                case 300 : icon = getActivity().getString(R.string.weather_shower_rain);
                    break;
                case 301 : icon = getActivity().getString(R.string.weather_shower_rain);
                    break;
                case 302 : icon = getActivity().getString(R.string.weather_heavy_drizzle);
                    break;
                case 310 : icon = getActivity().getString(R.string.weather_shower_rain);
                    break;
                case 311 : icon = getActivity().getString(R.string.weather_shower_rain);
                    break;
                case 312 : icon = getActivity().getString(R.string.weather_heavy_drizzle);
                    break;
                case 313 : icon = getActivity().getString(R.string.weather_rain_drizzle);
                    break;
                case 314 : icon = getActivity().getString(R.string.weather_heavy_drizzle);
                    break;
                case 321 : icon = getActivity().getString(R.string.weather_heavy_drizzle);
                    break;
                case 600 : icon = getActivity().getString(R.string.weather_snowy);
                    break;
                case 601 : icon = getActivity().getString(R.string.weather_snowy);
                    break;
                case 602 : icon = getActivity().getString(R.string.weather_heavy_snow);
                    break;
                case 611 : icon = getActivity().getString(R.string.weather_sleet);
                    break;
                case 612 : icon = getActivity().getString(R.string.weather_heavy_snow);
                    break;
                case 615 : icon = getActivity().getString(R.string.weather_snowy);
                    break;
                case 616 : icon = getActivity().getString(R.string.weather_snowy);
                    break;
                case 620 : icon = getActivity().getString(R.string.weather_snowy);
                    break;
                case 621 : icon = getActivity().getString(R.string.weather_snowy);
                    break;
                case 622 : icon = getActivity().getString(R.string.weather_snowy);
                    break;
            }
        weatherIcon2.setText(icon);
    }

    private void setWeatherIcon1(int id){
        String icon = "";
        switch(id) {
            case 501 : icon = getActivity().getString(R.string.weather_drizzle);
                break;
            case 500 : icon = getActivity().getString(R.string.weather_drizzle);
                break;
            case 502 : icon = getActivity().getString(R.string.weather_rainy);
                break;
            case 503 : icon = getActivity().getString(R.string.weather_rainy);
                break;
            case 504 : icon = getActivity().getString(R.string.weather_rainy);
                break;
            case 511 : icon = getActivity().getString(R.string.weather_rain_wind);
                break;
            case 520 : icon = getActivity().getString(R.string.weather_shower_rain);
                break;
            case 521 : icon = getActivity().getString(R.string.weather_drizzle);
                break;
            case 522 : icon = getActivity().getString(R.string.weather_thunder);
                break;
            case 531 : icon = getActivity().getString(R.string.weather_thunder);
                break;
            case 200 : icon = getActivity().getString(R.string.weather_thunder);
                break;
            case 201 : icon = getActivity().getString(R.string.weather_thunder);
                break;
            case 202 : icon = getActivity().getString(R.string.weather_thunder);
                break;
            case 210 : icon = getActivity().getString(R.string.weather_thunder);
                break;
            case 211 : icon = getActivity().getString(R.string.weather_thunder);
                break;
            case 212 : icon = getActivity().getString(R.string.weather_thunder);
                break;
            case 221 : icon = getActivity().getString(R.string.weather_thunder);
                break;
            case 230 : icon = getActivity().getString(R.string.weather_thunder);
                break;
            case 231 : icon = getActivity().getString(R.string.weather_thunder);
                break;
            case 232 : icon = getActivity().getString(R.string.weather_thunder);
                break;
            case 300 : icon = getActivity().getString(R.string.weather_shower_rain);
                break;
            case 301 : icon = getActivity().getString(R.string.weather_shower_rain);
                break;
            case 302 : icon = getActivity().getString(R.string.weather_heavy_drizzle);
                break;
            case 310 : icon = getActivity().getString(R.string.weather_shower_rain);
                break;
            case 311 : icon = getActivity().getString(R.string.weather_shower_rain);
                break;
            case 312 : icon = getActivity().getString(R.string.weather_heavy_drizzle);
                break;
            case 313 : icon = getActivity().getString(R.string.weather_rain_drizzle);
                break;
            case 314 : icon = getActivity().getString(R.string.weather_heavy_drizzle);
                break;
            case 321 : icon = getActivity().getString(R.string.weather_heavy_drizzle);
                break;
            case 600 : icon = getActivity().getString(R.string.weather_snowy);
                break;
            case 601 : icon = getActivity().getString(R.string.weather_snowy);
                break;
            case 602 : icon = getActivity().getString(R.string.weather_heavy_snow);
                break;
            case 611 : icon = getActivity().getString(R.string.weather_sleet);
                break;
            case 612 : icon = getActivity().getString(R.string.weather_heavy_snow);
                break;
            case 615 : icon = getActivity().getString(R.string.weather_snowy);
                break;
            case 616 : icon = getActivity().getString(R.string.weather_snowy);
                break;
            case 620 : icon = getActivity().getString(R.string.weather_snowy);
                break;
            case 621 : icon = getActivity().getString(R.string.weather_snowy);
                break;
            case 622 : icon = getActivity().getString(R.string.weather_snowy);
                break;
        }
        weatherIcon1.setText(icon);
    }

    private void setWeatherIcon3(int id){
        String icon = "";
        switch(id) {
            case 501 : icon = getActivity().getString(R.string.weather_drizzle);
                break;
            case 500 : icon = getActivity().getString(R.string.weather_drizzle);
                break;
            case 502 : icon = getActivity().getString(R.string.weather_rainy);
                break;
            case 503 : icon = getActivity().getString(R.string.weather_rainy);
                break;
            case 504 : icon = getActivity().getString(R.string.weather_rainy);
                break;
            case 511 : icon = getActivity().getString(R.string.weather_rain_wind);
                break;
            case 520 : icon = getActivity().getString(R.string.weather_shower_rain);
                break;
            case 521 : icon = getActivity().getString(R.string.weather_drizzle);
                break;
            case 522 : icon = getActivity().getString(R.string.weather_thunder);
                break;
            case 531 : icon = getActivity().getString(R.string.weather_thunder);
                break;
            case 200 : icon = getActivity().getString(R.string.weather_thunder);
                break;
            case 201 : icon = getActivity().getString(R.string.weather_thunder);
                break;
            case 202 : icon = getActivity().getString(R.string.weather_thunder);
                break;
            case 210 : icon = getActivity().getString(R.string.weather_thunder);
                break;
            case 211 : icon = getActivity().getString(R.string.weather_thunder);
                break;
            case 212 : icon = getActivity().getString(R.string.weather_thunder);
                break;
            case 221 : icon = getActivity().getString(R.string.weather_thunder);
                break;
            case 230 : icon = getActivity().getString(R.string.weather_thunder);
                break;
            case 231 : icon = getActivity().getString(R.string.weather_thunder);
                break;
            case 232 : icon = getActivity().getString(R.string.weather_thunder);
                break;
            case 300 : icon = getActivity().getString(R.string.weather_shower_rain);
                break;
            case 301 : icon = getActivity().getString(R.string.weather_shower_rain);
                break;
            case 302 : icon = getActivity().getString(R.string.weather_heavy_drizzle);
                break;
            case 310 : icon = getActivity().getString(R.string.weather_shower_rain);
                break;
            case 311 : icon = getActivity().getString(R.string.weather_shower_rain);
                break;
            case 312 : icon = getActivity().getString(R.string.weather_heavy_drizzle);
                break;
            case 313 : icon = getActivity().getString(R.string.weather_rain_drizzle);
                break;
            case 314 : icon = getActivity().getString(R.string.weather_heavy_drizzle);
                break;
            case 321 : icon = getActivity().getString(R.string.weather_heavy_drizzle);
                break;
            case 600 : icon = getActivity().getString(R.string.weather_snowy);
                break;
            case 601 : icon = getActivity().getString(R.string.weather_snowy);
                break;
            case 602 : icon = getActivity().getString(R.string.weather_heavy_snow);
                break;
            case 611 : icon = getActivity().getString(R.string.weather_sleet);
                break;
            case 612 : icon = getActivity().getString(R.string.weather_heavy_snow);
                break;
            case 615 : icon = getActivity().getString(R.string.weather_snowy);
                break;
            case 616 : icon = getActivity().getString(R.string.weather_snowy);
                break;
            case 620 : icon = getActivity().getString(R.string.weather_snowy);
                break;
            case 621 : icon = getActivity().getString(R.string.weather_snowy);
                break;
            case 622 : icon = getActivity().getString(R.string.weather_snowy);
                break;
        }
        weatherIcon3.setText(icon);
    }

    public void changeCity(String city){
        updateWeatherData(city);
    }

    private void renderWeather(JSONObject json){
        try {
            cityField.setText(json.getJSONObject("city").getString("name").toUpperCase(Locale.US) +
                    ", " +
                    json.getJSONObject("city").getString("country"));
            Log.i("Location" , "Location Received");

            /*JSONObject details = json.getJSONArray("weather").getJSONObject(0);
            JSONObject main = json.getJSONObject("main");*/

            JSONObject details1 = json.getJSONArray("list").getJSONObject(0);
            JSONObject details2 = json.getJSONArray("list").getJSONObject(1);
            JSONObject details3 = json.getJSONArray("list").getJSONObject(2);
            Log.i("Objects" , "JSON Objects Created");
            detailsField1.setText(
                    details1.getJSONArray("weather").getJSONObject(0).getString("description").toUpperCase(Locale.US) +
                            "\n" + "Humidity: " + details1.getString("humidity") + "%" +
                            "\n" + "Pressure: " + details1.getString("pressure") + " hPa");
            Log.i("Infor String 1","Details Done");
            detailsField2.setText(
                    details2.getJSONArray("weather").getJSONObject(0).getString("description").toUpperCase(Locale.US) +
                            "\n" + "Humidity: " + details2.getString("humidity") + "%" +
                            "\n" + "Pressure: " + details2.getString("pressure") + " hPa");
            Log.i("Infor String 2","Details Done");

            detailsField3.setText(
                    details3.getJSONArray("weather").getJSONObject(0).getString("description").toUpperCase(Locale.US) +
                            "\n" + "Humidity: " + details3.getString("humidity") + "%" +
                            "\n" + "Pressure: " + details3.getString("pressure") + " hPa");
            Log.i("Infor String 3","Details Done");


            //currentTemperatureField.setText(String.format("%.2f", main.getDouble("temp"))+ " ℃");

            /*DateFormat df = DateFormat.getDateTimeInstance();
            String updatedOn = df.format(new Date(json.getLong("dt")*1000));
            updatedField.setText("Last update: " + updatedOn);*/

            setWeatherIcon1(details1.getJSONArray("weather").getJSONObject(0).getInt("id"));
            setWeatherIcon2(details2.getJSONArray("weather").getJSONObject(0).getInt("id"));
            setWeatherIcon3(details3.getJSONArray("weather").getJSONObject(0).getInt("id"));


        }catch(Exception e){
            Log.e("SimpleWeather", "One or more fields not found in the JSON data");
        }
    }

    public WeatherFragment(){
        handler = new Handler();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_weather, container, false);
        cityField = (TextView)rootView.findViewById(R.id.city_field);
        updatedField = (TextView)rootView.findViewById(R.id.updated_field);
        detailsField2 = (TextView)rootView.findViewById(R.id.details_field2);
        currentTemperatureField = (TextView)rootView.findViewById(R.id.current_temperature_field);
        weatherIcon2 = (TextView)rootView.findViewById(R.id.weather_icon2);
        weatherIcon1 = (TextView)rootView.findViewById(R.id.weather_icon1);
        weatherIcon3 = (TextView)rootView.findViewById(R.id.weather_icon3);
        detailsField1 = (TextView)rootView.findViewById(R.id.details_field1);
        detailsField3 = (TextView)rootView.findViewById(R.id.details_field3);
        weatherIcon1.setTypeface(weatherFont);
        weatherIcon3.setTypeface(weatherFont);
        weatherIcon2.setTypeface(weatherFont);
        return rootView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        weatherFont = Typeface.createFromAsset(getActivity().getAssets(), "fonts/weather.ttf");
        updateWeatherData(new CityPreference(getActivity()).getCity());
    }
}