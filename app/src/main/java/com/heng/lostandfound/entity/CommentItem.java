package com.heng.lostandfound.entity;

import java.io.Serializable;

/**
 * Editor: hengBao
 * Wechat：zh17530588817
 * date: 2022/3/15/19:57
 * title：评论的封装类
 */
public class CommentItem implements Serializable {
    private String username;
    private String content;
    private String addTime;
    private String userImg;

    public CommentItem(String username, String content, String addTime, String userImg) {
        this.username = username;
        this.content = content;
        this.addTime = addTime;
        this.userImg = userImg;
    }

    public CommentItem() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    public String getUserImg() {
        return userImg;
    }

    public void setUserImg(String userImg) {
        this.userImg = userImg;
    }

    @Override
    public String toString() {
        return "CommentItem{" +
                "username='" + username + '\'' +
                ", content='" + content + '\'' +
                ", addTime='" + addTime + '\'' +
                '}';
    }
}
