package com.a5corp.weather;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DetailFragment extends Fragment {

    Typeface weatherFont;
    View rootView;
    TextView cityField;
    TextView dateText , tempText, humidityText, dayText, nightText, speedText, pressureText, sunriseText, sunsetText;
    TextView weatherIcon, humidityIcon, dayIcon, nightIcon, speedIcon, pressureIcon, sunriseIcon, sunsetIcon;
    JSONObject obj;
    Intent intent;

    public DetailFragment() {
    }

    public void updateView() {
        try {
            obj = new JSONObject(intent.getStringExtra("jsonStr"));
            initIcons();
            setTexts();
            setLowerLayout();
            cityField.setText(intent.getStringExtra("city"));
        }
        catch (JSONException ex) {
            Log.e("Detail View" , "Cannot Find Details");
            getActivity().finish();
        }
    }

    private void setLowerLayout() throws JSONException{
        final int humidity = obj.getInt("humidity");
        humidityText.setText(humidity + "%" + "");
        humidityText.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Snackbar.make(view, "Humidity : " + humidity + "%", Snackbar.LENGTH_SHORT)
                                .show();
                    }
                }
        );
        final long speed = obj.getLong("speed");
        speedText.setText(speed + " km/h" + "");
        speedText.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Snackbar.make(view, "Speed : " + speed + " km/h", Snackbar.LENGTH_SHORT)
                                .show();
                    }
                }
        );
        final long pressure = obj.getLong("pressure");
        pressureText.setText(pressure + " hPa" + "");
        pressureText.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Snackbar.make(view, "Pressure : " + pressure + " hPa", Snackbar.LENGTH_SHORT)
                                .show();
                    }
                }
        );
    }

    private void setTexts() throws JSONException{
        Long date1 = obj.getLong("dt");
        Date expiry = new Date(Long.parseLong(Long.toString(date1)) * 1000);
        String date = new SimpleDateFormat("EE, dd" , Locale.US).format(expiry);
        dateText.setText(date);

        long dy = obj.getJSONObject("temp").getLong("day");
        int day = (int) dy;
        dayText.setText(day + getString(R.string.c) + "");
        dayText.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Snackbar.make(view, "Temperature at Daytime", Snackbar.LENGTH_SHORT)
                                .show();
                    }
                }
        );

        long nt = obj.getJSONObject("temp").getLong("night");
        int night = (int) nt;
        nightText.setText(night + getString(R.string.c) + "");
        nightText.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Snackbar.make(view, "Temperature at Night", Snackbar.LENGTH_SHORT)
                                .show();
                    }
                }
        );

        SpannableString ss1=  new SpannableString(
                + obj.getJSONObject("temp").getLong("max") + "°" + "         "
                        + obj.getJSONObject("temp").getLong("min") + "°" + "\n");
        ss1.setSpan(new RelativeSizeSpan(1.4f) , 0 , 3 , 0);
        tempText.setText(ss1);

        long sr = intent.getLongExtra("sunrise" , 0);
        long ss = intent.getLongExtra("sunset" , 0);
        final String d1 = new java.text.SimpleDateFormat("hh:mm a" , Locale.US).format(new Date(sr * 1000));
        final String d2 = new java.text.SimpleDateFormat("hh:mm a" , Locale.US).format(new Date(ss * 1000));
        sunriseText.setText(d1);
        sunriseText.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Snackbar.make(view, "Sunrise Time : " + d1, Snackbar.LENGTH_SHORT)
                                .show();
                    }
                }
        );
        sunsetText.setText(d2);
        sunsetText.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Snackbar.make(view, "Sunset Time : " + d2, Snackbar.LENGTH_SHORT)
                                .show();
                    }
                }
        );
    }

    private void initIcons() throws JSONException{
        setWeatherIcon();
        humidityIcon.setText(getActivity().getString(R.string.humidity_icon));
        nightIcon.setText(getActivity().getString(R.string.night_icon));
        dayIcon.setText(getActivity().getString(R.string.day_icon));
        speedIcon.setText(getActivity().getString(R.string.speed_icon));
        pressureIcon.setText(getActivity().getString(R.string.pressure_icon));
        sunriseIcon.setText(getActivity().getString(R.string.sunrise_icon));
        sunsetIcon.setText(getActivity().getString(R.string.sunset_icon));
    }

    private void setWeatherIcon() throws JSONException {
        int id = obj.getJSONArray("weather").getJSONObject(0).getInt("id");
        String icon = "";
        switch(id) {
            case 501 :
            case 521 :
            case 500 : icon = getActivity().getString(R.string.weather_drizzle);
                break;
            case 502 :
            case 503 :
            case 504 : icon = getActivity().getString(R.string.weather_rainy);
                break;
            case 511 : icon = getActivity().getString(R.string.weather_rain_wind);
                break;
            case 301 :
            case 310 :
            case 311 :
            case 300 :
            case 520 : icon = getActivity().getString(R.string.weather_shower_rain);
                break;
            case 522 :
            case 531 :
            case 200 :
            case 201 :
            case 202 :
            case 210 :
            case 211 :
            case 212 :
            case 221 :
            case 230 :
            case 231 :
            case 232 : icon = getActivity().getString(R.string.weather_thunder);
                break;
            case 302 :
            case 314 :
            case 321 :
            case 312 : icon = getActivity().getString(R.string.weather_heavy_drizzle);
                break;
            case 313 : icon = getActivity().getString(R.string.weather_rain_drizzle);
                break;
            case 612 :
            case 602 : icon = getActivity().getString(R.string.weather_heavy_snow);
                break;
            case 611 : icon = getActivity().getString(R.string.weather_sleet);
                break;
            case 903 :
            case 600 :
            case 601 :
            case 615 :
            case 616 :
            case 620 :
            case 621 :
            case 622 : icon = getActivity().getString(R.string.weather_snowy);
                break;
            case 701 :
            case 702 :
            case 721 : icon = getActivity().getString(R.string.weather_smoke);
                break;
            case 751 :
            case 761 :
            case 731 : icon = getActivity().getString(R.string.weather_dust);
                break;
            case 741 : icon = getActivity().getString(R.string.weather_foggy);
                break;
            case 762 : icon = getActivity().getString(R.string.weather_volcano);
                break;
            case 771 :
            case 900 :
            case 781 : icon = getActivity().getString(R.string.weather_tornado);
                break;
            case 904 :
            case 800 : icon = getActivity().getString(R.string.weather_sunny);
                break;
            case 801 :
            case 802 :
            case 803 :
            case 804 : icon = getActivity().getString(R.string.weather_cloudy);
                break;
            case 901 : icon = getActivity().getString(R.string.weather_storm);
                break;
            case 902 : icon = getActivity().getString(R.string.weather_hurricane);
                break;
        }
        weatherIcon.setText(icon);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        weatherFont = Typeface.createFromAsset(getActivity().getAssets(), "fonts/newweather.ttf");
        intent = getActivity().getIntent();
        rootView = inflater.inflate(R.layout.fragment_detail, container, false);
        cityField = (TextView) rootView.findViewById(R.id.city_field);
        dateText = (TextView) rootView.findViewById(R.id.date_text);

        weatherIcon = (TextView) rootView.findViewById(R.id.weather_icon);
        weatherIcon.setTypeface(weatherFont);
        tempText = (TextView) rootView.findViewById(R.id.temp_text);

        humidityIcon = (TextView) rootView.findViewById(R.id.humidity_icon);
        humidityIcon.setTypeface(weatherFont);
        humidityText = (TextView) rootView.findViewById(R.id.humidity_text);
        speedIcon = (TextView) rootView.findViewById(R.id.speed_icon);
        speedIcon.setTypeface(weatherFont);
        speedText = (TextView) rootView.findViewById(R.id.speed_text);
        pressureIcon = (TextView) rootView.findViewById(R.id.pressure_icon);
        pressureIcon.setTypeface(weatherFont);
        pressureText = (TextView) rootView.findViewById(R.id.pressure_text);

        sunriseIcon = (TextView) rootView.findViewById(R.id.sunrise_icon);
        sunriseIcon.setTypeface(weatherFont);
        sunsetIcon = (TextView) rootView.findViewById(R.id.sunset_icon);
        sunsetIcon.setTypeface(weatherFont);
        sunriseText = (TextView) rootView.findViewById(R.id.sunrise_text);
        sunsetText = (TextView) rootView.findViewById(R.id.sunset_text);

        dayIcon = (TextView) rootView.findViewById(R.id.day_icon);
        dayIcon.setTypeface(weatherFont);
        dayText = (TextView) rootView.findViewById(R.id.day_text);
        nightIcon = (TextView) rootView.findViewById(R.id.night_icon);
        nightIcon.setTypeface(weatherFont);
        nightText = (TextView) rootView.findViewById(R.id.night_text);
        updateView();
        return rootView;
    }
}
