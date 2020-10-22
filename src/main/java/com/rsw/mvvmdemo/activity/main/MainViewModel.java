package com.rsw.mvvmdemo.activity.main;

import android.app.Application;

import com.rsw.mvvmdemo.base.BaseViewModel;
import com.rsw.mvvmdemo.base.RepositoryImpl;

import androidx.annotation.NonNull;

/**
 * Created by RSW
 * on 2020/10/12
 */
public class MainViewModel extends BaseViewModel<RepositoryImpl> {

    public MainViewModel(@NonNull Application application) {
        super(application);
    }

//    public LiveData<Resource<HomeFatherBean>> getCollectArticles(int curPage, ParamsBuilder paramsBuilder) {
//        return getRepository().getCollectArticles(curPage, paramsBuilder);
//    }

}
