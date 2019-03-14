package com.liangwei.kugouxia.frame.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

public class UrlUtils {
    /**
     * 进行url编码
     * @param v
     * @return
     */
    public static String encode(String v){
        if(v==null){
            return "param v is null";
        }
        try {
            return URLEncoder.encode(v,"utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "错误啦";
    }
    public static String putParams(String url,Map<String,String> map){
        StringBuilder stringBuilder = new StringBuilder();
        if (url.contains("?")){

        }else{
            stringBuilder.append(url+"?");
        }

        for (Map.Entry<String,String> entry:map.entrySet()){

            stringBuilder.append(entry.getKey()+"="+encode(entry.getValue())+"&");
        }
        return stringBuilder.toString().substring(0,stringBuilder.length()-1);
    }


}
