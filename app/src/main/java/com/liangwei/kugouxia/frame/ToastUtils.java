package com.liangwei.kugouxia.frame;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.liangwei.kugouxia.R;

/**
 * Created by weibao on 2018/5/19.
 */

public class ToastUtils {
    public static Toast toast = null;
    public static int showTime = Toast.LENGTH_LONG;
    /** 第一次时间 */
    private static long oneTime = 0 ;
    /** 第二次时间 */
    private static long twoTime = 0 ;
    static View view = null;
    static TextView tv_showText = null;
    public static  void ShowToast(Context context, String text){

        if(toast==null){
           toast = new Toast(context);
           view = LayoutInflater.from(context).inflate(R.layout.custom_toast,null);
           tv_showText = view.findViewById(R.id.custom_toast_tv_showText);
           oneTime = System.currentTimeMillis();
           tv_showText.setText(text);
           toast.setView(view);
           toast.show();
       }else{
            twoTime = System.currentTimeMillis();
            if(twoTime-oneTime>showTime){
                oneTime = twoTime;
                tv_showText.setText(text);
                toast.setView(view);
                toast.show();
            }
       }


    }
}
