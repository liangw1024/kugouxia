package com.liangwei.kugouxia.frame.CustomView;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.VideoView;

/**
 * https://github.com/leifu1107/BackgroundVideo
 * Created by 20385 on 2018/3/3.
 */

public class VideoBackgroundView extends VideoView {
    public VideoBackgroundView(Context context) {
        super(context);
    }

    public VideoBackgroundView(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    public VideoBackgroundView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    // 实现全屏,重新计算高度和宽度(如果不重新测量,视频不能充满整个屏幕)
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        int width = getDefaultSize(0, widthMeasureSpec);
        int height = getDefaultSize(0, heightMeasureSpec);
        setMeasuredDimension(width, height);
    }


}
