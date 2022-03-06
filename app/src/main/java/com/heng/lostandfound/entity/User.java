package com.heng.lostandfound.entity;

import java.io.Serializable;

/**
 * Editor: hengBao
 * Wechat：zh17530588817
 * date: 2022/3/6/15:46
 * title： 用户实体类
 */
public class User implements Serializable {
    private String uid;  //账号
    private  String uPwd;
    private String rName;
    private  Integer uSex;
    private String uPhone;
    private String uAddress;
    private Integer active;
    private Integer uLevel;    //用户级别
    private String userImage;  //用户头像
    private String uWrite;  // 用户备注

    public User() {
    }

    public User(String uid, String uPwd, String rName, Integer uSex, String uPhone, String uAddress, Integer active, Integer uLevel, String userImage, String uWrite) {
        this.uid = uid;
        this.uPwd = uPwd;
        this.rName = rName;
        this.uSex = uSex;
        this.uPhone = uPhone;
        this.uAddress = uAddress;
        this.active = active;
        this.uLevel = uLevel;
        this.userImage = userImage;
        this.uWrite = uWrite;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getuPwd() {
        return uPwd;
    }

    public void setuPwd(String uPwd) {
        this.uPwd = uPwd;
    }

    public String getrName() {
        return rName;
    }

    public void setrName(String rName) {
        this.rName = rName;
    }

    public Integer getuSex() {
        return uSex;
    }

    public void setuSex(Integer uSex) {
        this.uSex = uSex;
    }

    public String getuPhone() {
        return uPhone;
    }

    public void setuPhone(String uPhone) {
        this.uPhone = uPhone;
    }

    public String getuAddress() {
        return uAddress;
    }

    public void setuAddress(String uAddress) {
        this.uAddress = uAddress;
    }

    public Integer getActive() {
        return active;
    }

    public void setActive(Integer active) {
        this.active = active;
    }

    public Integer getuLevel() {
        return uLevel;
    }

    public void setuLevel(Integer uLevel) {
        this.uLevel = uLevel;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public String getuWrite() {
        return uWrite;
    }

    public void setuWrite(String uWrite) {
        this.uWrite = uWrite;
    }

    @Override
    public String toString() {
        return "User{" +
                "uid='" + uid + '\'' +
                ", uPwd='" + uPwd + '\'' +
                ", rName='" + rName + '\'' +
                ", uSex=" + uSex +
                ", uPhone='" + uPhone + '\'' +
                ", uAddress='" + uAddress + '\'' +
                ", active='" + active + '\'' +
                ", uLevel=" + uLevel +
                ", userImage='" + userImage + '\'' +
                ", uWrite='" + uWrite + '\'' +
                '}';
    }
}
