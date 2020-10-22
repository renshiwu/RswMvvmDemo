package com.rsw.mvvmdemo.activity.collect.collect;

import android.app.Application;

import com.rsw.mvvmdemo.base.BaseViewModel;
import com.rsw.mvvmdemo.base.RepositoryImpl;
import com.rsw.mvvmdemo.bean.basebean.HomeFatherBean;
import com.rsw.mvvmdemo.bean.basebean.ParamsBuilder;
import com.rsw.mvvmdemo.bean.basebean.Resource;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

/**
 * Created by leo
 * on 2019/11/14.
 */
public class CollectViewModel extends BaseViewModel<RepositoryImpl> {

    public CollectViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<Resource<HomeFatherBean>> getCollectArticles(int curPage, ParamsBuilder paramsBuilder) {
        return getRepository().getCollectArticles(curPage, paramsBuilder);
    }

    public LiveData<Resource<String>> unCollectByMe(int id, int originId) {
        return getRepository().unCollectByMe(id, originId);
    }

}
