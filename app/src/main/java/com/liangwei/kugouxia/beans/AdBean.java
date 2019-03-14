package com.liangwei.kugouxia.beans;

/**
 * scrollbar ad 的数据
 * Created by-liangWei on 2018/8/19 12:43
 */
public class AdBean {
    private String title;
    private String description;
    private Object img;
    private Object clickurl;
    public AdBean(String title, String description, Object img, Object clickurl) {
        this.title = title;
        this.description = description;
        this.img = img;
        this.clickurl = clickurl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Object getImg() {
        return img;
    }

    public void setImg(Object img) {
        this.img = img;
    }

    public Object getClickurl() {
        return clickurl;
    }

    public void setClickurl(Object clickurl) {
        this.clickurl = clickurl;
    }
}
