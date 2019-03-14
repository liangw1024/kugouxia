package com.liangwei.studio.ui;

import android.content.Context;
import android.os.Handler;

import android.widget.Toast;

/**
 * 自定义Toast
 * @author LiangWei OnCreat
 */
public class LwToast extends Toast {
    private static LwToast lwToast = null;
    //当前屏幕是否有toast
    private boolean isShowing = false;
    //显示时间
    private int showTime = 3*1000;

    public LwToast(Context context) {
        super(context);
    }
    public static LwToast getInstance(Context context){
        if (lwToast==null){
            lwToast = new LwToast(context);
        }
        return lwToast;
    }


   public void show(){
        if(isShowing){

        }else{
            super.show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    isShowing = false;
                }
            }, showTime);
        }
   }
}
