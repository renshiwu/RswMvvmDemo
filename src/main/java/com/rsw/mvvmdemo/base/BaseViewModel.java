package com.rsw.mvvmdemo.base;

import android.app.Application;

import com.trello.rxlifecycle2.LifecycleTransformer;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

/**
 * Created by RSW
 * on 2020/10/12
 */
public abstract class BaseViewModel<T extends BaseModel> extends AndroidViewModel {
    private T repository;
    private ArrayList<String> onNetTags;


    public BaseViewModel(@NonNull Application application) {
        super(application);
        createRepository();
        onNetTags = new ArrayList<>();
        repository.setOnNetTags(onNetTags);
    }


    public void setObjectLifecycleTransformer(LifecycleTransformer objectLifecycleTransformer) {
        if (repository != null) {
            repository.setObjectLifecycleTransformer(objectLifecycleTransformer);
        }
    }


    public void createRepository() {
        if (repository == null) {
            repository = (T) new RepositoryImpl();
        }
    }


    public T getRepository() {
        return repository;
    }


    @Override
    protected void onCleared() {
        super.onCleared();

    }

}
