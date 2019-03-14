package com.liangwei.kugouxia.frame;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;

import com.liangwei.kugouxia.R;

/**
 * Created by 20385 on 2018/3/3.
 */

public class ScreenManager {
    private static ScreenManager instance;
    public synchronized static ScreenManager getInstance() {
        if (instance == null) {
            instance = new ScreenManager();
        }
        return instance;
    }

    /**hide status*/
    public void setFullScreen(BaseActivity activity) {
        activity.getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }
    /**得到状态栏的高度*/
    public static int getStatusHeight(Context context){
        //获取status_bar_height资源的ID
        int resourceId = context.getResources().getIdentifier("status_bar_height","dimen","android");
        if(resourceId >0) {
            //根据资源ID获取响应的尺寸值
            int statusBarHeight = context.getResources().getDimensionPixelSize(resourceId);
            return statusBarHeight;
        }
        return 0;

    }

    /**沉浸状态栏*/
    public void setStatusBarTranslucmnt(BaseActivity mActivity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = mActivity.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            /*状态栏黑底文字\full screen not hide status*/
            //window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR|View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE );
        }
    }
    /**
     * 旋转屏幕
     * @param isChange
     * @param mActivity
     */
    public void setRotateScreen(boolean isChange, BaseActivity mActivity) {
        if (!isChange) {
            mActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        } else {
            mActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }


    /**
     * 设置状态栏background color
     * @param activity
     * @param colorResId
     */
    public static void setStatusBarColor(Activity activity, int colorResId) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = activity.getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(colorResId);
                //底部导航栏
                window.setNavigationBarColor(activity.getResources().getColor(colorResId));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int getScreenHeight(Context context){
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return displayMetrics.heightPixels;
    }
    public static int getScreenWidth(Context context){
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return displayMetrics.widthPixels;
    }


}
