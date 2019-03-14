package com.liangwei.kugouxia.beans;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 葫芦侠beam
 * Created by weibao on 2018/4/26.
 */

public class HlxXianBaoBean {
    //贴子id
    private String postID;
    //post标题
    private String title;
    //post详情
    private String detail;
    //post创建时间
    private String createTime;
    //post图片
    private List<String> images;


    public String getPostID() {
        return postID;
    }

    public void setPostID(String postID) {
        this.postID = postID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }


    /**
     * 时间戳转换为北京时间
     * @param stamp
     * @return
     */
   public static String stampToTime(String stamp){
       DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
       Date date = new Date(Long.valueOf(stamp));
       return dateFormat.format(date);
   }

}
