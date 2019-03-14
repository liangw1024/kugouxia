package com.liangwei.kugouxia.beans;

import java.util.List;

/**
 * Created by weibao on 2018/5/19.
 */

/**
 * 精选图片数据
 */
public class ChoicePicBean {
    private String title;
    private String describe;
    private List<String> images;
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }


}
