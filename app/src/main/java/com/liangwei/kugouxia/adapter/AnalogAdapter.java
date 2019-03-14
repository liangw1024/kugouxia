package com.liangwei.kugouxia.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import java.util.List;

import com.liangwei.kugouxia.R;
import com.liangwei.kugouxia.beans.AnalogVideoBean;
import com.liangwei.kugouxia.ui.activity.tools.AnalogVideoCall.AnalogVideoCallActivity;

/**
 * 模拟适配电话适配器
 * Created by weibao on 2018/6/10.
 */

public class AnalogAdapter extends BaseRecyclerAdapter {
    Context context;
    public AnalogAdapter(Context context, List<Object> datas, int layoutId) {
        super(context, datas, layoutId);
        this.context = context;
    }

    @Override
    public void convert(ViewHolder holder, final Object item) {

        final AnalogVideoBean analogVideo = (AnalogVideoBean) item;
        holder.setText(R.id.item_analog_video_tv,analogVideo.getTitle());
        holder.setImageByUrl(context,R.id.item_analog_video_iv,analogVideo.getImgUrl());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("analogVideoData",analogVideo);
                intent.setClass(context, AnalogVideoCallActivity.class);
                context.startActivity(intent);
            }
        });
    }
}
