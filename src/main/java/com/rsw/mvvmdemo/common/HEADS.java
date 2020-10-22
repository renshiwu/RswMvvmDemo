package com.rsw.mvvmdemo.common;

import java.util.HashMap;


public class HEADS {
    /**
     * 添加头部信息
     */
    public static HashMap<String, String> login(String token) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("Token", token);
        return map;
    }
}
