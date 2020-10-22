package com.rsw.mvvmdemo.fragment;

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
 * on 2020/10/12
 */

public class NewsViewModel extends BaseViewModel<RepositoryImpl> {

    public NewsViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<Resource<List<YinshiBean.ListBean>>> getYunfuyinshiList(int page, int size, int class_id, ParamsBuilder paramsBuilder) {
        return getRepository().getYunfuyinshiList(page, size, class_id, paramsBuilder);
    }
}