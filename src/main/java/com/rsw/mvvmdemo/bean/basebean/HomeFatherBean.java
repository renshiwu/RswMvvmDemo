package com.rsw.mvvmdemo.bean.basebean;


import com.rsw.mvvmdemo.bean.HomeBean;

import java.io.Serializable;
import java.util.ArrayList;


public class HomeFatherBean implements Serializable {
    private ArrayList<HomeBean> datas;

    public ArrayList<HomeBean> getDatas() {
        return datas;
    }

    public void setDatas(ArrayList<HomeBean> datas) {
        this.datas = datas;
    }
}
