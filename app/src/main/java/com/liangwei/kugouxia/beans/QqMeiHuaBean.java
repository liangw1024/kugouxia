package com.liangwei.kugouxia.beans;

import java.util.List;

/**
 * qq美化 bean
 * Created by weibao on 2018/5/4.
 */

public class QqMeiHuaBean {

    //post images
    private List<String> images;
    //post userName
    private String userName;
    //post usetHeadUrl
    private String userHeadUrl;
    //post title
    private String title;
    //post content
    private String content;
    //post send time
    private String time;

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserHeadUrl() {
        return userHeadUrl;
    }

    public void setUserHeadUrl(String userHeadUrl) {
        this.userHeadUrl = userHeadUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        String time = this.time.replace("T","  ");
        String mTime = time.replace("Z","");
        //去除后面4位数
        String result = mTime.substring(0,mTime.length()-5);
        return result;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
