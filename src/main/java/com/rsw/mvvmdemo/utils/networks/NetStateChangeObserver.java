package com.rsw.mvvmdemo.utils.networks;

/**
 * 观察者模式接口，目前可理解为。回调
 */
public interface NetStateChangeObserver {
    //网络断开连接的回调
    void onNetDisconnected();
    //有网络连接的回调
    void onNetConnected(NetworkType networkType);
}


