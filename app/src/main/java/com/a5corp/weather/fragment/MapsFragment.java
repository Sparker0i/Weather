package com.a5corp.weather.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.a5corp.weather.R;
import com.roughike.bottombar.BottomBar;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 */
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
        mBottomBar = new BottomBar(getContext());
        bundle = this.getArguments();
        return rootView;
    }

    public void mapsloader() {
        try {
            JSONObject json = new JSONObject(bundle.getString("json"));
            webView.getSettings().setJavaScriptEnabled(true);
            webView.loadUrl("file:///android_asset/map.html?lat=" + json.getJSONObject("city").getJSONObject("coord").getDouble("lat") + "&lon=" + json.getJSONObject("city").getJSONObject("coord").getDouble("lat") + "&appid=" + getString(R.string.open_weather_maps_app_id));
        }
        catch (JSONException jex) {

        }
    }
}
