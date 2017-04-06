package com.a5corp.weather.internet;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.a5corp.weather.R;
import com.a5corp.weather.preferences.Preferences;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class FetchWeatherOther extends AsyncTask<String , Void , JSONObject[]> {
    private static final String OPEN_WEATHER_MAP_DAILY_API = "http://api.openweathermap.org/data/2.5/weather?";
    private final String LOG_TAG = FetchWeatherOther.class.getSimpleName();

    private Context context;
    private final String QUERY_PARAM = "q";
    private final String FORMAT_PARAM = "mode";
    private final String FORMAT_VALUE = "json";
    private final String UNITS_PARAM = "units";
    private String UNITS_VALUE;
    private final String DAYS_PARAM = "cnt";
    private Uri builtDay;
    private Preferences preferences;

    public FetchWeatherOther(Context mContext) {
        context = mContext;
        preferences = new Preferences(context);
    }

    @Override
    public JSONObject[] doInBackground(String... params) {
        city(params);
        try {
            Log.d(LOG_TAG , "Execution");
            URL day = new URL(builtDay.toString());
            Log.i("day" , day.toString());
            Log.d(LOG_TAG , "URI Ready");
            HttpURLConnection connection0 = (HttpURLConnection)day.openConnection();
            connection0.addRequestProperty("x-api-key", context.getString(R.string.open_weather_maps_app_id));
            BufferedReader reader;
            StringBuilder json = new StringBuilder(1024);

            reader= new BufferedReader(new InputStreamReader(connection0.getInputStream()));
            String tmp;
            while((tmp = reader.readLine())!=null)
                json.append(tmp).append("\n");
            reader.close();

            JSONObject data = new JSONObject(json.toString());

            // This value will be 404 if the request was not successful
            if(data.getInt("cod") != 200){
                Log.e(LOG_TAG , "Execution Failed");
                return null;
            }

            JSONObject array[] = new JSONObject[2];
            array[0] = data;
            Log.d(LOG_TAG , "Array Ready");
            return array;
        }catch(JSONException e){
            Log.e(LOG_TAG , "Execution Failed JSON");
            return null;
        }
        catch(IOException e){
            Log.e(LOG_TAG , "Execution Failed IO");
            e.printStackTrace();
            return null;
        }
    }

    private void city(String... params) {
        UNITS_VALUE = preferences.getUnits();
        System.out.println(UNITS_VALUE);
        builtDay = Uri.parse(OPEN_WEATHER_MAP_DAILY_API).buildUpon()
                .appendQueryParameter(QUERY_PARAM , params[0])
                .appendQueryParameter(FORMAT_PARAM , FORMAT_VALUE)
                .appendQueryParameter(UNITS_PARAM , UNITS_VALUE)
                .appendQueryParameter(DAYS_PARAM , Integer.toString(10))
                .build();
    }
}
