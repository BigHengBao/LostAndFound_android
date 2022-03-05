package com.heng.lostandfound.api;

/**
 * 回调接口
 */
public interface ApiCallback {

    void onSuccess(String res);

    void onFailure(Exception e);
}
