package com.a5corp.weather.permissions;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.MenuItem;

import com.a5corp.weather.activity.WeatherActivity;
import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

public class PermissionClass {
    private Intent intent;
    private Context mContext;
    private final int READ_COARSE_LOCATION = 20;

    PermissionClass(Context context) {
        mContext = context;
        intent = new Intent((Activity) mContext , WeatherActivity.class);
        program();
    }

    void program() {

    }

    public void checkPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (!checkIfAlreadyHavePermission()) {
                ActivityCompat.requestPermissions((Activity) mContext, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, READ_COARSE_LOCATION);
            } else {
                permOk();
            }
        } else {
            permOk();
        }
    }

    private boolean checkIfAlreadyHavePermission() {
        int result = ContextCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    public void onRequestPermissionsResult(int requestCode,@NonNull String permissions[],@NonNull int[] grantResults) {
        switch (requestCode) {
            case READ_COARSE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    permOk();

                } else {
                    permission_denied();
                }
                break;
            }
            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    public void permission_denied() {
        // permission was not granted
        //permission is denied (this is the first time, when "never ask again" is not checked) so ask again explaining the usage of permission
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
                        i.setData(Uri.parse("package:" + mContext.getPackageName()));
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

    private void showDialogOK() {
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

    private void permOk() {
        MaterialDialog dialog;
        MaterialDialog.Builder builder = new MaterialDialog.Builder(mContext);
        builder.title("Great!")
                .content("You can now tap on the GPS icon on the main bar and view Weather Data of the current location.\n" +
                        "Try it by clicking on OK Below, then tapping on the GPS Icon in the main bar!")
                .positiveText("OK")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        mContext.startActivity(intent);
                    }
                });
        dialog = builder.build();
        dialog.show();
    }
}
