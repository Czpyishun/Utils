package com.example.czp.utils.helper.other;

import android.content.Context;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class FormatHelper {

    private Context context;

    public FormatHelper(Context context) {
        this.context = context;
    }


    private NumberFormat mNumberFormat;
    private static final int DOWNLOAD_10W = 10 * 10000;
    private static final int DOWNLOAD_1W = 10000;

    public String parseDownloadCount(long download_count) {

        String str;
        if (mNumberFormat == null) {
            mNumberFormat = new DecimalFormat("0.0");
        }
        if (download_count > DOWNLOAD_10W) {
            str = Long.toString(download_count / 10000);
        } else if (download_count > DOWNLOAD_1W) {
            str = mNumberFormat.format((double) download_count / 10000);
            if (str.lastIndexOf('0') != -1) {
                str = str.substring(0, str.length() - 2);
            }
        } else {
            str = Long.toString(download_count);
        }
        return str;
    }

    public String parsePrice(String price) {

        if (mNumberFormat == null) {
            mNumberFormat = new DecimalFormat("0.00");
        }
        return mNumberFormat.format(Double.parseDouble(price));
    }

    // / \ : * ? " < > ,| &
    private boolean isValidFileName(String name) {
        Pattern pattern = Pattern.compile("[\\/:*?\"<>|]");
        Matcher matcher = pattern.matcher(name);
        return !matcher.find();
    }

    public String replaceInvalidFileName(String name) {
        if (!isValidFileName(name)) {
            Pattern pattern = Pattern.compile("[\\/:*?\"<>|]");
            Matcher matcher = pattern.matcher(name);
            if (matcher.find()) {
                do {
                    name = name.replaceAll(matcher.group(), "");
                } while (matcher.find());
            }
        }
        return name;
    }


    public String getSpeedString(long bytesPerMillisecond) {
        return String.format("%dKB/s", bytesPerMillisecond / 1024);
    }

    public String getTimeRemaining(long durationInMilliseconds) {
        SimpleDateFormat sdf;
        if (durationInMilliseconds > 1000 * 60 * 60) {
            sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
        } else {
            sdf = new SimpleDateFormat("mm:ss", Locale.getDefault());
        }
        return sdf.format(new Date(durationInMilliseconds - TimeZone.getDefault().getRawOffset()));
    }

    public String getFileNameFromUrl(String url) {
        // 通过 ‘？’ 和 ‘/’ 判断文件名
        int index = url.lastIndexOf('?');
        String filename;
        if (index > 1) {
            filename = url.substring(url.lastIndexOf('/') + 1, index);
        } else {
            filename = url.substring(url.lastIndexOf('/') + 1);
        }
        return filename;
    }

    public int getPercent(long progress, long max) {
        int rate = 0;
        if (progress <= 0 || max <= 0) {
            rate = 0;
        } else if (progress > max) {
            rate = 100;
        } else {
            rate = (int) ((double) progress / max * 100);
        }
        return rate;
    }

}
