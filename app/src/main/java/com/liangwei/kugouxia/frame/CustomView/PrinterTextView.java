package com.liangwei.kugouxia.frame.CustomView;

import android.annotation.SuppressLint;
import android.content.Context;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by weibao on 2018/4/15.
 */

@SuppressLint("AppCompatCustomView")
public class PrinterTextView extends TextView {
    private Timer timer;
    private int printDelay = 300;//打印延迟
    private String text;//文本
    private int currentIndex;//当前index

    public PrinterTextView(Context context) {
        super(context);
    }

    public PrinterTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public PrinterTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }



    public void startPrintText(String mtext,int delay){
        stopPrintText();
        this.text = mtext;
        printDelay = delay;
        timer = new Timer();
        timer.schedule(new PrinterTimerTask(),printDelay,printDelay);
    }
    public void stopPrintText(){
        if(timer!=null){
            timer.cancel();
            timer = null;
            currentIndex = 0;
        }
    }
    class PrinterTimerTask extends TimerTask{
        @Override
        public void run() {
            post(new Runnable() {
                @Override
                public void run() {
                    if(currentIndex<text.length()){
                        currentIndex++;
                        if(currentIndex%2==0){
                            setText(text.substring(0,currentIndex));
                        }else{
                            setText(text.substring(0,currentIndex)+"_");
                        }

                    }else{
                        setText(text);
                        stopPrintText();
                    }
                }
            });

        }
    }
}
