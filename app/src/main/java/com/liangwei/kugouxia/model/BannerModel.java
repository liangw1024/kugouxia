package com.liangwei.kugouxia.model;

import android.util.Log;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;

import com.google.gson.JsonObject;
import com.liangwei.kugouxia.beans.BannerBean;
import com.liangwei.kugouxia.frame.AppConfig;

/**
 * banner相关数据获取 single class
 */
public class BannerModel extends ModelParent {
    //获取banner数据接口
    public static String url = AppConfig.app_url+"index.php?action=1";
    private static BannerModel bannerModel;
    //获取对象
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
            JSONObject json = (JSONObject) JSONObject.parse(content);

            JSONArray jsonArray = json.getJSONArray("content");
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                String img = jsonObject.getString("img_url");
                String title = jsonObject.getString("title");
                String url = jsonObject.getString("link");
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
