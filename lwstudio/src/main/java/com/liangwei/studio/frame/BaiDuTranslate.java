package com.liangwei.studio.frame;


import android.app.Activity;

import com.liangwei.studio.net.HttpRequestUtils;
import com.liangwei.studio.net.INetCallback;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class BaiDuTranslate {
    private static final String TRANS_API_HOST = "http://api.fanyi.baidu.com/api/trans/vip/translate";

    private String appid;
    private String key;
    private BaiDuTranslate(){}

    public BaiDuTranslate(String appid, String key) {
        this.appid = appid;
        this.key = key;
    }


    /**
     * 翻译
     * @param query
     * @param from
     * @param to
     */
    public void translate(Activity act, String query, String from, String to, INetCallback i) {
        Map<String, String> params = buildParams(query, from, to);
        HttpGet.get(act,TRANS_API_HOST,params, i);
    }

    //获取url
    private Map<String, String> buildParams(String query, String from, String to) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("q", query);
        params.put("from", from);
        params.put("to", to);
        params.put("appid", appid);

        // 随机数
        String salt = String.valueOf(System.currentTimeMillis());
        params.put("salt", salt);

        // 签名
        String src = appid + query + salt + key; // 加密前的原文
        params.put("sign", MD5.md5(src));

        return params;
    }

    public static class HttpGet {
        protected static final int SOCKET_TIMEOUT = 10000; // 10S
        protected static final String GET = "GET";

        public static void get(Activity act,String host, Map<String, String> params, INetCallback i) {
            String sendUrl = getUrlWithQueryString(host, params);
            HttpRequestUtils.getNeedUi(act, sendUrl,i);


        }

        //get url
        public static String getUrlWithQueryString(String url, Map<String, String> params) {
            if (params == null) {
                return url;
            }

            StringBuilder builder = new StringBuilder(url);
            if (url.contains("?")) {
                builder.append("&");
            } else {
                builder.append("?");
            }

            int i = 0;
            for (String key : params.keySet()) {
                String value = params.get(key);
                if (value == null) { // 过滤空的key
                    continue;
                }

                if (i != 0) {
                    builder.append('&');
                }

                builder.append(key);
                builder.append('=');
                builder.append(encode(value));

                i++;
            }

            return builder.toString();
        }

        protected static void close(Closeable closeable) {
            if (closeable != null) {
                try {
                    closeable.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        /**
         * 对输入的字符串进行URL编码, 即转换为%20这种形式
         *
         * @param input 原文
         * @return URL编码. 如果编码失败, 则返回原文
         */
        public static String encode(String input) {
            if (input == null) {
                return "";
            }

            try {
                return URLEncoder.encode(input, "utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            return input;
        }

        private static TrustManager myX509TrustManager = new X509TrustManager() {

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }

            @Override
            public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            }

            @Override
            public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            }
        };

    }

    /**
     * MD5编码相关的类
     *
     * @author wangjingtao
     *
     */
    public static class MD5 {
        // 首先初始化一个字符数组，用来存放每个16进制字符
        private static final char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd',
                'e', 'f'};

        /**
         * 获得一个字符串的MD5值
         *
         * @param input 输入的字符串
         * @return 输入字符串的MD5值
         */
        public static String md5(String input) {
            if (input == null)
                return null;

            try {
                // 拿到一个MD5转换器（如果想要SHA1参数换成”SHA1”）
                MessageDigest messageDigest = MessageDigest.getInstance("MD5");
                // 输入的字符串转换成字节数组
                byte[] inputByteArray = input.getBytes("utf-8");
                // inputByteArray是输入字符串转换得到的字节数组
                messageDigest.update(inputByteArray);
                // 转换并返回结果，也是字节数组，包含16个元素
                byte[] resultByteArray = messageDigest.digest();
                // 字符数组转换成字符串返回
                return byteArrayToHex(resultByteArray);
            } catch (NoSuchAlgorithmException e) {
                return null;
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return input;
        }

        /**
         * 获取文件的MD5值
         *
         * @param file
         * @return
         */
        public static String md5(File file) {
            try {
                if (!file.isFile()) {
                    System.err.println("文件" + file.getAbsolutePath() + "不存在或者不是文件");
                    return null;
                }

                FileInputStream in = new FileInputStream(file);

                String result = md5(in);

                in.close();

                return result;

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        public static String md5(InputStream in) {

            try {
                MessageDigest messagedigest = MessageDigest.getInstance("MD5");

                byte[] buffer = new byte[1024];
                int read = 0;
                while ((read = in.read(buffer)) != -1) {
                    messagedigest.update(buffer, 0, read);
                }

                in.close();

                String result = byteArrayToHex(messagedigest.digest());

                return result;
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        private static String byteArrayToHex(byte[] byteArray) {
            // new一个字符数组，这个就是用来组成结果字符串的（解释一下：一个byte是八位二进制，也就是2位十六进制字符（2的8次方等于16的2次方））
            char[] resultCharArray = new char[byteArray.length * 2];
            // 遍历字节数组，通过位运算（位运算效率高），转换成字符放到字符数组中去
            int index = 0;
            for (byte b : byteArray) {
                resultCharArray[index++] = hexDigits[b >>> 4 & 0xf];
                resultCharArray[index++] = hexDigits[b & 0xf];
            }

            // 字符数组组合成字符串返回
            return new String(resultCharArray);

        }

    }


}