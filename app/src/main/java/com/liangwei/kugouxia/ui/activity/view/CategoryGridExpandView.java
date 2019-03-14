package com.liangwei.kugouxia.ui.activity.view;

import android.content.Context;
import android.util.AttributeSet;

import com.liangwei.studio.ui.ExpandView;

import com.liangwei.kugouxia.R;

public class CategoryGridExpandView extends ExpandView {
    public CategoryGridExpandView(Context context) {
        super(context);
    }

    public CategoryGridExpandView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CategoryGridExpandView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public int setLayout() {
        return R.layout.expand_taotu_category;
    }

}
