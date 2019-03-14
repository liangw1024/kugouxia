package com.liangwei.studio.net;

/**
 * Created by-liangWei on 2018/8/7 10:59
 * 网络请求回调接口
 */
public interface INetCallback {
 
    /**
     * 请求成功返回数据
     * @param content
     */
    public void success(String content);

    /**
     * 请求失败
     * @param e
     * @param detail 失败原因
     */
    public void fail(Exception e,String detail);
}
