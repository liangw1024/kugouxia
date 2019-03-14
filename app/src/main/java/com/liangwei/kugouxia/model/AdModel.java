package com.liangwei.kugouxia.model;

import android.util.Log;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;

import com.liangwei.kugouxia.beans.AdBean;
import com.liangwei.kugouxia.frame.AppConfig;

/**
 * ad Model
 */
public class AdModel extends ModelParent {
    public static String adUrl = AppConfig.app_url+"adData.php";

    private static AdModel model;
    public static AdModel getInstance(){
        if (model==null){
            model = new AdModel();
        }else{

        }
        return model;
    }



    @Override
    public ArrayList parse(String content, IParse parseCallback) {
        ArrayList<AdBean> arrayList = new ArrayList<>();
        try{
            JSONArray jsonArray = JSONArray.parseArray(content);
            for (int index=0;index<jsonArray.size();index++){
                JSONObject jsonData = jsonArray.getJSONObject(index);
                String title = jsonData.getString("title");
                String description = jsonData.getString("description");
                String imgUrl = jsonData.getString("img");
                String clickUrl = jsonData.getString("click");
                AdBean data = new AdBean(title,description,imgUrl,clickUrl);
                arrayList.add(data);
            }
            parseCallback.success();
        }catch (Exception e){
            parseCallback.fail(e);
        }

        return arrayList;
    }
}
