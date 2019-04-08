package com.liangwei.kugouxia.model;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.liangwei.studio.net.HttpRequestUtils;
import com.liangwei.studio.net.INetCallback;

import java.util.ArrayList;


/**
 *
 */
public abstract class ModelParent{
    private IModelView iModelView;

    /***
     * 向服务器请求数据并做处理
     * @param context
     * @param url 接口地址
     * @param iModelView 处理获取事件完成接口
     * @param iParse
     * @return
     */
    public void request(Context context, String url, IModelView iModelView,IParse iParse) {
        this.iModelView = iModelView;
        //发起请求
        HttpRequestUtils.postNeedUi((Activity) context, url, new INetCallback() {
            @Override
            public void success(String content) {
                //对返回的网络数据进行封装处理
                ArrayList datas = parse(content,iParse);
                Log.d("Model", "请求数据成功====>"+content);
                iModelView.success(datas);

            }
            @Override
            public void fail(Exception e, String detail) {
                iModelView.fail(e);
            }
        });
    }

    public void requestByGet(Context context, String url, IModelView iModelView,IParse iParse) {
        this.iModelView = iModelView;
        //发起请求
        HttpRequestUtils.getNeedUi((Activity) context, url, new INetCallback() {
            @Override
            public void success(String content) {
                //对返回的网络数据进行封装处理
                ArrayList datas = parse(content,iParse);
                Log.d("Model", "请求数据成功====>"+content);
                iModelView.success(datas);

            }
            @Override
            public void fail(Exception e, String detail) {
                iModelView.fail(e);
            }
        });
    }
    /**
     * 解析返回的网络数据，并封装成对象处理成一个数组
     */
    public abstract ArrayList parse(String content,IParse parseCallback);

    public static interface IParse{
        public void success();
        public void fail(Exception e);

    }
}