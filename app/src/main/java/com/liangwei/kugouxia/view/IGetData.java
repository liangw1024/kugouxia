package com.liangwei.kugouxia.view;

import java.util.List;

/**
 * 用于解析数据成功后 交给view处理
 * Created by-liangWei on 2018/8/9 16:33
 */
public interface IGetData {
    /**
     *
     * @param isOk 是否解析成功
     * @param objects
     * @param  e
     */
    public void result(boolean isOk,List<Object> objects,Exception e);

}
