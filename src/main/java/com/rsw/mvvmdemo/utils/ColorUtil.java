package com.rsw.mvvmdemo.utils;

import android.content.Context;

import androidx.core.content.ContextCompat;

/**
 *  @author rtrs-renshiwu
 *  @time 2020/9/14  14:40
 *  @describe
 */
public class ColorUtil {
    public static int getColor(Context context,int colorId){
       return ContextCompat.getColor(context,colorId);
    }
}
