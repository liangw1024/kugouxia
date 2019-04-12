package com.liangwei.studio.ui;

import android.annotation.SuppressLint;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.RemoteViews;

import com.liangwei.studio.R;

/**
 * 可以收缩的view  expand collapse
 */
public abstract class ExpandView extends RelativeLayout{
    public String TAG = "ExpandView(collapse)----->";
    /**展开状态*/
    public final static int STATE_EXPAND = 0;
    /**收起状态*/
    public final static int STATE_COLLAPSE = 1;
    private int state = 0;
    private Context context = null;
    private boolean animationRunning = false;
    private Animation expandAnimation = null;
    private Animation collapseAnimation = null;
    private int layout = 0;
    private View view = null;
    private IViewParse iViewParse = null;

    public ExpandView(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public ExpandView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public ExpandView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    /**
     * 自定义View控件解析
     * @param iViewParse
     */
    public void setiViewParse(IViewParse iViewParse) {
        this.iViewParse = iViewParse;
        iViewParse.parse(view);
    }

    /**
     * expand collapse的layout
     * @return
     */
    public abstract int setLayout();


    private void init(){
        view = LayoutInflater.from(context).inflate(setLayout(),this,true);


    }

    public void setLayout(int layout) {
        this.layout = layout;
    }

    /**
     * 设置展开动画
     * @param animation
     * @return
     */
    public ExpandView setExpandAnimation(Animation animation){
        this.expandAnimation = animation;
        return this;
    }
    /**
     * 设置关闭动画
     * @param animation
     * @return
     */
    public ExpandView setCollapseAnimation(Animation animation){
        this.collapseAnimation = animation;
        return this;
    }

    public void hide(){
        state = STATE_COLLAPSE;
        setVisibility(View.GONE);
        animationRunning = false;
    }
    public void start(){
        state = STATE_EXPAND;
        setVisibility(View.VISIBLE);
        animationRunning = false;
    }
    /**
     * 展开view
     */
    public void expand(){
        if (expandAnimation == null) {
            expandAnimation = AnimationUtils.loadAnimation(context,R.anim.default_expand_animation);
        }
        if (isAnimationRunning()==false){
            startExpandAnimation();
        }

    }
    /**
     * 收缩view
     */
    public void collapse(){
        if (collapseAnimation == null) {
            collapseAnimation = AnimationUtils.loadAnimation(context,R.anim.default_collapse_anim);
        }
        if (isAnimationRunning()==false){
            startCollapseAnimation();
        }

    }

    /**
     * 判断动画是否在运行
     * @return
     */
    public boolean isAnimationRunning() {
        return animationRunning;
    }

    /**
     * 得到View当前的状态
     * @return
     */
    public int getState() {
        return state;
    }

    /**
     * 开始展开动画
     */
    public void startExpandAnimation(){
        Log.d(TAG,"开始展开动画");
            expandAnimation.setAnimationListener(new ExpandAnimationListener());
            setAnimation(expandAnimation);
            startAnimation(expandAnimation);
    }

    /**
     * 开始关闭动画
     */
    public void startCollapseAnimation(){
        Log.d(TAG,"开始关闭动画");
            collapseAnimation.setAnimationListener(new CollapseAnimationListener());
            setAnimation(collapseAnimation);
            startAnimation(collapseAnimation);
    }

    /**
     * view 子view解析接口
     */
    public static interface IViewParse{
        public void parse(View view);
    }
    /**
     * expand animation listener
     */
    private class ExpandAnimationListener implements Animation.AnimationListener {
        @Override
        public void onAnimationStart(Animation animation) {
            animationRunning = true;
            setVisibility(View.VISIBLE);
        }

        @Override
        public void onAnimationEnd(Animation animation) {
            animationRunning = false;
            state = ExpandView.STATE_EXPAND;
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    }
    /**
     * collapse animation listener
     */
    private class CollapseAnimationListener implements Animation.AnimationListener {

        @Override
        public void onAnimationStart(Animation animation) {
            animationRunning = true;
        }

        @Override
        public void onAnimationEnd(Animation animation) {
            animationRunning = false;
            setVisibility(View.INVISIBLE);
            state = ExpandView.STATE_COLLAPSE;
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    }
}
