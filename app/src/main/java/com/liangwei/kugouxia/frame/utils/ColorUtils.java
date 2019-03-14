package com.liangwei.kugouxia.frame.utils;

import android.graphics.Color;

/**
 * Created by weibao on 2018/6/9.
 */

public class ColorUtils {
    public static int colorToRgb(int color){
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);
        return red+green+blue;
    }
}
