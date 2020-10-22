package com.rsw.mvvmdemo.common;


import com.rsw.mvvmdemo.utils.GsonUtil;

import java.util.HashMap;

/**
 * 键值对上传类
 */
public class JSONS {

    public static String login(String userName, String password) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("userName", userName);
        map.put("password", password);
        map.put("userType", 1);
        return GsonUtil.ser(map);
    }


}
