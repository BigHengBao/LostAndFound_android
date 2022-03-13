package com.heng.lostandfound.entity;

import org.w3c.dom.Comment;

import java.io.Serializable;

/**
 * Editor: hengBao
 * Wechat：zh17530588817
 * date: 2022/3/13/13:42
 * title：物品详细信息封装类
 */
public class GoodsInfoItem implements Serializable {
    private String authorName;
    private Goods goods;
    private Comment comment;
    private UserCollection collection;
    private Order order;

    public GoodsInfoItem(String authorName, Goods goods, Comment comment, UserCollection collection, Order order) {
        this.authorName = authorName;
        this.goods = goods;
        this.comment = comment;
        this.collection = collection;
        this.order = order;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public GoodsInfoItem() {
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public Goods getGoods() {
        return goods;
    }

    public void setGoods(Goods goods) {
        this.goods = goods;
    }

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    public UserCollection getCollection() {
        return collection;
    }

    public void setCollection(UserCollection collection) {
        this.collection = collection;
    }

    @Override
    public String toString() {
        return "GoodsInfoItem{" +
                "authorName='" + authorName + '\'' +
                ", goods=" + goods +
                ", comment=" + comment +
                ", collection=" + collection +
                ", order=" + order +
                '}';
    }
}
