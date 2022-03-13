package com.heng.lostandfound.entity;

import android.graphics.Bitmap;

import java.io.Serializable;

/**
 * @author : HengZhang
 * @date : 2022/3/10 11:11
 */

public class HomeGVItem implements Serializable {
    private String itemName;
    Bitmap ItemImg;
    private String userName;
    private String userAccount;

    public HomeGVItem(String itemName, Bitmap itemImg, String userName, String userAccount) {
        this.itemName = itemName;
        ItemImg = itemImg;
        this.userName = userName;
        this.userAccount = userAccount;
    }

    public HomeGVItem() {
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Bitmap getItemImg() {
        return ItemImg;
    }

    public void setItemImg(Bitmap itemImg) {
        ItemImg = itemImg;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    @Override
    public String toString() {
        return "HomeGVItem{" +
                "itemName='" + itemName + '\'' +
                ", ItemImg=" + ItemImg +
                ", userName='" + userName + '\'' +
                ", userAccount='" + userAccount + '\'' +
                '}';
    }
}
