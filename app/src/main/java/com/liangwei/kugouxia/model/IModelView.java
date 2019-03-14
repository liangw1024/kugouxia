package com.liangwei.kugouxia.model;

import java.util.ArrayList;

/**
 * 通用的model 处理事件
 * @param <T>
 */
public interface IModelView<T> {
    public void success(ArrayList<T> obj);
    public void fail(Exception e);
    public void loading(int progress);
}
