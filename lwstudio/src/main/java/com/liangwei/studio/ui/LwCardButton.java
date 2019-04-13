package com.liangwei.studio.ui;

import android.content.*;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.widget.*;
import android.view.*;
import android.util.*;
import com.liangwei.studio.R;
import androidx.cardview.widget.CardView;

public class LwCardButton extends RelativeLayout {
    private Context context;
    //垂直布局
    public final static int ORIENTATION_VERTICAL = 0;
    //横向布局
    public final static int ORIENTATION_HORIZONTAL = 1;
    //点击回调接口
    private OnClickListener onClickListener;
    //标签图片 hot
    public final static int TARGET_IMG_HOT = R.mipmap.ic_target_hot;
    //标签图片 new
    public final static int TARGET_IMG_NEW = R.mipmap.ic_target_hot;
    //标签图片 pay
    public final static int TARGET_IMG_PAY = R.mipmap.ic_target_hot;

    //布局方向
    private int orientation = 0;
    //隐藏target
    private boolean hideTarget = true;
    //隐藏分割线
    private boolean hideDivision = false;
    //标签图片
    private int img_target = 0;

    private ImageView iv_target, iv_description, iv_division;
    private TextView tv_description;
    private CardView cardView;
    private View root = null;

    //在代码调用的时候使用
    public LwCardButton(Context context) {
        super(context);
        this.context = context;
        init(context, null);
    }

    //在布局文件中使用的时候调用，比两个参数的多个样式文件
    public LwCardButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        init(context, attrs);
    }

    //在布局文件中使用的时候调用
    public LwCardButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init(context, attrs);

    }


    public void setOnClickListener(OnClickListener onClickListener) {
        cardView.setOnClickListener(onClickListener);
    }


    private void init(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.LwCardButton);
        String attribute_orientation = typedArray.getString(R.styleable.LwCardButton_orientation);
        switch (attribute_orientation) {
            case "horizontal":
                Log.d("kgxui","orientation:"+"horizontal");
                orientation = ORIENTATION_HORIZONTAL;
                root =  View.inflate(getContext(), R.layout.view_lw_card_btn_horizontal, this);
                //root = LayoutInflater.from(context).inflate(R.layout.view_lw_card_btn_horizontal,this,true);
                break;
            case "vertical":
                Log.d("kgxui","orientation:"+"vertical");
                orientation = ORIENTATION_VERTICAL;
                root = LayoutInflater.from(context).inflate(R.layout.view_lw_card_btn_vertical,this,true);
                break;
            default:
                Log.d("kgxui","orientation:"+"horizontal");
                root =  View.inflate(getContext(), R.layout.view_lw_card_btn_horizontal, this);
                //root = LayoutInflater.from(context).inflate(R.layout.view_lw_card_btn_horizontal,this,true);
                break;

        }

        parseView(root);
        if (onClickListener != null) {
            cardView.setOnClickListener(onClickListener);
        }

        String descriptionText = typedArray.getString(R.styleable.LwCardButton_descriptionText);
        Drawable descriptionImage = typedArray.getDrawable(R.styleable.LwCardButton_descriptionImage);
        Drawable targetImage = typedArray.getDrawable(R.styleable.LwCardButton_targetImage);
        Drawable divisionImage = typedArray.getDrawable(R.styleable.LwCardButton_divisionImage);
        int descriptionTextColor = typedArray.getColor(R.styleable.LwCardButton_descriptionTextColor, Color.parseColor("#000000"));
        if (!descriptionText.isEmpty()) {
            tv_description.setText(descriptionText);
        }
        tv_description.setTextColor(descriptionTextColor);
        if (descriptionImage != null) {
            iv_description.setImageDrawable(descriptionImage);
        }
        if (targetImage != null) {
            iv_target.setImageDrawable(targetImage);
        }
        if (divisionImage != null) {
            iv_division.setImageDrawable(divisionImage);
        } else {
            iv_division.setImageDrawable(context.getResources().getDrawable(R.drawable.bg_division_gradient_one));
        }

    }

    /**
     * 根据布局方向解析绑定控件
     */
    private void parseView(View view) {
        switch (orientation) {
            case ORIENTATION_VERTICAL:
                parseVertical(view);
                break;
            case ORIENTATION_HORIZONTAL:
                parseHorizontal(view);
                break;
        }

    }

    /**
     * 解析垂直布局
     */
    private void parseVertical(View view) {
        Log.d("kgxui","parse:"+"vertical");
        cardView = view.findViewById(R.id.view_lw_card_btn_vertical_cardview);
        iv_description = view.findViewById(R.id.view_lw_card_btn_vertical_iv_description);
        iv_division = view.findViewById(R.id.view_lw_card_btn_vertical_iv_division);
        iv_target = view.findViewById(R.id.view_lw_card_btn_vertical_iv_target);
        tv_description = view.findViewById(R.id.view_lw_card_btn_vertical_tv_description);
    }

    /**
     * 解析横向布局
     */
    private void parseHorizontal(View view) {
        Log.d("kgxui","parse:"+"horizontal");
        cardView = view.findViewById(R.id.view_lw_card_btn_horizontal_cardview);
        iv_description = view.findViewById(R.id.view_lw_card_btn_horizontal_iv_description);
        iv_division = view.findViewById(R.id.view_lw_card_btn_horizontal_iv_division);
        iv_target = view.findViewById(R.id.view_lw_card_btn_horizontal_iv_target);
        tv_description = view.findViewById(R.id.view_lw_card_btn_horizontal_tv_description);
    }
}


