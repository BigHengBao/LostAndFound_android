package com.heng.lostandfound.entity;

import java.io.Serializable;

/**
 * Editor: hengBao
 * Wechat：zh17530588817
 * date: 2022/3/13/16:16
 * title：商品用户表
 */
public class Order implements Serializable {
    private String gName;
    private String uAccount;
    private Integer active;
    private Integer type;  //类型：0：寻物，1：招领

    public Order() {
    }

    public Order(String gName, String uAccount, Integer active, Integer type) {
        this.gName = gName;
        this.uAccount = uAccount;
        this.active = active;
        this.type = type;
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

    public Integer getActive() {
        return active;
    }

    public void setActive(Integer active) {
        this.active = active;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Order{" +
                "gName='" + gName + '\'' +
                ", uAccount='" + uAccount + '\'' +
                ", active=" + active +
                ", type=" + type +
                '}';
    }
}
