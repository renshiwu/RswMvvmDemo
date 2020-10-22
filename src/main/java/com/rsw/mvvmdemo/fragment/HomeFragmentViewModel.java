package com.rsw.mvvmdemo.fragment;

import android.app.Application;

import com.rsw.mvvmdemo.base.BaseViewModel;
import com.rsw.mvvmdemo.base.RepositoryImpl;

import androidx.annotation.NonNull;

/**
 * Created by RSW
 * on 2020/10/12
 */

public class HomeFragmentViewModel extends BaseViewModel<RepositoryImpl> {

    public HomeFragmentViewModel(@NonNull Application application) {
        super(application);
    }

//    public LiveData<Resource<HomeFatherBean>> getCollectArticles(int curPage, ParamsBuilder paramsBuilder) {
//        return getRepository().getCollectArticles(curPage, paramsBuilder);
//    }

}