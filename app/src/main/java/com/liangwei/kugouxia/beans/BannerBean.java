package com.liangwei.kugouxia.beans;


/**
 * banner 数据
 * Created by-liangWei on 2018/8/9 16:10
 */
public class BannerBean {
    private String title;
    private String img;
    private String url;
    public BannerBean(String title, String img, String url) {
        this.title = title;
        this.img = img;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
