package com.a5corp.weather.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.a5corp.weather.R;
import com.a5corp.weather.model.Global;

import java.util.concurrent.ExecutionException;

public class LoadingFragment extends Fragment {

    View rootView;
    Global global;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_loading, container, false);
        HandlerThread handlerThread = new HandlerThread("background-handler");
        handlerThread.start();
        Looper looper = handlerThread.getLooper();
        global  = new Global();

        new Handler(looper).postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    global.init();
                }
                catch (InterruptedException | ExecutionException ex) {
                    ex.printStackTrace();
                }
            }
        } , 1000);
        return rootView;
    }
}
