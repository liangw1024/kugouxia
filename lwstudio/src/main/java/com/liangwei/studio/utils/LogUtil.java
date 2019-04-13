package com.liangwei.studio.utils;

import android.util.Log;

public class LogUtil {
    public static void logD(Class cls,String content){
        Log.d(cls.getName(),"------>> "+content);
    }
}
