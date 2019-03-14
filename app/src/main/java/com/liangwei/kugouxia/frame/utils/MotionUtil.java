package com.liangwei.kugouxia.frame.utils;

import android.util.Log;
import android.view.MotionEvent;

public class MotionUtil {
    /**向左移动**/
    public final static int MOVE_LEFT = 0;
    /**向上移动**/
    public final static int MOVE_UP = 1;
    /**向右移动**/
    public final static int MOVE_RIGHT = 2;
    /**向下移动**/
    public final static int MOVE_DOWN = 3;

    private static int pressX,currentX;
    private static int pressY,currentY;

    /**
     * 得到移动方向 返回的是MtionUtil里面的常量
     * @param event
     * @return
     */
    public static int getMotionOrientation(MotionEvent event,int moveDistance){
        int result = 0;
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                pressX = (int) event.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                currentX = (int) event.getX();
                if (pressX-currentX<moveDistance){
                    Log.d("motion---->","move light");
                    result = MOVE_RIGHT;
                }else if(pressX-currentX>moveDistance){
                    Log.d("motion---->","move left");
                    result = MOVE_LEFT;
                }
                break;
        }
        return result;
    }
}
