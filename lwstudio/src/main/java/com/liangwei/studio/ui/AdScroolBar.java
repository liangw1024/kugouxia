package com.liangwei.studio.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Message;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;

import com.liangwei.studio.R;

/**
 * Created by-liangWei on 2018/8/19 12:39
 * 滚动广告条
 */
public class AdScroolBar extends RecyclerView {
    //action 滚动ad
    public final static int AD_SCROOL = 0;
    //action 停止滚动ad
    public final static int AD_SCROOL_STOP = 1;
    //向上滚动
    public final static int AD_SCROLL_UP = 2;
    //向下滚动
    public final static int AD_SCROLL_DOWN = 3;
    //广告滚动方向
    public int adScrollState = AD_SCROLL_UP;

    //滑动动画时间
    private int scrollAnimationTime = 800;
    //切换间隔时间
    private int switchoverIntervalTime = scrollAnimationTime+1000;
    //当前滚动位置
    public int presentScrolPosition = 0;
    AdHandler adHandler;
    private Context context;

    public int getScrollAnimationTime() {
        return scrollAnimationTime;
    }

    public void setScrollAnimationTime(int scrollAnimationTime) {
        this.scrollAnimationTime = scrollAnimationTime;
    }

    public int getSwitchoverIntervalTime() {
        return switchoverIntervalTime;
    }

    public void setSwitchoverIntervalTime(int switchoverIntervalTime) {
        this.switchoverIntervalTime = getScrollAnimationTime()+switchoverIntervalTime;
    }

    public AdScroolBar(Context context) {
        super(context);
        this.context = context;

    }

    public AdScroolBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init(attrs);
    }

    public AdScroolBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init(attrs);
    }

    private void init(AttributeSet attributeSet){
        TypedArray typedArray = context.obtainStyledAttributes(attributeSet,R.styleable.AdScroolbar);
        int attr_switchoverIntervalTime = typedArray.getInteger(R.styleable.AdScroolbar_ad_scroolbar_switchoverIntervalTime,switchoverIntervalTime);
        if(attr_switchoverIntervalTime!=0){
            setSwitchoverIntervalTime(attr_switchoverIntervalTime);
            Log.d("lwui:"+context.getPackageName(),"switchOver interval time is ---->"+switchoverIntervalTime);

        }

        int attr_scroolTime = typedArray.getInteger(R.styleable.AdScroolbar_ad_scroolbar_scroolTime,scrollAnimationTime);
        if(attr_scroolTime!=0){
            this.scrollAnimationTime = attr_scroolTime;
        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context){
            @Override
            public void smoothScrollToPosition(RecyclerView recyclerView, State state, int position) {
                super.smoothScrollToPosition(recyclerView, state, position);
                LinearSmoothScroller linearSmoothScroller = new LinearSmoothScroller(context){
                    @Override
                    protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
                        // 返回的是划过1px 经历的时间
                        return (float) (scrollAnimationTime / displayMetrics.densityDpi);
                    }
                };
                linearSmoothScroller.setTargetPosition(position);
                startSmoothScroll(linearSmoothScroller);
            }
        };
        setLayoutManager(linearLayoutManager);
        setHasFixedSize(true);
        adHandler= new AdHandler();
        sendScroolMes(AD_SCROOL);

    }

    /**
     * excute smooth scroll handler
     */
    private class AdHandler extends android.os.Handler{
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case AD_SCROOL:
                    try {
                        if(getAdapter().getItemCount()!=0&getAdapter()!=null){
                            smoothScroolAd();
                            //达到不停发送的效果
                            sendScroolMes(AD_SCROOL);
                        }else{
                            Log.d("adScoolbar-->>>>","没有数据 不用滚动".toString());
                        }
                    }catch (NullPointerException exception){
                        Log.d("adScoolbar-->>>>","创建adapter失败 不用执;行滚动"+exception.toString());
                    }


                    break;
            }
        }
    }
    //delay发送让ad滚动的msg
    private void sendScroolMes(int action){
        adHandler.removeMessages(action);
        adHandler.sendEmptyMessageDelayed(action,switchoverIntervalTime);
    }

    /**
     * smooth滚动广告条
     */
    private void smoothScroolAd(){
        switch (adScrollState){
            case AD_SCROOL_STOP:
                break;
            case AD_SCROLL_UP:
                presentScrolPosition+=1;
                smoothScrollToPosition(presentScrolPosition);
                //滑动到了最后一个Item 改变滑动状态 ：下滑
                if(presentScrolPosition==getAdapter().getItemCount()){
                    adScrollState = AD_SCROLL_DOWN;
                }

                break;
            case AD_SCROLL_DOWN:
                presentScrolPosition-=1;
                smoothScrollToPosition(presentScrolPosition);
                //滑动到了第一个Item 改变滑动状态：上滑
                if(presentScrolPosition==0){
                    adScrollState = AD_SCROLL_UP;
                }


                break;
                default:
                    break;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {

        return false;
    }
//    public void stopScroll(){
//        scrollToPosition(presentScrolPosition);
//        adScrollState = AD_SCROOL_STOP;
//    }

}
