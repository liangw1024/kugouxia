package com.liangwei.kugouxia.frame.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.liangwei.kugouxia.frame.ToastUtils;

/**
 * bitmap 工具类
 * Created by 伟宝 on 2018/4/27.
 */

public class BitmapUtils {
    public static Bitmap bitmapTemp = null;
    /**
     * 图片质量压缩
     * @param image
     * @param srcPath 要保存的路径
     * @return
     */
    public static Bitmap compressImage(Bitmap image, String srcPath) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while (baos.toByteArray().length / 1024 > 100) {    // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset();// 重置baos即清空baos
            options -= 10;// 每次都减少10
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中

        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);// 把ByteArrayInputStream数据生成图片
        try {
            FileOutputStream out = new FileOutputStream(srcPath);
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, out);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    // 为图片target添加水印文字
    // Bitmap target：被添加水印的图片
    // String mark：水印文章
    public static Bitmap createWatermark(Bitmap target, String mark,int pointsize) {
        int w = target.getWidth();
        int h = target.getHeight();



        //建立一个空的Bitmap  ;
        Bitmap bmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        // 初始化画布绘制的图像到bmp上
        Canvas canvas = new Canvas(bmp);

        Paint p = new Paint();

        // 水印的颜色
        p.setColor(Color.WHITE);

        // 水印的字体大小
        p.setTextSize(pointsize);
        p.setShadowLayer(1f, 0f, 1f, Color.DKGRAY);
        p.setAntiAlias(true);// 去锯齿

        canvas.drawBitmap(target, 0, 0, p);
        canvas.drawCircle(w/8,w/2,20,p);
        canvas.rotate((float) -15.5);
        canvas.drawText(mark, w/7, h /2-20, p);

//        canvas.rotate(0,20,45);
        canvas.save();
       // final int save = canvas.save(Canvas.ALL_SAVE_FLAG);
        canvas.restore();

        return bmp;
    }

    public interface IGetBimap{
        public void success(Bitmap bitmap);
        public void fail();
    }
    public static void getBitmapByUrl(Context context, String url, final IGetBimap iGetBimapSuccess){
        Glide.with(context).load(url).asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                if(resource==null){
                    iGetBimapSuccess.fail();
                }else{
                    iGetBimapSuccess.success(resource);
                }
            }
        });

    }
    /**
     * 保存bitmp to sdcard kugouxia
     * @param mBitmap
     * @param context
     */
    public static void SaveBitmap(final Bitmap mBitmap, final Context context) {
        // 第一步：首先保存图片
        //将Bitmap保存图片到指定的路径/sdcard/Boohee/下，文件名以当前系统时间命名,但是这种方法保存的图片没有加入到系统图库中
        File appDir = new File(Environment.getExternalStorageDirectory(), "kugouxia/img");

        if (!appDir.exists()) {
            if(!appDir.mkdirs()){
                ToastUtils.ShowToast(context,"mkdirs fail");
            }
        }

        String fileName = System.currentTimeMillis() + ".jpg";
        File file = new File(appDir, fileName);

        try {
            FileOutputStream fos = new FileOutputStream(file);
            mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 第二步：其次把文件插入到系统图库
        try {
            MediaStore.Images.Media.insertImage(context.getContentResolver(), file.getAbsolutePath(), fileName, null);
            Toast.makeText(context,"保存成功",Toast.LENGTH_SHORT).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(context,"保存失败:"+e.toString(),Toast.LENGTH_SHORT).show();
        }
        // 第三步：最后通知图库更新
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + file)));
    }

    /**
     * 把一个view转bitmap
     * @param view
     * @return
     */
    public static Bitmap getBitmapFromView(View view){
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(),view.getHeight(), Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        view.layout(view.getLeft(), view.getTop(), view.getRight(), view.getBottom());
        Drawable bgDrawable = view.getBackground();
        if (bgDrawable != null)
        {
            bgDrawable.draw(canvas);
        }
        else
        {
            canvas.drawColor(Color.GREEN);
        }
        // Draw view to canvas
        view.draw(canvas);
        return bitmap;
    }


}
