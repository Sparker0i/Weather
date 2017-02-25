package com.a5corp.weather.permissions;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

public class Permissions {
    private Context mContext;
    final int READ_COARSE_LOCATION = 20;

    public Permissions(Context context) {
        mContext = context;
    }

    public void checkPermission() {
        ActivityCompat.requestPermissions((Activity) mContext, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, READ_COARSE_LOCATION);
    }

    public void showMaterialDialog() {
        MaterialDialog dialog;
        MaterialDialog.Builder builder = new MaterialDialog.Builder(mContext);
        builder.title("Permission needed")
                .content("This Action Requires the Location Setting to be enabled. Go to Settings and check the Location Permission inside the Permissions View")
                .positiveText("SETTINGS")
                .negativeText("CANCEL")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
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
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                });
        dialog = builder.build();
        dialog.show();
    }

    public void showDialogOK() {
        MaterialDialog dialog;
        MaterialDialog.Builder builder = new MaterialDialog.Builder(mContext);
        builder.title("Permission needed")
                .content("This permission is required to access the weather data of your location.")
                .positiveText("OK")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                });
        dialog = builder.build();
        dialog.show();
    }

    public void permission_denied() {
        // permission was not granted
        // permission is denied (this is the first time, when "never ask again" is not checked) so ask again explaining the usage of permission
        // shouldShowRequestPermissionRationale will return true
        //show the dialog or snackbar saying its necessary and try again otherwise proceed with setup.
        if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) mContext,
                Manifest.permission.ACCESS_COARSE_LOCATION)) {
            showDialogOK();
        } //permission is denied (and never ask again is  checked)
        //shouldShowRequestPermissionRationale will return false
        else {
            showMaterialDialog();
        }
    }
}
