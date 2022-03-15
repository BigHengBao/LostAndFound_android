package com.heng.lostandfound.entity;

/**
 * @author : HengZhang
 * @date : 2022/3/13 19:20
 * title：评论表
 */
public class Comment {
    private String gName;
    private String uAccount;
    private String content;
    private String addTime;
    private Integer active;
    private String authorName;

    public Comment(String gName, String uAccount, String content, String addTime, Integer active, String authorName) {
        this.gName = gName;
        this.uAccount = uAccount;
        this.content = content;
        this.addTime = addTime;
        this.active = active;
        this.authorName = authorName;
    }


    public Comment() {
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

    public Integer getActive() {
        return active;
    }

    public void setActive(Integer active) {
        this.active = active;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "gName='" + gName + '\'' +
                ", uAccount='" + uAccount + '\'' +
                ", content='" + content + '\'' +
                ", addTime='" + addTime + '\'' +
                ", active=" + active +
                ", authorName='" + authorName + '\'' +
                '}';
    }
}
