package com.rsw.mvvmdemo.viewmodel;

import android.app.Application;

import com.rsw.mvvmdemo.base.BaseViewModel;
import com.rsw.mvvmdemo.base.RepositoryImpl;
import com.rsw.mvvmdemo.bean.YinshiBean;
import com.rsw.mvvmdemo.bean.basebean.ParamsBuilder;
import com.rsw.mvvmdemo.bean.basebean.Resource;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

/**
 * Created by RSW
 * on 2020/10/13
 */
public class NominateViewModel extends BaseViewModel<RepositoryImpl> {
    public NominateViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<Resource<List<YinshiBean.ListBean>>> getYunfuyinshiListBykeyWord(int page, int size, String keyword, ParamsBuilder paramsBuilder) {
        return getRepository().getYunfuyinshiListBykeyWord(page, size, keyword, paramsBuilder);
    }

    public LiveData<Resource<List<YinshiBean.ListBean>>> getBanners(int size, ParamsBuilder paramsBuilder) {
        return getRepository().getBanners(size, paramsBuilder);
    }

}