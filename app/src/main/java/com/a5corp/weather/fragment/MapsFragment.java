package com.a5corp.weather.fragment;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.a5corp.weather.R;
import com.a5corp.weather.activity.WeatherActivity;
import com.a5corp.weather.preferences.Preferences;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

public class MapsFragment extends Fragment {

    public View rootView;
    WebView webView;
    BottomBar mBottomBar;
    Preferences prefs;

    public MapsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_maps, container, false);
        webView = (WebView) rootView.findViewById(R.id.webView);
        prefs = new Preferences(getContext());
        loadMap();
        ((WeatherActivity) getActivity()).hideFab();
        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    public void loadMap() {
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("file:///android_asset/map.html?lat=" + prefs.getLatitude() + "&lon=" + prefs.getLongitude() + "&k=2.0" + "&appid=" + getString(R.string.open_weather_maps_app_id));
        webView.setInitialScale(1);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        mBottomBar = (BottomBar) rootView.findViewById(R.id.bottomBar);
        mBottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int menuItemId) {
                if (menuItemId == R.id.map_rain) {
                    webView.loadUrl("javascript:map.removeLayer(windLayer);map.removeLayer(tempLayer);map.addLayer(rainLayer);");
                } else if (menuItemId == R.id.map_wind) {
                    webView.loadUrl("javascript:map.removeLayer(rainLayer);map.removeLayer(tempLayer);map.addLayer(windLayer);");
                } else if (menuItemId == R.id.map_temperature) {
                    webView.loadUrl("javascript:map.removeLayer(windLayer);map.removeLayer(rainLayer);map.addLayer(tempLayer);");
                }
            }
        });
    }
}
