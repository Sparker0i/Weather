package com.a5corp.weather.fragment;


import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.a5corp.weather.R;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import org.json.JSONException;
import org.json.JSONObject;

public class MapsFragment extends Fragment {

    public View rootView;
    WebView webView;
    Bundle bundle;
    private BottomBar mBottomBar;

    public MapsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_maps, container, false);
        webView = (WebView) rootView.findViewById(R.id.webView);
        mBottomBar = (BottomBar) rootView.findViewById(R.id.bottomBar);
        bundle = this.getArguments();
        mapsloader();
        return rootView;
    }

    public void mapsloader() {
        try {
            JSONObject json = new JSONObject(bundle.getString("json"));
            webView.getSettings().setJavaScriptEnabled(true);
            webView.loadUrl("file:///android_asset/map.html?lat=" + json.getJSONObject("city").getJSONObject("coord").getDouble("lon") + "&lon=" + json.getJSONObject("city").getJSONObject("coord").getDouble("lat") + "&appid=" + getString(R.string.open_weather_maps_app_id));
            mBottomBar.setOnTabSelectListener(new OnTabSelectListener() {
                @Override
                public void onTabSelected(@IdRes int tabId) {
                    if (tabId == R.id.map_rain) {
                        webView.loadUrl("javascript:map.removeLayer(windLayer);map.removeLayer(tempLayer);map.addLayer(rainLayer);");
                    }
                    else if (tabId == R.id.map_temperature) {
                        webView.loadUrl("javascript:map.removeLayer(windLayer);map.removeLayer(rainLayer);map.addLayer(tempLayer);");
                    }
                    else if (tabId == R.id.map_wind) {
                        webView.loadUrl("javascript:map.removeLayer(rainLayer);map.removeLayer(tempLayer);map.addLayer(windLayer);");
                    }
                }
            });

        }
        catch (JSONException jex) {

        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mBottomBar.onSaveInstanceState();
    }
}
