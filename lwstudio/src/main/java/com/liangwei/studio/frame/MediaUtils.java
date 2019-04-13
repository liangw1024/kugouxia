package com.liangwei.studio.frame;

import android.content.Context;
import android.graphics.Bitmap;
import android.provider.MediaStore;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MediaUtils {
    /**
     * save a bitmap to systemAlbum
     * @param bmp
     * @return result
     */
    public static boolean saveBitmapToAlbum(Context context,Bitmap bmp, String path, String fileName){
        File fPath = new File(path);
        if(!fPath.exists()){
            fPath.mkdirs();
        }
        File imgFile = new File(fPath,fileName);
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(imgFile);
            bmp.compress(Bitmap.CompressFormat.PNG,100,fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
            try {
                MediaStore.Images.Media.insertImage(context.getContentResolver(), imgFile.getAbsolutePath(), fileName, "酷狗侠");
                return true;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }
}
