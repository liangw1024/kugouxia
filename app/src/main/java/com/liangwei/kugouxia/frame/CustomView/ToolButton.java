package com.liangwei.kugouxia.frame.CustomView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.liangwei.kugouxia.R;

/**
 * Created by weibao on 2018/5/27.
 */

public class ToolButton extends RelativeLayout {
   private TextView tv_describe;
   private ImageView iv_describe;
   IOnclickListener iOnclickListener = null;

    public void setiOnclickListener(IOnclickListener iOnclickListener) {
        this.iOnclickListener = iOnclickListener;
    }

    public interface IOnclickListener{
        public void onclick(View parent);
    }

    public ToolButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }
    public void init(Context context, AttributeSet attrs){
        View view = View.inflate(getContext(), R.layout.custom_tool_button, this);
        tv_describe = view.findViewById(R.id.custom_tool_button_tv_describe);
        iv_describe = view.findViewById(R.id.custom_tool_button_iv_describe);
        TypedArray typedArray = context.obtainStyledAttributes(attrs,R.styleable.ToolButton);
        String text = typedArray.getString(R.styleable.ToolButton_tb_text);
        int textColor = typedArray.getColor(R.styleable.ToolButton_tb_textColor, Color.parseColor("#000000"));
        Drawable image = typedArray.getDrawable(R.styleable.ToolButton_tb_image);
        Log.i("kgx","text:"+text+"\ttextcolor:"+text+"\t");
        setText(text);
        setTextColor(textColor);
        setImage(image);
        view.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(iOnclickListener!=null){
                    iOnclickListener.onclick(view);
                }
            }
        });
    }

    /**
     * 设置按钮文本
     * @param describeText
     */
    public void setText(String describeText){
        if(describeText==null){
            return;
        }
        tv_describe.setText(describeText);
    }

    /**
     *设置按钮文本颜色
     * @param describeTextColor
     */
    public void setTextColor(int describeTextColor){
        if(describeTextColor==0){
            return;
        }
        tv_describe.setTextColor(describeTextColor);
    }

    /**
     * 设置按钮图标
     * @param describeImage
     */
    public void setImage(Drawable describeImage){
        if(describeImage==null) {
            return;
        }
        iv_describe.setImageDrawable(describeImage);
    }
}
