package com.heng.lostandfound.entity;

/**
 * @author : HengZhang
 * @date : 2022/3/5 10:44
 */

public class LoginResponse {
    private String requestId;
    private boolean result;

    public LoginResponse(String requestId, boolean result) {
        this.requestId = requestId;
        this.result = result;
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
        return "LoginResponse{" +
                "requestId='" + requestId + '\'' +
                ", result=" + result +
                '}';
    }
}
