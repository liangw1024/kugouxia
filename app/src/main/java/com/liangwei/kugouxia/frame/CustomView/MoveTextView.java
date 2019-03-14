package com.liangwei.kugouxia.frame.CustomView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import androidx.annotation.Nullable;
import android.util.AttributeSet;

import com.liangwei.kugouxia.R;
import com.liangwei.kugouxia.frame.ScreenManager;

/**
 * Created by weibao on 2018/6/1.
 */

public class MoveTextView extends androidx.appcompat.widget.AppCompatImageView {
    public void setContext(Context context) {
        this.context = context;
    }

    private Context context;
    int viewHeight = 50;
    //文字x坐标
    private int x = 0;
    //运行线程
    private LogicThred logicThred = null;
    //显示文本
    private String text = "伟宝网络传媒 我一直用心在做";
    //显示文本颜色
    private int textColor = Color.RED;
    //显示文本大小
    private int textSize = 30;
    //移动速度
    private int speed = 30;
    //是否运行
    private boolean isRunning = false;

    public boolean isIsstatus() {
        return isstatus;
    }

    public void setIsstatus(boolean isstatus) {
        this.isstatus = isstatus;
    }

    private boolean isstatus = true;


    private Paint paint = new Paint();
    public String getText() {
        return text;
    }
    public MoveTextView setText(String text) {
        if(text==null){
           // throw new NullPointerException("text is null : text不能为空");
        }else{
            this.text = text;
        }
        return this;
    }

    public int getTextColor() {
        return textColor;
    }
    public MoveTextView setTextColor(int textColor) {
        if(!(textColor==0)){
            this.textColor = textColor;
        }
        return this;
    }


    public int getTextSize() {
        return textSize;
    }
    public MoveTextView setTextSize(int textSize) {
        if(!(textSize==0)){
            this.textSize = textSize;
        }else{
            throw new NullPointerException("textsize cannot 0");
        }
        return this;
    }

    public int getSpeed() {
        return speed;
    }
    public MoveTextView setSpeed(int speed) {
        if(!(speed==0)){
            this.speed = speed;
        }
        return this;
    }

    public boolean isRunning() {
        return isRunning;
    }
    public void setRunning(boolean running) {
        isRunning = running;
    }

    public MoveTextView(Context context) {
        super(context);
    }

    public MoveTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        paint.setTextSize(textSize);
        paint.setColor(textColor);
        //rect 套text
        Rect rect = new Rect();
        paint.getTextBounds(text,0,text.length()-1,rect);

        viewHeight = rect.height();
        if(isstatus){
            canvas.drawText(text,x,viewHeight,paint);
        }else{
            canvas.drawText(text,x,viewHeight-ScreenManager.getStatusHeight(context),paint);
        }


    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int heightMeasureSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int viewWidth = MeasureSpec.getSize(widthMeasureSpec);
        switch (heightMeasureSpecMode){
            case MeasureSpec.UNSPECIFIED://unSpecified未指定
                break;
            case MeasureSpec.AT_MOST://最大
                setMeasuredDimension(viewWidth,textSize+10);
                break;
            case MeasureSpec.EXACTLY://exactly 精确
                break;
        }
    }

    /**
     * 初始化
     * @param context
     * @param attrs
     */
    public void init(Context context,AttributeSet attrs){
        TypedArray typedArray = context.obtainStyledAttributes(attrs,R.styleable.MoveTextView);
        String mText = typedArray.getString(R.styleable.MoveTextView_mtv_text);
        int mTextColor = typedArray.getColor(R.styleable.MoveTextView_mtv_textColor,textColor);
        int mTextSize = typedArray.getInteger(R.styleable.MoveTextView_mtv_textSize,textSize);
        int mSpeed = typedArray.getInteger(R.styleable.MoveTextView_mtv_speed,speed);
        setText(mText);
        setTextColor(mTextColor);
        setTextSize(mTextSize);
        setSpeed(mSpeed);
    }

    /**
     * 开始运行
     * @return
     */
    public MoveTextView start(){
        isRunning = true;
        if(logicThred==null){
            logicThred = new LogicThred();
            logicThred.start();
        }else{
            logicThred.start();
        }
        return this;
    }

    /**
     * 停止运行
     * @return
     */
    public MoveTextView stop(){
        isRunning = false;
        if(logicThred==null){
            throw new NullPointerException("LogicThred is null ,you need start:请先执行start()");
        }else{
            logicThred.stop();
        }

        return this;
    }

    /**
     * 控制处理x
     */
    private class LogicThred extends Thread{
        @Override
        public void run() {
            super.run();
            while (isRunning){
                try {
                    sleep(speed);
                    x--;
                    postInvalidate();
                    if(x<0-paint.measureText(text)){//x出屏幕 回到原点
                       x=getWidth();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
