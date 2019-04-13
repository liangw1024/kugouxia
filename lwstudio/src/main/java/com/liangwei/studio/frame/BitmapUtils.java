package com.liangwei.studio.frame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.media.MediaMetadataRetriever;
import android.util.Log;
import android.view.View;

public class BitmapUtils {


    /**
     * 按比例缩放图片
     *
     * @param origin 原图
     * @param ratio  比例
     * @return 新的bitmap
     */
    public static Bitmap scaleBitmap(Bitmap origin, float ratio) {
        if (origin == null) {
            return null;
        }
        int width = origin.getWidth();
        int height = origin.getHeight();
        Matrix matrix = new Matrix();
        matrix.preScale(ratio, ratio);
        Bitmap newBM = Bitmap.createBitmap(origin, 0, 0, width, height, matrix, false);
        if (newBM.equals(origin)) {
            return newBM;
        }
        origin.recycle();
        return newBM;
    }

    /**
     * 根据给定的宽和高进行拉伸
     *
     * @param origin    原图
     * @param newWidth  新图的宽
     * @param newHeight 新图的高
     * @return new Bitmap
     */
    public static Bitmap scaleBitmap(Bitmap origin, int newWidth, int newHeight) {
        if (origin == null) {
            return null;
        }
        int height = origin.getHeight();
        int width = origin.getWidth();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);// 使用后乘
        Bitmap newBM = Bitmap.createBitmap(origin, 0, 0, width, height, matrix, false);
        return newBM;
    }
    public static Bitmap addWatermaskImage(Bitmap src,Bitmap watermark){
        //创建一个和src相同大小的bitmap
        Bitmap result = Bitmap.createBitmap(src.getWidth(), src.getHeight(), Bitmap.Config.ARGB_8888);
        //画布
        Canvas canvas = new Canvas(result);
        //在空的bitmap上面绘制原图
        canvas.drawBitmap(src, 0,0,null );
        //创建水印图片
        Bitmap mWatermark = watermark;
        //在空的bitmap上面绘制watermark
        canvas.drawBitmap(mWatermark, 20,20,null);
        canvas.save(
                
        );// 保存
        // store
        canvas.restore();// 存储
        return result;

    }
    public static Bitmap createTextWatermask(Bitmap src,String text,int size,int color,int x,int y){
        Bitmap result = Bitmap.createBitmap(src.getWidth(),src.getHeight(),Bitmap.Config.ARGB_8888);
        //在result上面绘图
        Canvas canvas= new Canvas(result);
        //画布(0,0)上面绘制原来的图片
        canvas.drawBitmap(src,0 ,0,null );

        //创建画笔
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(color);
        paint.setTextSize(size);
        Rect bounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), bounds);
        //在画布上面绘制文职
        canvas.drawText(text, x, y, paint);
        return result;

    }

    /**
     * 将一个Drawable 转bitmap
     * @param drawable
     * @return
     */
    public static Bitmap drawableToBitmap(Drawable drawable){
        int w = drawable.getIntrinsicWidth();
        int h = drawable.getIntrinsicHeight();

        // 取 drawable 的颜色格式
        Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                : Bitmap.Config.RGB_565;
        // 建立对应 bitmap
        Bitmap bitmap = Bitmap.createBitmap(w, h, config);
        // 建立对应 bitmap 的画布
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, w, h);
        // 把 drawable 内容画到画布中
        drawable.draw(canvas);
        return bitmap;

    }

    /**
     * 将View 转为bitmap
     * @param view
     * @return
     */
    public static Bitmap viewToVBitmap(View view) {
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888); //利用bitmap生成画布
        Canvas canvas = new Canvas(bitmap); //把view中的内容绘制在画布上
        view.draw(canvas);
        return bitmap;
    }
    /**
     * 图片模糊处理
     * @param sentBitmap
     * @param radius
     * @param canReuseInBitmap
     * @return
     */
    public static Bitmap blur(Bitmap sentBitmap, int radius, boolean canReuseInBitmap) {
        Bitmap bitmap;
        if (canReuseInBitmap) {
            bitmap = sentBitmap;
        } else {
            bitmap = sentBitmap.copy(sentBitmap.getConfig(), true);
        }


        if (radius < 1) {
            return (null);
        }


        int w = bitmap.getWidth();
        int h = bitmap.getHeight();


        int[] pix = new int[w * h];
        bitmap.getPixels(pix, 0, w, 0, 0, w, h);


        int wm = w - 1;
        int hm = h - 1;
        int wh = w * h;
        int div = radius + radius + 1;


        int r[] = new int[wh];
        int g[] = new int[wh];
        int b[] = new int[wh];
        int rsum, gsum, bsum, x, y, i, p, yp, yi, yw;
        int vmin[] = new int[Math.max(w, h)];


        int divsum = (div + 1) >> 1;
        divsum *= divsum;
        int dv[] = new int[256 * divsum];
        for (i = 0; i < 256 * divsum; i++) {
            dv[i] = (i / divsum);
        }


        yw = yi = 0;


        int[][] stack = new int[div][3];
        int stackpointer;
        int stackstart;
        int[] sir;
        int rbs;
        int r1 = radius + 1;
        int routsum, goutsum, boutsum;
        int rinsum, ginsum, binsum;


        for (y = 0; y < h; y++) {
            rinsum = ginsum = binsum = routsum = goutsum = boutsum = rsum = gsum = bsum = 0;
            for (i = -radius; i <= radius; i++) {
                p = pix[yi + Math.min(wm, Math.max(i, 0))];
                sir = stack[i + radius];
                sir[0] = (p & 0xff0000) >> 16;
                sir[1] = (p & 0x00ff00) >> 8;
                sir[2] = (p & 0x0000ff);
                rbs = r1 - Math.abs(i);
                rsum += sir[0] * rbs;
                gsum += sir[1] * rbs;
                bsum += sir[2] * rbs;
                if (i > 0) {
                    rinsum += sir[0];
                    ginsum += sir[1];
                    binsum += sir[2];
                } else {
                    routsum += sir[0];
                    goutsum += sir[1];
                    boutsum += sir[2];
                }
            }
            stackpointer = radius;


            for (x = 0; x < w; x++) {


                r[yi] = dv[rsum];
                g[yi] = dv[gsum];
                b[yi] = dv[bsum];


                rsum -= routsum;
                gsum -= goutsum;
                bsum -= boutsum;


                stackstart = stackpointer - radius + div;
                sir = stack[stackstart % div];


                routsum -= sir[0];
                goutsum -= sir[1];
                boutsum -= sir[2];


                if (y == 0) {
                    vmin[x] = Math.min(x + radius + 1, wm);
                }
                p = pix[yw + vmin[x]];


                sir[0] = (p & 0xff0000) >> 16;
                sir[1] = (p & 0x00ff00) >> 8;
                sir[2] = (p & 0x0000ff);


                rinsum += sir[0];
                ginsum += sir[1];
                binsum += sir[2];


                rsum += rinsum;
                gsum += ginsum;
                bsum += binsum;


                stackpointer = (stackpointer + 1) % div;
                sir = stack[(stackpointer) % div];


                routsum += sir[0];
                goutsum += sir[1];
                boutsum += sir[2];


                rinsum -= sir[0];
                ginsum -= sir[1];
                binsum -= sir[2];


                yi++;
            }
            yw += w;
        }
        for (x = 0; x < w; x++) {
            rinsum = ginsum = binsum = routsum = goutsum = boutsum = rsum = gsum = bsum = 0;
            yp = -radius * w;
            for (i = -radius; i <= radius; i++) {
                yi = Math.max(0, yp) + x;


                sir = stack[i + radius];


                sir[0] = r[yi];
                sir[1] = g[yi];
                sir[2] = b[yi];


                rbs = r1 - Math.abs(i);


                rsum += r[yi] * rbs;
                gsum += g[yi] * rbs;
                bsum += b[yi] * rbs;


                if (i > 0) {
                    rinsum += sir[0];
                    ginsum += sir[1];
                    binsum += sir[2];
                } else {
                    routsum += sir[0];
                    goutsum += sir[1];
                    boutsum += sir[2];
                }


                if (i < hm) {
                    yp += w;
                }
            }
            yi = x;
            stackpointer = radius;
            for (y = 0; y < h; y++) {
                // Preserve alpha channel: ( 0xff000000 & pix[yi] )
                pix[yi] = (0xff000000 & pix[yi]) | (dv[rsum] << 16) | (dv[gsum] << 8) | dv[bsum];


                rsum -= routsum;
                gsum -= goutsum;
                bsum -= boutsum;


                stackstart = stackpointer - radius + div;
                sir = stack[stackstart % div];


                routsum -= sir[0];
                goutsum -= sir[1];
                boutsum -= sir[2];


                if (x == 0) {
                    vmin[y] = Math.min(y + r1, hm) * w;
                }
                p = x + vmin[y];


                sir[0] = r[p];
                sir[1] = g[p];
                sir[2] = b[p];


                rinsum += sir[0];
                ginsum += sir[1];
                binsum += sir[2];


                rsum += rinsum;
                gsum += ginsum;
                bsum += binsum;


                stackpointer = (stackpointer + 1) % div;
                sir = stack[stackpointer];


                routsum += sir[0];
                goutsum += sir[1];
                boutsum += sir[2];


                rinsum -= sir[0];
                ginsum -= sir[1];
                binsum -= sir[2];


                yi += w;
            }
        }


        bitmap.setPixels(pix, 0, w, 0, 0, w, h);


        return (bitmap);
    }

}
