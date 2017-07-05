package com.a5corp.weather.permissions;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;

import com.a5corp.weather.R;
import com.a5corp.weather.activity.WeatherActivity;
import com.a5corp.weather.utils.Constants;

public class Permissions {
    private Context mContext;

    public Permissions(Context context) {
        mContext = context;
    }

    private void showNoRationale() {
        View rootView = ((Activity) mContext).getWindow().getDecorView().findViewById(R.id.fragment);
        Snackbar.make(rootView , "Tap Settings, then in Permissions, enable the Location Permission to do this action" , Snackbar.LENGTH_INDEFINITE)
                .setAction("SETTINGS", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final Intent i = new Intent();
                        i.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        i.addCategory(Intent.CATEGORY_DEFAULT);
                        i.setData(Uri.parse("package:" + mContext.getApplicationContext().getPackageName()));
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                        i.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                        mContext.startActivity(i);
                    }
                })
                .show();
        Log.i("Permissions" , "showNoRationale");
    }

    private void showRationale() {
        View rootView = ((Activity) mContext).getWindow().getDecorView().findViewById(R.id.fragment);
        Snackbar.make(rootView , "This Permission Is Required to access Weather Data of your location" , Snackbar.LENGTH_LONG).show();
        Log.i("Permissions" , "showRationale");
    }

    private void showStorageRationale() {
        View rootView = ((Activity) mContext).getWindow().getDecorView().findViewById(R.id.fragment);
        Snackbar.make(rootView , "This Permission Is Required to store the QR Code to your gallery so that you can open it in the PayTm app" , Snackbar.LENGTH_LONG).show();
        Log.i("Permissions" , "showRationale");
    }

    public void permissionDenied() {
        // permission was not granted
        // permission is denied (this is the first time, when "never ask again" is not checked) so ask again explaining the usage of permission
        // shouldShowRequestPermissionRationale will return true
        //show the dialog or snackbar saying its necessary and try again otherwise proceed with setup.
        Log.i("Denied" , "Permission");
        if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) mContext,
                Manifest.permission.ACCESS_COARSE_LOCATION)) {
            showRationale();
        } //permission is denied (and never ask again is  checked)
        //shouldShowRequestPermissionRationale will return false
        else {
            showNoRationale();
        }
    }

    public void showDenialMessage(int requestCode) {
        switch (requestCode) {
            case Constants.WRITE_EXTERNAL_STORAGE:
                if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) mContext,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    showStorageRationale();
                }
        }
    }
}
