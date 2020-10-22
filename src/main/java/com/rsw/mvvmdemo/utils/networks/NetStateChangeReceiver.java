package com.rsw.mvvmdemo.utils.networks;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;

import com.rsw.mvvmdemo.application.MyApplication;

import java.util.ArrayList;
import java.util.List;

/**
 * 网络监听的广播
 *
 */
public class NetStateChangeReceiver extends BroadcastReceiver {

    @SuppressLint("MissingPermission")
    private NetworkType mType = NetWorkUtils.getNetworkType(MyApplication.getContext());

    private static class InstanceHolder{
        private static final NetStateChangeReceiver INSTANCE = new NetStateChangeReceiver();
    }

    private List<NetStateChangeObserver> mObservers = new ArrayList<>();


    //注册了网络状态改变监听，一旦网络状态改变就会调用的方法
    @Override
    public void onReceive(Context context, Intent intent) {
        if (ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())){
            @SuppressLint("MissingPermission") NetworkType networkType = NetWorkUtils.getNetworkType(context);
            notifyObservers(networkType);
        }
    }

    //注册广播
    public static void registerReceiver(Context context){
        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        context.registerReceiver( InstanceHolder.INSTANCE,intentFilter);
    }


    //注释广播
    public static void unRegisterReceiver(Context context){
        context.unregisterReceiver( InstanceHolder.INSTANCE);
    }


    //添加观察者，一旦有改变，观察者会通知
    public static void registerObserver(NetStateChangeObserver observer){
        if (observer == null) {
            return;
        }
        if (!InstanceHolder.INSTANCE.mObservers.contains(observer)){
            InstanceHolder.INSTANCE.mObservers.add(observer);
        }
    }

    //观察者
    public static void unRegisterObserver(NetStateChangeObserver observer){
        if (observer == null) {
            return;
        }
        if (InstanceHolder.INSTANCE.mObservers == null) {
            return;
        }
        InstanceHolder.INSTANCE.mObservers.remove(observer);
    }

    private void notifyObservers(NetworkType networkType){
        if (mType == networkType) {
            return;
        }
        mType = networkType;
        if (networkType == NetworkType.NETWORK_NO){
            for (NetStateChangeObserver observer : mObservers){
                observer.onNetDisconnected();
            }
        }else {
            for (NetStateChangeObserver observer : mObservers){
                observer.onNetConnected(networkType);
            }
        }
    }
}
