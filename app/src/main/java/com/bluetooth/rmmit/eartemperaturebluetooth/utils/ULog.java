package com.bluetooth.rmmit.eartemperaturebluetooth.utils;

import android.util.Log;

/**
 * Created by Administrator on 2016/6/28.
 */
public class ULog {
    /** 正式上线是设为false */
    private static boolean testMode = true;

    public static void v(String tag, String msg) {
        if (testMode) {
            Log.v(tag, msg);
        }
    }

    public static void d(String tag, String msg) {
        if (testMode) {
            Log.d(tag, msg);
        }
    }

    public static void i(String tag, String msg) {
        if (testMode) {
            Log.i(tag, msg);
        }

    }

    public static void w(String tag, String msg) {
        if (testMode) {
            Log.w(tag, msg);
        }
    }

    public static void e(String tag, String msg) {
        if (testMode) {
            Log.e(tag, msg);
        }
    }
}
