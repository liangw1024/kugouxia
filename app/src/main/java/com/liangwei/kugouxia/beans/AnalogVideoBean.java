package com.liangwei.kugouxia.beans;

import java.io.Serializable;

/**
 * 模拟视频数据
 * Created by weibao on 2018/6/11.
 */

public class AnalogVideoBean implements Serializable {
    private String imgUrl;
    private String videoUrl;
    private String title;

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
