package com.rsw.mvvmdemo.base;

import android.app.Application;

import androidx.annotation.NonNull;

/**
 * Created by RSW
 * on 2020/10/12
 * 不需要用ViewModel的,请用此类代替
 */
public class NormalViewModel extends BaseViewModel {


    public NormalViewModel(@NonNull Application application) {
        super(application);
    }

}
