package com.rsw.mvvmdemo.utils;

import android.content.Context;
import android.widget.Toast;

import com.rsw.mvvmdemo.application.MyApplication;


/**
 * 防止重复点击toast，一直显示未隐藏
 */
public class ToastUtils {
    private static Context context = MyApplication.getContext();
    /**
     * Toast对象
     */
    private static Toast toast = null;


    /**
     * 显示Toast
     *
     * @param message
     */
    public static void showToast(String message) {
        if (ButtonClickUtils.isFastClick()) {
            return;
        }
        if (toast == null) {
            toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
            toast.show();
        } else {
            toast.cancel();
            toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}
