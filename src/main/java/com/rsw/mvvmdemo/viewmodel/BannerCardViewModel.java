package com.rsw.mvvmdemo.viewmodel;

import android.app.Application;

import com.rsw.mvvmdemo.base.BaseViewModel;
import com.rsw.mvvmdemo.base.RepositoryImpl;

import androidx.annotation.NonNull;

/**
 * Created by RSW
 * on 2020/10/12
 */
public class BannerCardViewModel extends BaseViewModel<RepositoryImpl> {
    public BannerCardViewModel(@NonNull Application application) {
        super(application);
    }
}
