package com.liangwei.studio.ui.btn;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.liangwei.studio.R;

public class FloatingButtonsItem extends RelativeLayout {
    private Context context;
    private View rootView;
    private ImageView iv_icon;
    private TextView tv_description;

    private String text;
    private int textColor;
    private float textSize;
    private float textMarginTop;

    private Drawable img;
    private float imgWidth;
    private float imgHeight;


    public FloatingButtonsItem(Context context) {
        super(context);
    }

    public FloatingButtonsItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        rootView = LayoutInflater.from(getContext()).inflate(R.layout.view_floating_button_item, this, true);
        iv_icon = rootView.findViewById(R.id.view_floating_button_item_iv_icon);
        tv_description = rootView.findViewById(R.id.view_floating_button_item_tv_description);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.FloatingButtonsItem);


        setImgParameters(typedArray.getDrawable(R.styleable.FloatingButtonsItem_fbi_img),
                typedArray.getDimension(R.styleable.FloatingButtonsItem_fbi_imgWidth, 50),
                typedArray.getDimension(R.styleable.FloatingButtonsItem_fbi_imgHeight, 50));
        setTextParameters(typedArray.getString(R.styleable.FloatingButtonsItem_fbi_text),
                typedArray.getColor(R.styleable.FloatingButtonsItem_fbi_textColor, Color.RED),
                (int) typedArray.getDimension(R.styleable.FloatingButtonsItem_fbi_textSize, 10),
                (int) typedArray.getDimension(R.styleable.FloatingButtonsItem_fbi_textMarginTop, 0));

    }

    public FloatingButtonsItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 设置img参数
     *
     * @param drawable
     * @param wid      width
     * @param hei      height
     */
    public void setImgParameters(Drawable drawable, float wid, float hei) {
        LinearLayout.LayoutParams parameters = (LinearLayout.LayoutParams) iv_icon.getLayoutParams();
        if (drawable != null) {
            iv_icon.setImageDrawable(drawable);
        } else {
            iv_icon.setImageDrawable(getResources().getDrawable(R.drawable.bg_division_gradient_one));
        }
        if (wid != 0) {
            parameters.width = (int) wid;
        } else {
            parameters.width = 50;
        }
        if (hei != 0) {
            parameters.height = (int) hei;
        } else {
            parameters.height = 50;
        }
        iv_icon.setLayoutParams(parameters);
    }

    /**
     * 设置文本parameters
     *
     * @param text
     * @param color
     * @param size
     * @param marginTop
     */
    public void setTextParameters(String text, int color, int size, int marginTop) {
        if (text != null) {
            tv_description.setText(text);
        } else {
            tv_description.setText("良伟工作室");
        }

        if (color != 0) {
            tv_description.setTextColor(color);
        } else {
            tv_description.setTextColor(Color.RED);
        }

        if (size != 0) {
            tv_description.setTextSize(size);
        } else {
            tv_description.setTextSize(18);
        }
        if (marginTop != 0) {
            LinearLayout.LayoutParams parameters = (LinearLayout.LayoutParams) tv_description.getLayoutParams();
            parameters.topMargin = marginTop;
            tv_description.setLayoutParams(parameters);
        } else {

        }
    }


    public ImageView getIv_icon() {
        return iv_icon;
    }

    public void setIv_icon(ImageView iv_icon) {
        this.iv_icon = iv_icon;
    }

    public TextView getTv_description() {
        return tv_description;
    }

    public void setTv_description(TextView tv_description) {
        this.tv_description = tv_description;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
        tv_description.setText(text);
    }

    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
        tv_description.setTextColor(textColor);
    }

    public float getTextSize() {
        return textSize;
    }

    public void setTextSize(float textSize) {
        this.textSize = textSize;
        tv_description.setTextSize(textSize);
    }

    public Drawable getImg() {
        return img;
    }

    public void setImg(Drawable img) {
        this.img = img;
    }

    public float getImgWidth() {
        return imgWidth;
    }

    public void setImgWidth(float imgWidth) {
        this.imgWidth = imgWidth;
    }

    public float getImgHeight() {
        return imgHeight;
    }

    public void setImgHeight(float imgHeight) {
        this.imgHeight = imgHeight;
    }

    public float getTextMarginTop() {
        return textMarginTop;
    }

    public void setTextMarginTop(float imgMarginTop) {
        this.textMarginTop = textMarginTop;
    }
}
