package com.liangwei.kugouxia.view;

import java.util.List;

public interface IBaseVIew {
    public void load(int process);
    public void success(List<List<String>> result);
    public void fail(Exception e,int code);
}
