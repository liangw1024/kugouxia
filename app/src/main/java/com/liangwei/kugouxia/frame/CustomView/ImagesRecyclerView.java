package com.liangwei.kugouxia.frame.CustomView;

import android.content.Context;
import androidx.recyclerview.widget.GridLayoutManager;
import android.util.AttributeSet;

import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;

import java.util.List;

import com.liangwei.kugouxia.adapter.AdapterImage;

/**
 * 显示一组图片 grid
 * Created by weibao on 2018/5/19.
 */

public class ImagesRecyclerView extends LRecyclerView {
    private  AdapterImage adapterImage;
    private List<String> list_images;
    private Context context;

    /**
     * 设置图片链接
     * @param list_images
     */
    public void setList_images(List<String> list_images) {
            this.list_images = list_images;
    }

    public List<String> getList_images() {
        return list_images;
    }

    /**
     * 更新数据
     */
    public void updata(){
        adapterImage = new AdapterImage(context,list_images,AdapterImage.MODE_WRAP_CONTENT);
        LRecyclerViewAdapter lRecyclerViewAdapter = new LRecyclerViewAdapter(adapterImage);
        adapterImage.setiClickImage(new AdapterImage.IClickImage<String>() {
            @Override
            public void click(List<String> urls,int position) {

            }

            // TODO: 2018/9/23 0023  写一个点击查看图片

//            @Override
//            public void click(Bitmap bit) {
//                BitmapUtils.bitmapTemp = bit;
//                context.startActivity(new Intent(context, ShowImageActivity.class));
//            }
        });
        setAdapter(lRecyclerViewAdapter);
        adapterImage.notifyDataSetChanged();
    }

    public ImagesRecyclerView(Context context) {
        super(context);
    }

    public ImagesRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
       init(context);

    }

    public ImagesRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
    private void init(Context context){
        GridLayoutManager gridLayoutManager = new GridLayoutManager(context,4);
        setLayoutManager(gridLayoutManager);

    }

}
