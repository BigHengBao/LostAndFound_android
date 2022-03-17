package com.heng.lostandfound.entity;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Editor: hengBao
 * Wechat：zh17530588817
 * date: 2022/3/13/16:40
 * title：收藏表
 */public class UserCollection {
    private String gName;  //物品名称
    private String uAccount;  //用户账号
    private String addTime;
    private Integer active;
    private String gAuthorName;    //发启事的账号

    public UserCollection(String gName, String uAccount, String addTime, Integer active, String gAuthorName) {
        this.gName = gName;
        this.uAccount = uAccount;
        this.addTime = addTime;
        this.active = active;
        this.gAuthorName = gAuthorName;
    }

    public UserCollection() {
    }

    public String getgName() {
        return gName;
    }

    public void setgName(String gName) {
        this.gName = gName;
    }

    public String getuAccount() {
        return uAccount;
    }

    public void setuAccount(String uAccount) {
        this.uAccount = uAccount;
    }

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    public Integer getActive() {
        return active;
    }

    public void setActive(Integer active) {
        this.active = active;
    }

    public String getgAuthorName() {
        return gAuthorName;
    }

    public void setgAuthorName(String gAuthorName) {
        this.gAuthorName = gAuthorName;
    }

    @Override
    public String toString() {
        return "collection{" +
                "gName='" + gName + '\'' +
                ", uid='" + uAccount + '\'' +
                ", addTime=" + addTime +
                ", active=" + active +
                ", gUser='" + gAuthorName + '\'' +
                '}';
    }
}
