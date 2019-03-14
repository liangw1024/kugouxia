package com.liangwei.kugouxia.model;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.liangwei.studio.net.HttpRequestUtils;
import com.liangwei.studio.net.INetCallback;

import java.util.ArrayList;



public abstract class ModelParent{
    private IModelView iModelView;

    /***
     * 从网络获取数据然后解析
     * @param context
     * @param url
     * @param iModelView 处理获取事件
     * @return
     */
    public void request(Context context, String url, IModelView iModelView,IParse iParse) {
        this.iModelView = iModelView;
        HttpRequestUtils.postNeedUi((Activity) context, url, new INetCallback() {
            @Override
            public void success(String content) {
                ArrayList arrayList = parse(content,iParse);
                Log.d("data", content);
                iModelView.success(arrayList);

            }
            @Override
            public void fail(Exception e, String detail) {
                iModelView.fail(e);
            }
        });
    }
    public void requestByGet(Context context, String url, IModelView iModelView,IParse iParse) {
        this.iModelView = iModelView;
        HttpRequestUtils.getNeedUi((Activity) context, url, new INetCallback() {
            @Override
            public void success(String content) {
                ArrayList arrayList = parse(content,iParse);
                Log.d("data", content);
                iModelView.success(arrayList);

            }
            @Override
            public void fail(Exception e, String detail) {
                iModelView.fail(e);
            }
        });
    }
    /**
     * 用户自定义解析方法
     * 将content解析为一个ArrayList集合
     */
    public abstract ArrayList parse(String content,IParse parseCallback);
    public static interface IParse{
        public void success();
        public void fail(Exception e);

    }
}