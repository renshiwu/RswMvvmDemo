package com.rsw.mvvmdemo.bean.basebean;

import java.io.Serializable;

/**
 * 这个类是泛型类，可根据后端的返回字段修改
 */
public class ResponModel<T> implements Serializable {
    private T list;

    public T getData() {
        return list;
    }

    private int errorCode;
    private String errorMsg;

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
