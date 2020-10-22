package com.rsw.mvvmdemo.utils;

import android.content.Context;

import com.rsw.mvvmdemo.application.MyApplication;

public class Utils {
    private Utils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static Context getApp() {
        return MyApplication.getContext();
    }
}
