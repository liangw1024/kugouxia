package com.liangwei.kugouxia.model;

import android.util.Log;

import com.liangwei.kugouxia.beans.QQCard;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class QQTaoTuModel extends ModelParent {
    @Override
    public ArrayList parse(String content, IParse iParse) {
        List<QQCard> mQQcards = new ArrayList<>();
        String result = content;
        try{
            JSONObject jsonObject = new JSONObject(result);
            JSONObject data = jsonObject.getJSONObject("data");
            JSONArray list = data.getJSONArray("list");
            for (int i = 0; i < list.length(); i++) {
                JSONObject listData = list.getJSONObject(i);
                String name = listData.getString("name");
                String signatrue = listData.getString("memo");
                String head = listData.getString("avatar");
                String bg = listData.getString("card");
                String time = listData.getString("create_time");
                QQCard qqCardData = new QQCard();
                qqCardData.setHeadUrl(head);
                qqCardData.setName(name);
                qqCardData.setSignatrue(signatrue);
                qqCardData.setBackgroundUrl(bg);
                qqCardData.setTime(time);
                mQQcards.add(qqCardData);
            }
            iParse.success();
        }catch (Exception e){
            iParse.fail(e);
        }
        return (ArrayList) mQQcards;
    }
}
