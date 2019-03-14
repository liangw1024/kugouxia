package com.liangwei.kugouxia.frame;

import android.util.Log;

import org.apache.commons.codec.binary.Base64;

import java.io.UnsupportedEncodingException;

/**
 * Created by weibao on 2018/5/5.
 */

public class Base64Utils {


    private static final String UTF_8 = "UTF-8";

    /**
     * 对给定的字符串进行base64解码操作
     */
    public static String decodeData(String inputData) {
        try {
            if (null == inputData) {
                return null;
            }
            return new String(Base64.decodeBase64(inputData.getBytes(UTF_8)), UTF_8);
        } catch (UnsupportedEncodingException e) {
            Log.e("qqtoool",e.toString());
        }

        return null;
    }

    /**
     * 对给定的字符串进行base64加密操作
     */
    public static String encodeData(String inputData) {
        try {
            if (null == inputData) {
                return null;
            }
            return new String(Base64.encodeBase64(inputData.getBytes(UTF_8)), UTF_8);
        } catch (UnsupportedEncodingException e) {
            Log.e("qqtoool",e.toString());
        }

        return null;
    }

    public static void main(String[] args) {
        System.out.println(Base64Utils.encodeData("A"));
    }
}
