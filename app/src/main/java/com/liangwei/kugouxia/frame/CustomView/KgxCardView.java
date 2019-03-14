package com.liangwei.kugouxia.frame.CustomView;

/**
 * Created by weibao on 2018/3/31.
 */

import android.content.*;
import android.content.res.*;
import android.graphics.Color;
import android.graphics.drawable.*;
import android.util.*;
import android.view.*;
import android.widget.*;
import androidx.cardview.widget.CardView;

import com.liangwei.kugouxia.R;

public class KgxCardView extends CardView{
    ImageView iv_icon;
    TextView tv_message;
    CardView cardView;
    View line;

    //在代码调用的时候使用
    public KgxCardView(Context context) {
        super(context);
        init(context, null);
    }

    //在布局文件中使用的时候调用，比两个参数的多个样式文件
    public KgxCardView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    //在布局文件中使用的时候调用
    public KgxCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);

    }

    /*初始化接口变量*/
    IMyClick iMyClick = null;

    /*自定义事件*/
    public void setOnMyClickListener(IMyClick i) {
        iMyClick = i;
        if (iMyClick != null) {
            cardView.setOnClickListener(new CardView.OnClickListener() {
                @Override
                public void onClick(View p1) {
                    iMyClick.myClick();
                }
            });

        }


    }

    private void init(Context c, AttributeSet aa) {
        View view = View.inflate(getContext(), R.layout.custom_kgcardview, this);//先有孩子，再去找爹，喜当爹
        //初始化控件
        iv_icon = (ImageView) view.findViewById(R.id.custom_kgxcardview_describeIcon);
        tv_message = (TextView) view.findViewById(R.id.custom_kgxcardview_describeTitle);
        cardView = (CardView) view.findViewById(R.id.custom_kgxcardview_describeCard);
        line = view.findViewById(R.id.custom_kgxcardview_describeDivision);
        TypedArray a = c.obtainStyledAttributes(aa, R.styleable.KgxCardView);
        CharSequence text = a.getText(R.styleable.KgxCardView_kg_text);
        if (text != null) {
            tv_message.setText(text);
        }

        tv_message.setTextColor(a.getColor(R.styleable.KgxCardView_kg_textColor, Color.parseColor("#686868")));

        Drawable drawable = a.getDrawable(R.styleable.KgxCardView_kg_image);
        if (drawable != null) {
            iv_icon.setBackgroundDrawable(drawable);
        }
        Drawable line_bg = a.getDrawable(R.styleable.KgxCardView_kg_division_bg);
        if(line_bg!=null){
            line.setBackground(line_bg);
        }
        a.recycle();
    }


    public void setText(String s) {
        tv_message.setText(s);
    }

    public void setHeight(int i) {

    }

    public interface IMyClick {
        public void myClick();
    }


}

