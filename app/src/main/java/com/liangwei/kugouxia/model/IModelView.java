package com.liangwei.kugouxia.model;

import java.util.ArrayList;

/**
 * 数据处理完成的接口 操作view
 * @param <T> 数据对象类型
 */
public interface IModelView<T> {
    public void success(ArrayList<T> beans);
    public void fail(Exception e);
    public void loading(int progress);
}
