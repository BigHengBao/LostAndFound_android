package com.heng.lostandfound.entity;

import android.graphics.Bitmap;

import java.io.Serializable;

/**
 * @author : HengZhang
 * @date : 2022/3/10 13:24
 * title:home界面的recycler的item
 */

public class RecyclerItem implements Serializable {
    private String goodsName;
    private Integer orderType;
    private String authorName;
    private String goodsType;
    private String orderTime;
    private String goodsImage;

    public RecyclerItem(String goodsName, Integer orderType, String authorName, String goodsType, String orderTime, String goodsImage) {
        this.goodsName = goodsName;
        this.orderType = orderType;
        this.authorName = authorName;
        this.goodsType = goodsType;
        this.orderTime = orderTime;
        this.goodsImage = goodsImage;
    }

    public RecyclerItem() {
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public Integer getOrderType() {
        return orderType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getGoodsImage() {
        return goodsImage;
    }

    public void setGoodsImage(String goodsImage) {
        this.goodsImage = goodsImage;
    }

    public String getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(String goodsType) {
        this.goodsType = goodsType;
    }

    @Override
    public String toString() {
        return "RecyclerItem{" +
                "goodsName='" + goodsName + '\'' +
                ", orderType=" + orderType +
                ", authorName='" + authorName + '\'' +
                ", goodsType='" + goodsType + '\'' +
                ", orderTime=" + orderTime +
                ", goodsImage=" + goodsImage +
                '}';
    }
}
