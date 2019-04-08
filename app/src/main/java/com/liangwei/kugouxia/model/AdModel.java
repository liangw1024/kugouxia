package com.liangwei.kugouxia.model;



import android.util.Log;

import java.util.ArrayList;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.liangwei.kugouxia.beans.AdBean;
import com.liangwei.kugouxia.frame.AppConfig;

/**
 * ad Model
 */
public class AdModel extends ModelParent {
    public static String adUrl = AppConfig.app_url+"index.php?action=2";
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
        ArrayList<AdBean> datas = new ArrayList<>();
        try{
            JSONObject json = (JSONObject) JSONObject.parse(content);
            JSONArray jsonArray = json.getJSONArray("content");

            for (int index=0;index<jsonArray.size();index++){
                JSONObject jsonData = jsonArray.getJSONObject(index);
                String title = jsonData.getString("title");
                String description = jsonData.getString("description");
                String imgUrl = jsonData.getString("img_url");
                String clickUrl = jsonData.getString("link");
                AdBean data = new AdBean(title,description,imgUrl,clickUrl);
                datas.add(data);
            }
            parseCallback.success();
        }catch (Exception e){
            parseCallback.fail(e);
        }

        return datas;
    }
}
