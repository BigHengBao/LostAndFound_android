package com.heng.lostandfound.entity;

import java.io.Serializable;

/**
 * @author : HengZhang
 * @date : 2022/3/5 10:44
 */

public class MyResponse implements Serializable {
    private String requestId;
    private boolean result;
    private String msg;

    public MyResponse(String requestId, boolean result, String msg) {
        this.requestId = requestId;
        this.result = result;
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "MyResponse{" +
                "requestId='" + requestId + '\'' +
                ", result=" + result +
                ", msg='" + msg + '\'' +
                '}';
    }
}
