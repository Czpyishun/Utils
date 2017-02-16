package com.example.czp.utils.helper.base;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;


public class ToastHelper {

    private Context mContext;

    public ToastHelper(Context context) {
        mContext = context;
    }

    Toast toast = null;

    private void showToast(String msg, int msgResId, int time) {
        if (TextUtils.isEmpty(msg) && msgResId == -1) {
            return;
        }
        String text = TextUtils.isEmpty(msg) ? mContext.getResources().getString(msgResId) : msg;
        if (toast == null) {
            toast = Toast.makeText(mContext, text, time);
        } else {
            toast.setText(text);
        }
        toast.show();
    }

    public void showLongToast(int msgResId) {
        showToast(null, msgResId, Toast.LENGTH_LONG);
    }

    public void showLongToast(String msg) {
        showToast(msg, -1, Toast.LENGTH_LONG);
    }

    public void showShortToast(int msgResId) {
        showToast(null, msgResId, Toast.LENGTH_SHORT);
    }

    public void showShortToast(String msg) {
        showToast(msg, -1, Toast.LENGTH_SHORT);
    }
}
