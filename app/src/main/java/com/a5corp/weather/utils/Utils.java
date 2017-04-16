package com.a5corp.weather.utils;

import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;

public class Utils {
    private Context mContext;

    public Utils(Context context) {
        mContext = context;
    }

    public boolean isFirstInstall() {
        try {
            long firstInstallTime =  mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0).firstInstallTime;
            long lastUpdateTime = mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0).lastUpdateTime;
            Log.i("First" , firstInstallTime == lastUpdateTime ? "True" : "False");
            return firstInstallTime == lastUpdateTime;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }



    public boolean isInstallFromUpdate() {
        try {
            long firstInstallTime =   mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0).firstInstallTime;
            long lastUpdateTime = mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0).lastUpdateTime;
            Log.i("Updated" , firstInstallTime == lastUpdateTime ? "True" : "False");
            return firstInstallTime != lastUpdateTime;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }
}
