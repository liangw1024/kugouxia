package com.liangwei.studio.net;

import android.app.Activity;
import android.util.Log;

import com.github.kevinsawicki.http.HttpRequest;

/**
 * Created by linagWei on 2018/8/7 10:7
 *封装HttpRequest工具类
 */
public class HttpRequestUtils {
    /**
     * get请求 netCallback的方法都是在ui thread里面
     * @param activity runOnUiThread
     * @param url
     * @param INetCallback
     */
    public static void getNeedUi(final Activity activity, final String url, final INetCallback INetCallback) {
        Log.d("net","当前的url:"+url);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    HttpRequest httpRequest = HttpRequest.get(url);
                    final String body = httpRequest.body();
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            INetCallback.success(body);
                            Log.d("net",body);
                        }
                    });
                }catch (final Exception e){
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            INetCallback.fail(e , null);
                        }
                    });

                }
            }
        }).start();
    }
    /**
     * get请求 netCallback的方法都是在ui thread里面
     * @param activity runOnUiThread
     * @param url
     * @param INetCallback
     */
    public static void postNeedUi(final Activity activity, final String url, final INetCallback INetCallback) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    HttpRequest httpRequest = HttpRequest.post(url);
                    final String body = httpRequest.body();
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Log.d("netFrame","content:"+body);
                            INetCallback.success(body);
                        }
                    });
                }catch (final Exception e){
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Log.d("netFrame","err:"+e.toString());
                            INetCallback.fail(e , null);
                        }
                    });

                }
            }
        }).start();
    }
}
