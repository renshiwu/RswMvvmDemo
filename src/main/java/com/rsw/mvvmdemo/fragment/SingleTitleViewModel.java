package com.rsw.mvvmdemo.fragment;

import android.app.Application;

import com.rsw.mvvmdemo.base.BaseViewModel;
import com.rsw.mvvmdemo.base.RepositoryImpl;

import androidx.annotation.NonNull;

/**
 * Created by RSW
 * on 2020/10/12
 */
public class SingleTitleViewModel extends BaseViewModel<RepositoryImpl> {
    public SingleTitleViewModel(@NonNull Application application) {
        super(application);
    }
    public String title;
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
}
