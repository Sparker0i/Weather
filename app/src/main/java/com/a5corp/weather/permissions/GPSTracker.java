package com.a5corp.weather.permissions;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.text.DecimalFormat;

import static android.content.Context.LOCATION_SERVICE;

public class GPSTracker implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener {
    LocationManager service;
    private Context mContext;
    private Location mLastLocation;
    private GoogleApiClient mGoogleApiClient;
    LocationRequest mLocationRequest;
    private String lat, lon;

    GPSTracker(Context context) {
        mContext = context;
        buildGoogleApiClient();
        service = (LocationManager) mContext.getSystemService(LOCATION_SERVICE);
        boolean enabled = service.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (!enabled) {
            View parent = ((Activity) mContext).getCurrentFocus();
            new MaterialDialog.Builder(mContext)
                    .content("GPS Needs to be enabled to view Weather Data of your Location")
                    .title("Enable GPS")
                    .positiveText("ENABLE")
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            mContext.startActivity(intent);
                        }
                    })
                    .show();
        }
    }

    public String getLatitude() {
        return lat;
    }

    public String getLongitude() {
        return lon;
    }

    private void program() {

    }

    @Override
    public void onConnected(Bundle bundle) {
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(100); // Update location every second
        try {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                    mGoogleApiClient);
        }
        catch (SecurityException ex) {
            Log.e("Error" , "Helloworld");
        }
        if (mLastLocation != null) {
            lat = String.valueOf(mLastLocation.getLatitude());
            lon = String.valueOf(mLastLocation.getLongitude());
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(Location location) {
        lat = String.valueOf(location.getLatitude());
        lon = String.valueOf(location.getLongitude());
        DecimalFormat df = new DecimalFormat("###.##");
        double la = Double.parseDouble(lat);
        double lo = Double.parseDouble(lon);
        lat = df.format(la);
        lon = df.format(lo);
        Log.i("lat", lat);
        Log.i("lon", lon);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        buildGoogleApiClient();
    }

    private synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(mContext)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

}
