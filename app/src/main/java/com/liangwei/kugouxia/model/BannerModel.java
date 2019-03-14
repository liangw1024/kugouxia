package com.liangwei.kugouxia.model;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;

import com.liangwei.kugouxia.beans.BannerBean;
import com.liangwei.kugouxia.frame.AppConfig;

/**
 * banner相关数据获取 single class
 */
public class BannerModel extends ModelParent {
    public static String url = AppConfig.app_url+"banner.php";
    private static BannerModel bannerModel;
    public static BannerModel getInstance(){
        if (bannerModel==null){
            bannerModel = new BannerModel();
        }else{

        }
        return bannerModel;
    }


    @Override
    public ArrayList parse(String content,IParse iParse) {
        ArrayList<BannerBean> bannerBeans = new ArrayList<>();
        try{
            JSONArray jsonArray = JSONArray.parseArray(content);
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                String img = jsonObject.getString("imageUrl");
                String title = jsonObject.getString("title");
                String url = jsonObject.getString("url");
                BannerBean bannerBean = new BannerBean(title,img,url);
                bannerBeans.add(bannerBean);
            }
            iParse.success();

        }catch (Exception e){
            iParse.fail(e);
        }
        return bannerBeans;
    }

}
