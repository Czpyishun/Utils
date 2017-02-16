package com.example.czp.utils.helper.device;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import java.io.File;

public class DeviceHelper {


    public String getDeviceId(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return tm.getDeviceId();
    }

    public String getMacAddress(Context context) {
        try {
            WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            WifiInfo info = wifi.getConnectionInfo();
            return info.getMacAddress();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public String getAndroidId(Context context) {
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    public String getDeviceSystemVer() {
        return Build.VERSION.RELEASE;
    }


    public int getAppVersionCode(Context context, String package_name) {
        int appVersionCode = 0;
        try {
            if (!TextUtils.isEmpty(package_name)) {
                PackageInfo packageInfo = context.getPackageManager().getPackageInfo(package_name, 0);
                appVersionCode = packageInfo.versionCode;
            }
        } catch (Exception e) {
            return appVersionCode;
        }
        return appVersionCode;
    }

    public String getAppVersionName(Context context, String package_name) {
        String appVersionName = "";
        try {
            if (!TextUtils.isEmpty(package_name)) {
                PackageInfo packageInfo = context.getPackageManager().getPackageInfo(package_name, 0);
                appVersionName = packageInfo.versionName;
            }
        } catch (Exception e) {
            return appVersionName;
        }
        return appVersionName;
    }

    public boolean isInstallApp(Context context, String package_name) {
        try {
            if (!TextUtils.isEmpty(package_name)) {
                context.getPackageManager().getPackageInfo(package_name, 0);
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public void install(Context context, String local_path) {
        try {
            Intent intentInstall = new Intent(Intent.ACTION_VIEW);
            intentInstall.setDataAndType(Uri.parse("file://" + local_path), "application/vnd.android.package-archive");
            intentInstall.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intentInstall);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public boolean isDownloaded(String Version) {
        File file = new File(getDownloadPath() + "/app_" + Version + ".apk");
        if (file.exists()) {
            return true;
        }
        return false;
    }

    public File getFile(String Version, int type) throws Exception {
        String fileType = ".download";
        if (type == 1) {
            fileType = ".apk";
        }
        File file = new File(getDownloadPath() + "/app_" + Version + fileType);
        if (!file.exists()) {
            file.createNewFile();
        }
        return file;
    }

    public String getDownloadPath() {
        String mDownloadPath = "";
        if (!TextUtils.isEmpty(mDownloadPath)) {
            return mDownloadPath;
        }
        mDownloadPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/fanshu";
        File file = new File(mDownloadPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        return mDownloadPath;
    }

    public boolean updateFileName(String oldPath, String newPath) {
        boolean isUpdateName = false;
        if (isApkExists(oldPath)) {
            File file = new File(oldPath);
            isUpdateName = file.renameTo(new File(newPath));
        }
        return isUpdateName;
    }

    public boolean isApkExists(String local_path) {
        boolean isApkExists = false;
        if (!TextUtils.isEmpty(local_path)) {
            File file = new File(local_path);
            if (file.exists()) {
                isApkExists = true;
            }
        }
        return isApkExists;
    }



}
