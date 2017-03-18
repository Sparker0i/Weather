package com.a5corp.weather.fragment;

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

import com.a5corp.weather.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DetailFragment extends Fragment {

    Typeface weatherFont;
    View rootView;
    TextView cityField;
    TextView dateText , tempText, humidityText, dayText, nightText, speedText, pressureText;
    TextView weatherIcon, humidityIcon, dayIcon, nightIcon, speedIcon, pressureIcon;
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
            cityField.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Snackbar.make(view , intent.getStringExtra("city") , Snackbar.LENGTH_SHORT).show();
                }
            });
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
        humidityIcon.setOnClickListener(
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
        speedIcon.setOnClickListener(
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
        pressureIcon.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Snackbar.make(view, "Pressure : " + pressure + " hPa", Snackbar.LENGTH_SHORT)
                                .show();
                    }
                }
        );
        try {
            String cond = obj.getJSONArray("weather").getJSONObject(0).getString("description");
            String[] strArray = cond.split(" ");
            final StringBuilder builder = new StringBuilder();
            for (String s : strArray) {
                String cap = s.substring(0, 1).toUpperCase() + s.substring(1);
                builder.append(cap.concat(" "));
            }
            Long date1 = obj.getLong("dt");
            Date expiry = new Date(Long.parseLong(Long.toString(date1)) * 1000);
            final String date = new SimpleDateFormat("EE, dd" , Locale.US).format(expiry);
            weatherIcon.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Snackbar.make(view, "Hey there, expect " + builder.toString() + "on " + date, Snackbar.LENGTH_SHORT)
                                    .show();
                        }
                    }
            );
        }
        catch (JSONException ex) {
            //HW
        }
    }

    private void setTexts() throws JSONException{
        Long date1 = obj.getLong("dt");
        Date expiry = new Date(Long.parseLong(Long.toString(date1)) * 1000);
        String date = new SimpleDateFormat("EE, dd" , Locale.US).format(expiry);
        dateText.setText(date);

        long dy = obj.getJSONObject("temp").getLong("day");
        final int day = (int) dy;
        dayText.setText(day + getString(R.string.c) + "");
        dayText.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Snackbar.make(view, "Temperature at Daytime : " + day + getString(R.string.c), Snackbar.LENGTH_SHORT)
                                .show();
                    }
                }
        );
        dayIcon.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Snackbar.make(view, "Temperature at Daytime : " + day + getString(R.string.c), Snackbar.LENGTH_SHORT)
                                .show();
                    }
                }
        );

        long nt = obj.getJSONObject("temp").getLong("night");
        final int night = (int) nt;
        nightText.setText(night + getString(R.string.c) + "");
        nightText.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Snackbar.make(view, "Temperature at Night : " + night + getString(R.string.c), Snackbar.LENGTH_SHORT)
                                .show();
                    }
                }
        );
        nightIcon.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Snackbar.make(view, "Temperature at Night : " + night + getString(R.string.c), Snackbar.LENGTH_SHORT)
                                .show();
                    }
                }
        );

        SpannableString ss1=  new SpannableString(
                + obj.getJSONObject("temp").getLong("max") + "°" + "         "
                        + obj.getJSONObject("temp").getLong("min") + "°" + "\n");
        ss1.setSpan(new RelativeSizeSpan(1.4f) , 0 , 3 , 0);
        tempText.setText(ss1);
    }

    private void initIcons() throws JSONException{
        setWeatherIcon();
        humidityIcon.setText(getActivity().getString(R.string.humidity_icon));
        nightIcon.setText(getActivity().getString(R.string.night_icon));
        dayIcon.setText(getActivity().getString(R.string.day_icon));
        speedIcon.setText(getActivity().getString(R.string.speed_icon));
        pressureIcon.setText(getActivity().getString(R.string.pressure_icon));
    }

    private boolean checkDay() {
        Calendar c = Calendar.getInstance();
        int hours = c.get(Calendar.HOUR_OF_DAY);

        return !(hours >= 18 || hours <= 6);
    }

    private void setWeatherIcon() {
        int id = 0;
        try {
            id = obj.getJSONArray("weather").getJSONObject(0).getInt("id");
        }
        catch (JSONException ex) {
            //HW
        }
        String icon = "";
        if (checkDay())
        switch(id) {
            case 501 : icon = getActivity().getString(R.string.day_drizzle);
                break;
            case 500 : icon = getActivity().getString(R.string.day_drizzle);
                break;
            case 502 : icon = getActivity().getString(R.string.day_rainy);
                break;
            case 503 : icon = getActivity().getString(R.string.day_rainy);
                break;
            case 504 : icon = getActivity().getString(R.string.day_rainy);
                break;
            case 511 : icon = getActivity().getString(R.string.day_rain_wind);
                break;
            case 520 : icon = getActivity().getString(R.string.day_rain_drizzle);
                break;
            case 521 : icon = getActivity().getString(R.string.day_drizzle);
                break;
            case 522 : icon = getActivity().getString(R.string.day_thunder);
                break;
            case 531 : icon = getActivity().getString(R.string.day_thunder);
                break;
            case 200 : icon = getActivity().getString(R.string.day_thunder);
                break;
            case 201 : icon = getActivity().getString(R.string.day_thunder);
                break;
            case 202 : icon = getActivity().getString(R.string.day_thunder);
                break;
            case 210 : icon = getActivity().getString(R.string.day_thunder);
                break;
            case 211 : icon = getActivity().getString(R.string.day_thunder);
                break;
            case 212 : icon = getActivity().getString(R.string.day_thunder);
                break;
            case 221 : icon = getActivity().getString(R.string.day_thunder);
                break;
            case 230 : icon = getActivity().getString(R.string.day_thunder);
                break;
            case 231 : icon = getActivity().getString(R.string.day_thunder);
                break;
            case 232 : icon = getActivity().getString(R.string.day_thunder);
                break;
            case 300 : icon = getActivity().getString(R.string.day_rain_drizzle);
                break;
            case 301 : icon = getActivity().getString(R.string.day_rain_drizzle);
                break;
            case 302 : icon = getActivity().getString(R.string.day_heavy_drizzle);
                break;
            case 310 : icon = getActivity().getString(R.string.day_rain_drizzle);
                break;
            case 311 : icon = getActivity().getString(R.string.day_rain_drizzle);
                break;
            case 312 : icon = getActivity().getString(R.string.day_heavy_drizzle);
                break;
            case 313 : icon = getActivity().getString(R.string.day_rain_drizzle);
                break;
            case 314 : icon = getActivity().getString(R.string.day_heavy_drizzle);
                break;
            case 321 : icon = getActivity().getString(R.string.day_heavy_drizzle);
                break;
            case 600 : icon = getActivity().getString(R.string.day_snowy);
                break;
            case 601 : icon = getActivity().getString(R.string.day_snowy);
                break;
            case 602 : icon = getActivity().getString(R.string.snow);
                break;
            case 611 : icon = getActivity().getString(R.string.sleet);
                break;
            case 612 : icon = getActivity().getString(R.string.day_snowy);
                break;
            case 903 :
            case 615 : icon = getActivity().getString(R.string.day_snowy);
                break;
            case 616 : icon = getActivity().getString(R.string.day_snowy);
                break;
            case 620 : icon = getActivity().getString(R.string.day_snowy);
                break;
            case 621 : icon = getActivity().getString(R.string.day_snowy);
                break;
            case 622 : icon = getActivity().getString(R.string.day_snowy);
                break;
            case 701 :
            case 702 :
            case 721 : icon = getActivity().getString(R.string.smoke);
                break;
            case 751 :
            case 761 :
            case 731 : icon = getActivity().getString(R.string.dust);
                break;
            case 741 : icon = getActivity().getString(R.string.fog);
                break;
            case 762 : icon = getActivity().getString(R.string.volcano);
                break;
            case 771 :
            case 900 :
            case 781 : icon = getActivity().getString(R.string.tornado);
                break;
            case 904 : icon = getActivity().getString(R.string.day_clear);
                break;
            case 800 : icon = getActivity().getString(R.string.day_clear);
                break;
            case 801 : icon = getActivity().getString(R.string.day_cloudy);
                break;
            case 802 : icon = getActivity().getString(R.string.day_cloudy);
                break;
            case 803 : icon = getActivity().getString(R.string.day_cloudy);
                break;
            case 804 : icon = getActivity().getString(R.string.day_cloudy);
                break;
            case 901 : icon = getActivity().getString(R.string.storm_showers);
                break;
            case 902 : icon = getActivity().getString(R.string.hurricane);
                break;
        }
        else
            switch(id) {
                case 501 : icon = getActivity().getString(R.string.night_drizzle);
                    break;
                case 500 : icon = getActivity().getString(R.string.night_drizzle);
                    break;
                case 502 : icon = getActivity().getString(R.string.night_rainy);
                    break;
                case 503 : icon = getActivity().getString(R.string.night_rainy);
                    break;
                case 504 : icon = getActivity().getString(R.string.night_rainy);
                    break;
                case 511 : icon = getActivity().getString(R.string.night_rain_wind);
                    break;
                case 520 : icon = getActivity().getString(R.string.night_rain_drizzle);
                    break;
                case 521 : icon = getActivity().getString(R.string.night_drizzle);
                    break;
                case 522 : icon = getActivity().getString(R.string.night_thunder);
                    break;
                case 531 : icon = getActivity().getString(R.string.night_thunder);
                    break;
                case 200 : icon = getActivity().getString(R.string.night_thunder);
                    break;
                case 201 : icon = getActivity().getString(R.string.night_thunder);
                    break;
                case 202 : icon = getActivity().getString(R.string.night_thunder);
                    break;
                case 210 : icon = getActivity().getString(R.string.night_thunder);
                    break;
                case 211 : icon = getActivity().getString(R.string.night_thunder);
                    break;
                case 212 : icon = getActivity().getString(R.string.night_thunder);
                    break;
                case 221 : icon = getActivity().getString(R.string.night_thunder);
                    break;
                case 230 : icon = getActivity().getString(R.string.night_thunder);
                    break;
                case 231 : icon = getActivity().getString(R.string.night_thunder);
                    break;
                case 232 : icon = getActivity().getString(R.string.night_thunder);
                    break;
                case 300 : icon = getActivity().getString(R.string.night_rain_drizzle);
                    break;
                case 301 : icon = getActivity().getString(R.string.night_rain_drizzle);
                    break;
                case 302 : icon = getActivity().getString(R.string.night_heavy_drizzle);
                    break;
                case 310 : icon = getActivity().getString(R.string.night_rain_drizzle);
                    break;
                case 311 : icon = getActivity().getString(R.string.night_rain_drizzle);
                    break;
                case 312 : icon = getActivity().getString(R.string.night_heavy_drizzle);
                    break;
                case 313 : icon = getActivity().getString(R.string.night_rain_drizzle);
                    break;
                case 314 : icon = getActivity().getString(R.string.night_heavy_drizzle);
                    break;
                case 321 : icon = getActivity().getString(R.string.night_heavy_drizzle);
                    break;
                case 600 : icon = getActivity().getString(R.string.night_snowy);
                    break;
                case 601 : icon = getActivity().getString(R.string.night_snowy);
                    break;
                case 602 : icon = getActivity().getString(R.string.snow);
                    break;
                case 611 : icon = getActivity().getString(R.string.sleet);
                    break;
                case 612 : icon = getActivity().getString(R.string.night_snowy);
                    break;
                case 903 :
                case 615 : icon = getActivity().getString(R.string.night_snowy);
                    break;
                case 616 : icon = getActivity().getString(R.string.night_snowy);
                    break;
                case 620 : icon = getActivity().getString(R.string.night_snowy);
                    break;
                case 621 : icon = getActivity().getString(R.string.night_snowy);
                    break;
                case 622 : icon = getActivity().getString(R.string.night_snowy);
                    break;
                case 701 :
                case 702 :
                case 721 : icon = getActivity().getString(R.string.smoke);
                    break;
                case 751 :
                case 761 :
                case 731 : icon = getActivity().getString(R.string.dust);
                    break;
                case 741 : icon = getActivity().getString(R.string.fog);
                    break;
                case 762 : icon = getActivity().getString(R.string.volcano);
                    break;
                case 771 :
                case 900 :
                case 781 : icon = getActivity().getString(R.string.tornado);
                    break;
                case 904 : icon = getActivity().getString(R.string.night_clear);
                    break;
                case 800 : icon = getActivity().getString(R.string.night_clear);
                    break;
                case 801 : icon = getActivity().getString(R.string.night_cloudy);
                    break;
                case 802 : icon = getActivity().getString(R.string.night_cloudy);
                    break;
                case 803 : icon = getActivity().getString(R.string.night_cloudy);
                    break;
                case 804 : icon = getActivity().getString(R.string.night_cloudy);
                    break;
                case 901 : icon = getActivity().getString(R.string.storm_showers);
                    break;
                case 902 : icon = getActivity().getString(R.string.hurricane);
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
