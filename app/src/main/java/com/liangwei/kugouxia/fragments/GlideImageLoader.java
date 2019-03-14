package com.liangwei.kugouxia.fragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.youth.banner.loader.ImageLoader;

import java.io.ByteArrayOutputStream;

import com.liangwei.kugouxia.R;

/**
 * banner图片加载器
 * Created by weibao on 2018/3/17.
 */
public class GlideImageLoader extends ImageLoader {
    public static int TYPE_NET_IMG = 1;
    public static int TYPE_DRAWALE = 2;
    public static int TYPE_BITMAP = 3;
    private int type = 1;
    public GlideImageLoader(int type){
        this.type=type;
    }
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        /**
         注意：
         1.图片加载器由自己选择，这里不限制，只是提供几种使用方法
         2.返回的图片路径为Object类型，由于不能确定你到底使用的那种图片加载器，
         传输的到的是什么格式，那么这种就使用Object接收和返回，你只需要强转成你传输的类型就行，
         切记不要胡乱强转！
         */
        if (type==TYPE_BITMAP){
            Bitmap bitmap = (Bitmap) path;
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG,100,byteArrayOutputStream);
            byte bytes[] = byteArrayOutputStream.toByteArray();
            Glide.with(context) .load(bytes).diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true).error(R.drawable.ic_launcher_background) .into(imageView);
        }else if(type==TYPE_NET_IMG){
            Glide.with(context) .load(path).error(R.drawable.ic_launcher_background) .into(imageView);
        }else if(type==TYPE_DRAWALE){
            Glide.with(context) .load(path).error(R.drawable.ic_launcher_background) .into(imageView);
        }

    }
}